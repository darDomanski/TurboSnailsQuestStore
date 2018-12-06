package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Item;

import java.util.List;

public class InventoryDAOimpl implements InventoryDAO {
    private DBConnector connectionPool;

    public InventoryDAOimpl(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Item> getListOfItemsByUserId(String userId) {
        return null;
    }
}
