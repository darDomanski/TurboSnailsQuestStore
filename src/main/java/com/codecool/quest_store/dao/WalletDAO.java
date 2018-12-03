package com.codecool.quest_store.dao;

public interface WalletDAO {
    int getStudentsCoolcoinsAmount(int studentId, String columnName);
    void changeCoolcoinsAmount(int coolcoinsAmount, String columname, int studentId);
}
