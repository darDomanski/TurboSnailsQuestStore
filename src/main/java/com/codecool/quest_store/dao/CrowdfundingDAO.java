package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Item;

import java.util.List;

public interface CrowdfundingDAO {
    int getCollectedMoneyByArtifactId(int artifactId);

    void addCollectedMoney(int amountOfMoney, int artifactId);

    List<Item> getAllItems();
}
