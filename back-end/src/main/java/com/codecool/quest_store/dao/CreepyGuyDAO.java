package com.codecool.quest_store.dao;

public interface CreepyGuyDAO {

    void createClass(String className);
    int[] getAccessLevels();
    void updateAccessLevels(int[] update);
}
