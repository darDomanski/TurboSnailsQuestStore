package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.DBConnector;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;


public class CreepyGuyClassesController implements HttpHandler {
    private DBConnector connectionPool;

    public CreepyGuyClassesController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SessionResolver sessionResolver = new SessionResolver(httpExchange, connectionPool);
        sessionResolver.checkIfSessionIsValid();
        String response = "";
        String method = httpExchange.getRequestMethod();

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/creepyguy/classes.twig");
        JtwigModel model = JtwigModel.newModel();

        if(method.equals("GET")){
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(method.equals("POST")){
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
