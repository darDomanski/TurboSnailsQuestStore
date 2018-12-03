package com.codecool.quest_store;

import com.codecool.quest_store.controller.CreepyGuyController;
import com.codecool.quest_store.controller.LoginController;
import com.codecool.quest_store.controller.MentorController;
import com.codecool.quest_store.controller.StudentController;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;


public class App {
    public static void main(String[] args) throws Exception {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/login", new LoginController());

        server.createContext("/creepyguy/classes", new CreepyGuyController());
        server.createContext("/creepyguy/mentors", new CreepyGuyController());
        server.createContext("/creepyguy/levels", new CreepyGuyController());

        server.createContext("/mentor/codecoolers", new MentorController());
        server.createContext("/mentor/quests", new MentorController());
        server.createContext("/mentor/artifacts", new MentorController());

        server.createContext("/student/store", new StudentController());
        server.createContext("/student/crowdfunding", new StudentController());
        server.createContext("/student/artifacts", new StudentController());
        server.createContext("/student/inventory", new StudentController());
        server.createContext("/student/wallet", new StudentController());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}
