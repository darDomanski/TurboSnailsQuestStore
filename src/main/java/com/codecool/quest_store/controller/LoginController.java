package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.LoginDAO;
import com.codecool.quest_store.dao.LoginDAOImpl;
import com.codecool.quest_store.model.Person;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Map;


public class LoginController implements HttpHandler {
    private Connection connection;
    private RedirectController redirectController;
    private LoginDAO loginDAO;

    public LoginController(Connection connection) {
        this.connection = connection;
        this.loginDAO = new LoginDAOImpl(this.connection);
        this.redirectController = new RedirectController();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        // Probably should be in view

        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){

            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
            JtwigModel model = JtwigModel.newModel();
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        // If the form was submitted, retrieve it's content.
        if (method.equals("POST")) {
            String context;
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map inputs = redirectController.parseFormData(formData);
            String username = (String) inputs.get("login");
            String password = (String) inputs.get("password");

            Person person = loginDAO.getPersonByLoginPassword(username, password);

            if (person != null) {
                String userType = loginDAO.getUserTypeById(person.getId());
                if (userType.equals("mentor")) {
                    context = "mentor/codecoolers";
                } else if (userType.equals("student")) {
                    context = "student/store";
                } else {
                    context = "creepyguy/classes";
                }
            } else {
                context = "login";
            }
            redirectController.redirect(httpExchange, context);

        }
    }
}
