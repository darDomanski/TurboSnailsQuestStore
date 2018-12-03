package com.codecool.quest_store.dao;

public interface LevelsDAO {
    int getStudentLevel(int studentId);
    void updateLevel(int levelId, int minCoolcoinsAmount, int maxCoolcoinsAmount);
}
