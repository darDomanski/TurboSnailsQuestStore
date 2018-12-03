package com.codecool.quest_store.dao;

public interface WalletDAO {
    int getStudentsCoolcoinsAmount(int studentId);
    void increaseCoolcoinsAmount(int coolcoinsAmount, int studentId);
    void decreaseCoolcoinsAmount(int coolcoinsAmount, int studentId);
}
