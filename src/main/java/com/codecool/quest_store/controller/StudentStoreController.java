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
import java.util.ArrayList;
import java.util.List;


public class StudentStoreController implements HttpHandler {
    private DBConnector connectionPool;
    private SessionResolver sessionResolver;
    private SessionDAO sessionDAO;
    private int userId;
    private int student_level;
    private LevelsDAO levelsDAO;



    public StudentStoreController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        sessionResolver = new SessionResolver(httpExchange, connectionPool);
        sessionResolver.checkIfSessionIsValid();
        String response = "";
        String method = httpExchange.getRequestMethod();

        // Probably should be in view
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/store.twig");
        JtwigModel model = JtwigModel.newModel();

        String cookieString = httpExchange.getRequestHeaders().getFirst("Cookie");
        if (cookieString != null) {
            HttpCookie cookie = HttpCookie.parse(cookieString).get(0);
            String sesionNumber = cookie.getValue();
            sessionDAO = new SessionDAOImpl(connectionPool);
            userId = sessionDAO.getUserIdBySession( sesionNumber );

            levelsDAO = new LevelsDAOImpl(connectionPool);
            System.out.println(userId);
            student_level = levelsDAO.getStudentLevel(userId);
            System.out.println(student_level);
        }else {
            System.out.println("There is no cookie");
        }

        ItemDAO items = new ArtifactDAO(connectionPool);
        LevelsDAOImpl levelsDAO = new LevelsDAOImpl(connectionPool);

        List<Item> artifactsBasic = items.getAllBasic();
        List<Item> artifactsExtra = items.getAllExtra();
        int userId = getUserIdBySessionId(httpExchange);
        int studentLevel = levelsDAO.getStudentLevel(userId);

        List<Item> basicStudentArtifacts = getArtifactsByStudentLevel(artifactsBasic, studentLevel);
        List<Item> magicStudentArtifacts = getArtifactsByStudentLevel(artifactsExtra, studentLevel);

        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){

            model.with("student_level", student_level);
            model.with("artifactsBasic", artifactsBasic);
            model.with("artifactsExtra", artifactsExtra);
            model.with("artifactsBasic", basicStudentArtifacts);
            model.with("artifactsExtra", magicStudentArtifacts);
            response = template.render(model);

            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        // If the form was submitted, retrieve it's content.
        if(method.equals("POST")){

            model.with("student_level", student_level);
            model.with("artifactsBasic", artifactsBasic);
            model.with("artifactsExtra", artifactsExtra);
            response = template.render(model);

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

}