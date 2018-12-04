package com.codecool.quest_store;

import com.codecool.quest_store.controller.*;
import com.codecool.quest_store.dao.DBConnector;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;


public class App {
    public static void main(String[] args) throws Exception {

        // Initialize connection pool
        DBConnector connectionPool = new DBConnector();

        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/login", new LoginController(connectionPool));

        server.createContext("/creepyguy/classes", new CreepyGuyClassesController(connectionPool));
        server.createContext("/creepyguy/mentors", new CreepyGuyMentorsController(connectionPool));
        server.createContext("/creepyguy/levels", new CreepyGuyLevelsController(connectionPool));

        server.createContext("/mentor/codecoolers", new MentorCodecoolersController(connectionPool));
        server.createContext("/mentor/quests", new MentorQuestsController(connectionPool));
        server.createContext("/mentor/artifacts", new MentorArtifactsController(connectionPool));

        server.createContext("/student/store", new StudentStoreController(connectionPool));
        server.createContext("/student/crowdfunding", new StudentCrowdfundingController(connectionPool));
        server.createContext("/student/artifacts", new StudentArtifactsController(connectionPool));
        server.createContext("/student/inventory", new StudentInventoryController(connectionPool));
        server.createContext("/student/wallet", new StudentWalletController(connectionPool));

        server.createContext("/static", new StaticController());

        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();




    }
}
