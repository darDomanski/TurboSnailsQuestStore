package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.*;
import com.codecool.quest_store.model.Item;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.List;


public class StudentInventoryController implements HttpHandler {
    private DBConnector connectionPool;
    private SessionDAO sessionDAO;
    private InventoryDAO inventoryDAO;
    private Integer userId;
    private Integer student_level;
    private LevelsDAO levelsDAO;


    public StudentInventoryController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
        this.sessionDAO = new SessionDAOImpl(connectionPool);
        this.inventoryDAO = new InventoryDAOimpl(connectionPool);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SessionResolver sessionResolver = new SessionResolver(httpExchange, connectionPool);
        sessionResolver.checkIfSessionIsValid();
        String response = "";
        String method = httpExchange.getRequestMethod();
        int userId = getUserIdBySessionId(httpExchange);

        List<Item> inventory = inventoryDAO.getListOfItemsByUserId(userId);

        // Probably should be in view
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/inventory.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("inventory", inventory);

        String cookieString = httpExchange.getRequestHeaders().getFirst("Cookie");
        if (cookieString != null) {
            HttpCookie cookie = HttpCookie.parse(cookieString).get(0);
            String sesionNumber = cookie.getValue();
            sessionDAO = new SessionDAOImpl(connectionPool);
            userId = sessionDAO.getUserIdBySession(sesionNumber);

            levelsDAO = new LevelsDAOImpl(connectionPool);
            student_level = levelsDAO.getStudentLevel(userId);
        }

        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){

            model.with("student_level", student_level);
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        // If the form was submitted, retrieve it's content.
        if(method.equals("POST")){

            model.with("student_level", student_level);
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
            userId = this.sessionDAO.getUserIdBySession(cookie.getValue());
        }
        return userId;
    }
}
