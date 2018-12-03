package com.codecool.quest_store;

import com.codecool.quest_store.controller.*;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;


public class App {
    public static void main(String[] args) throws Exception {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/login", new LoginController());

        server.createContext("/creepyguy/classes", new CreepyGuyClassesController());
        server.createContext("/creepyguy/mentors", new CreepyGuyMentorsController());
        server.createContext("/creepyguy/levels", new CreepyGuyLevelsController());

        server.createContext("/mentor/codecoolers", new MentorCodecoolersController());
        server.createContext("/mentor/quests", new MentorQuestsController());
        server.createContext("/mentor/artifacts", new MentorArtifactsController());

        server.createContext("/student/store", new StudentStoreController());
        server.createContext("/student/crowdfunding", new StudentCrowdfundingController());
        server.createContext("/student/artifacts", new StudentArtifactsController());
        server.createContext("/student/inventory", new StudentInventoryController());
        server.createContext("/student/wallet", new StudentWalletController());

        server.createContext("/static", new StaticController());

        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}
