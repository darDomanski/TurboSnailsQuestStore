package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.*;
import com.codecool.quest_store.model.Person;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpCookie;

public class SessionResolver {
    private HttpExchange httpExchange;
    private RedirectController redirectController;
    private DBConnector connectionPool;
    private SessionDAO sessionDAO;
    private PersonDAO personDAO;

    public SessionResolver(HttpExchange httpExchange, DBConnector connectionPool) {
        this.httpExchange = httpExchange;
        this.redirectController = new RedirectController();
        this.connectionPool = connectionPool;
        this.sessionDAO = new SessionDAOImpl(connectionPool);
        this.personDAO = new QSUserDAO(connectionPool);
    }

    public void checkIfSessionIsValid() throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

        if (cookieStr == null) {
            redirectController.redirect(httpExchange, "login");
        } else {
            HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
            int userId = sessionDAO.getUserIdBySession(cookie.getValue());
            String requestURI = httpExchange.getRequestURI().toString();

            if (userId == 0) {
                if (!requestURI.contains("creepyguy")) {
                    redirectController.redirect(httpExchange, "login");
                }
            } else {
                Person user = personDAO.getPersonById(userId);
                if (!requestURI.contains(user.getUserType())) {
                    redirectController.redirect(httpExchange, "login");
                }
            }
        }
    }
}
