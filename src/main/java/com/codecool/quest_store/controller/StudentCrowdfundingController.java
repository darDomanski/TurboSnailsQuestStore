package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.*;
import com.codecool.quest_store.model.Item;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentCrowdfundingController implements HttpHandler {
    private DBConnector connectionPool;
    private ItemDAO itemDAO;
    private CrowdfundingDAO crowdfundingDAO;
    private RedirectController redirectController;
    private WalletDAO walletDAO;
    private SessionDAO sessionDAO;

    public StudentCrowdfundingController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
        this.itemDAO = new ArtifactDAO(connectionPool);
        this.crowdfundingDAO = new CrowdfundingDAOImpl(connectionPool);
        this.redirectController = new RedirectController();
        this.walletDAO = new WalletDAOImpl(connectionPool);
        this.sessionDAO = new SessionDAOImpl(connectionPool);

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SessionResolver sessionResolver = new SessionResolver(httpExchange, connectionPool);
        sessionResolver.checkIfSessionIsValid();
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            System.out.println(formData);
            Map inputs = redirectController.parseFormData(formData);
            System.out.println(inputs);
            int artifactId = Integer.parseInt((String) inputs.keySet().toArray()[0]);
            int donation = Integer.parseInt((String) inputs.values().toArray()[0]);

            crowdfundingDAO.addCollectedMoney(donation, artifactId);
            decreaseStudentsCoolcoins(donation, getUserId(httpExchange));
        }

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/crowdfunding.twig");
        JtwigModel model = JtwigModel.newModel();

        Map<Integer, Item> itemCollectedCoinsMap = getAllExtraArtifactsWithCollectedCoolcoins();
        model.with("artifactsValuesMap", itemCollectedCoinsMap);

        response = template.render(model);
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private Map<Integer, Item> getAllExtraArtifactsWithCollectedCoolcoins() {
        List<Item> extraItems = crowdfundingDAO.getAllItems();
        Map<Integer, Item> artifactsWithCollectedCoolcoins = new HashMap<>();

        for (Item item : extraItems) {
            Integer moneyCollected = item.getValue() - crowdfundingDAO.getCollectedMoneyByArtifactId(item.getId());
            artifactsWithCollectedCoolcoins.put(moneyCollected, item);
        }

        System.out.println(artifactsWithCollectedCoolcoins);
        return artifactsWithCollectedCoolcoins;
    }

    private void decreaseStudentsCoolcoins(int coolcoinsAmount, int studentId) {

        int coolcoinsDecreased = walletDAO.getStudentsCoolcoinsAmount(studentId, "current_coins") - coolcoinsAmount;
        walletDAO.changeCoolcoinsAmount(coolcoinsDecreased, "current_coins", studentId);
    }

    private int getUserId(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = null;
        if (cookieStr != null) {
            cookie = HttpCookie.parse(cookieStr).get(0);
        }
        System.out.println(sessionDAO.getUserIdBySession(cookie.getValue()) + " userID");
        return sessionDAO.getUserIdBySession(cookie.getValue());
    }
}