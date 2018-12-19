package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.*;
import com.codecool.quest_store.model.Person;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;

public class SessionController implements HttpHandler {
    private DBConnector connectionPool;
    private SessionDAO sessionDAO;
    private PersonDAO personDAO;
    private RedirectController redirectController;

    public SessionController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
        this.sessionDAO = new SessionDAOImpl(connectionPool);
        this.personDAO = new QSUserDAO(this.connectionPool);
        this.redirectController = new RedirectController();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String context = "";
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

        if (cookieStr == null) {
            context = "login";
        } else {
            HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
            int userId = sessionDAO.getUserIdBySession(cookie.getValue());
            if (userId == 0) {
                context = "creepyguy/classes";
            } else {
                Person user = personDAO.getPersonById(userId);
                if (user.getUserType().equals("student")) {
                    context = "student/store";
                } else if (user.getUserType().equals("mentor")) {
                    context = "mentor/codecoolers";
                }
            }
        }
        redirectController.redirect(httpExchange, context);
    }
}
