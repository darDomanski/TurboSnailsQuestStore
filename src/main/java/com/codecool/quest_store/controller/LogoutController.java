package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.DBConnector;
import com.codecool.quest_store.dao.SessionDAO;
import com.codecool.quest_store.dao.SessionDAOImpl;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;

public class LogoutController implements HttpHandler {
    private SessionDAO sessionDAO;
    private RedirectController redirectController;
    private DBConnector connectionPool;

    public LogoutController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
        this.sessionDAO = new SessionDAOImpl(connectionPool);
        this.redirectController = new RedirectController();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String cookieString = httpExchange.getRequestHeaders().getFirst("Cookie");
        if (cookieString != null) {
            HttpCookie sessionIdCookie = HttpCookie.parse(cookieString).get(0);
            sessionDAO.removeSession(sessionIdCookie.getValue());
        }

        HttpCookie cookie = new HttpCookie("sessionId", "token=deleted; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT");
        cookie.setMaxAge(-1);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());

        redirectController.redirect(httpExchange, "login");
    }
}
