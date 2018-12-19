package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;


public class StudentWalletController implements HttpHandler {
    private DBConnector connectionPool;
    private SessionDAO sessionDAO;
    private int userId;
    private Integer student_level;
    private LevelsDAO levelsDAO;
    private WalletDAO walletDAO;
    private int coolcoins_amount;

    public StudentWalletController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SessionResolver sessionResolver = new SessionResolver(httpExchange, connectionPool);
        sessionResolver.checkIfSessionIsValid();
        String response = "";
        String method = httpExchange.getRequestMethod();

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/wallet.twig");
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

        walletDAO = new WalletDAOImpl(connectionPool);
        coolcoins_amount = walletDAO.getStudentsCoolcoinsAmount(userId, "current_coins");

        if(method.equals("GET")){

            model.with("student_level", student_level);
            model.with("coolcoins_amount", coolcoins_amount);
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(method.equals("POST")){

            model.with("student_level", student_level);
            model.with("coolcoins_amount", coolcoins_amount);
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}