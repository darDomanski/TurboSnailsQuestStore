package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.DBConnector;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;


public class MentorQuestsController implements HttpHandler {
    private DBConnector connectionPool;

    public MentorQuestsController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
