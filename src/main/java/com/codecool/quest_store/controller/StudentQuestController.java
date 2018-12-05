package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.DBConnector;
import com.codecool.quest_store.dao.ItemDAO;
import com.codecool.quest_store.dao.QuestDAO;
import com.codecool.quest_store.model.Item;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


public class StudentQuestController implements HttpHandler {
    private DBConnector connectionPool;

    public StudentQuestController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        // Probably should be in view
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/quests.twig");
        JtwigModel model = JtwigModel.newModel();


        ItemDAO items = new QuestDAO((connectionPool));
        List<Item> questsBasic = items.getAllBasic();
        List<Item> questsExtra = items.getAllExtra();


        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){

            model.with("questsBasic", questsBasic);
            model.with("questsExtra", questsExtra);
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        // If the form was submitted, retrieve it's content.
        if(method.equals("POST")){

            model.with("questsBasic", questsBasic);
            model.with("questsExtra", questsExtra);
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
