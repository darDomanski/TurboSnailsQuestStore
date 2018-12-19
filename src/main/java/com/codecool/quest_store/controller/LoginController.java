package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.*;
import com.codecool.quest_store.model.Person;
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
import java.util.Map;
import java.util.UUID;


public class LoginController implements HttpHandler {
    private DBConnector connectionPool;


    private RedirectController redirectController;
    private LoginDAO loginDAO;
    private SessionDAO sessionDAO;

    public LoginController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
        this.loginDAO = new LoginDAOImpl(this.connectionPool);
        this.sessionDAO = new SessionDAOImpl(this.connectionPool);
        this.redirectController = new RedirectController();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if(method.equals("GET")){
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
            JtwigModel model = JtwigModel.newModel();
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (method.equals("POST")) {
            removeSessionIfCookieExists(httpExchange);

            String context = "";
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map inputs = redirectController.parseFormData(formData);
            String username = (String) inputs.get("login");
            String password = (String) inputs.get("password");

            boolean userExists = loginDAO.checkIfUserExists(username, password);

            if (userExists) {
                Person person = loginDAO.getPersonByLoginPassword(username, password);
                HttpCookie cookie = new HttpCookie("sessionId", UUID.randomUUID().toString());
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                sessionDAO.addSession(person.getId(), cookie.getValue());

                if (person != null) {
                    String userType = loginDAO.getUserTypeById(person.getId());
                    if (userType.equals("mentor")) {
                        context = "mentor/codecoolers";
                    } else if (userType.equals("student")) {
                        context = "student/store";
                    }
                }
            } else {
                context = "login";
            }
            redirectController.redirect(httpExchange, context);
        }

    }

    private void removeSessionIfCookieExists(HttpExchange httpExchange) {
        String cookieString = httpExchange.getRequestHeaders().getFirst("Cookie");

        if (cookieString != null) {
            HttpCookie cookie = HttpCookie.parse(cookieString).get(0);
            sessionDAO.removeSession(cookie.getValue());
        }
    }
}