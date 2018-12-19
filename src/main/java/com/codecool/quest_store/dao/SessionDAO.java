package com.codecool.quest_store.dao;

public interface SessionDAO {
    void addSession(int userId, String sessionId);

    void removeSession(String sessionId);

    int getUserIdBySession(String sessionId);
}
