package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.*;
import com.codecool.quest_store.model.Item;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentStoreController implements HttpHandler {
    private DBConnector connectionPool;
    private SessionResolver sessionResolver;
    private SessionDAO sessionDAO;
    private int userId;
    private int student_level;
    private LevelsDAO levelsDAO;
    private WalletDAO walletDAO;
    private ItemDAO itemDAO;
    private InventoryDAO inventoryDAO;


    public StudentStoreController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        sessionResolver = new SessionResolver(httpExchange, connectionPool);
        sessionResolver.checkIfSessionIsValid();
        String response = "";
        String method = httpExchange.getRequestMethod();

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/store.twig");
        JtwigModel model = JtwigModel.newModel();

        String cookieString = httpExchange.getRequestHeaders().getFirst("Cookie");
        if (cookieString != null) {
            HttpCookie cookie = HttpCookie.parse(cookieString).get(0);
            String sesionNumber = cookie.getValue();
            sessionDAO = new SessionDAOImpl(connectionPool);
            userId = sessionDAO.getUserIdBySession(sesionNumber);

            levelsDAO = new LevelsDAOImpl(connectionPool);
            student_level = levelsDAO.getStudentLevel(userId);
        }

        itemDAO = new ArtifactDAO(connectionPool);
        LevelsDAOImpl levelsDAO = new LevelsDAOImpl(connectionPool);

        List<Item> artifactsBasic = itemDAO.getAllBasic();
        List<Item> artifactsExtra = itemDAO.getAllExtra();
        int userId = getUserIdBySessionId(httpExchange);
        int studentLevel = levelsDAO.getStudentLevel(userId);

        List<Item> basicStudentArtifacts = getArtifactsByStudentLevel(artifactsBasic, studentLevel);
        List<Item> magicStudentArtifacts = getArtifactsByStudentLevel(artifactsExtra, studentLevel);

        if(method.equals("GET")){

            model.with("student_level", student_level);
            model.with("artifactsBasic", basicStudentArtifacts);
            model.with("artifactsExtra", magicStudentArtifacts);
            model.with("notPossibleToBuy", false);
            model.with("buyAttemptTitle", "");
            response = template.render(model);

            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(method.equals("POST")){

            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println(formData);
            Map inputs = parseFormData(formData);
            System.out.println("artifact id: " + inputs.get("id"));

            // check if possible to buy
            walletDAO = new WalletDAOImpl(connectionPool);
            int studentCoolcoins = walletDAO.getStudentsCoolcoinsAmount(userId, "current_coins");
            int artifactId = Integer.parseInt((String) inputs.get("id"));
            Item artifactToBuy = itemDAO.getById(artifactId);
            boolean isPossibleToBuy = checkIfArtifactIsPossibleToBuy(artifactToBuy.getValue(), studentCoolcoins);

            model.with("student_level", student_level);
            // action after above checking
            if (!isPossibleToBuy) {
                model.with("notPossibleToBuy", true);
                model.with("buyed", false);
                model.with("buyAttemptTitle", artifactToBuy.getTitle());
            } else {
                model.with("notPossibleToBuy", false);
                model.with("buyed", true);
                model.with("buyAttemptTitle", artifactToBuy.getTitle());
                // add to student inventory
                inventoryDAO = new InventoryDAOimpl(connectionPool);
                inventoryDAO.addArtifactToStudentInventory(userId, artifactToBuy.getId());
                // decrease coolcoins in student wallet
                int coolcoinsDecreased = walletDAO.getStudentsCoolcoinsAmount(userId, "current_coins") - artifactToBuy.getValue();
                walletDAO.changeCoolcoinsAmount(coolcoinsDecreased, "current_coins", userId);
            }
            model.with("artifactsBasic", basicStudentArtifacts);
            model.with("artifactsExtra", magicStudentArtifacts);

            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private int getUserIdBySessionId(HttpExchange httpExchange) {
        String cookieString = httpExchange.getRequestHeaders().getFirst("Cookie");
        int userId = 0;

        if (cookieString != null) {
            HttpCookie cookie = HttpCookie.parse(cookieString).get(0);
            userId = new SessionDAOImpl(connectionPool).getUserIdBySession(cookie.getValue());
        }
        return userId;
    }

    private List<Item> getArtifactsByStudentLevel(List<Item> artifacts, int studentLevel) {
        List<Item> studentArtifacts = new ArrayList<>();

        for (Item artifact : artifacts) {
            if (artifact.getAccess_level().intValue() <= studentLevel) {
                studentArtifacts.add(artifact);
            }
        }

        return studentArtifacts;
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private boolean checkIfArtifactIsPossibleToBuy(int artifactValue, int studentCoolcoins) {
        return artifactValue <= studentCoolcoins;
    }

}