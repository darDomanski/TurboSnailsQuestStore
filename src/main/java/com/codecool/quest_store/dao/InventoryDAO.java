package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Item;

import java.util.List;


public interface InventoryDAO {
    List<Item> getListOfItemsByUserId(int userId);
    void addArtifactToStudentInventory(int studentId, int artifactId);
}
