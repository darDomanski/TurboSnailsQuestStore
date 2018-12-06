package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Item;

import java.util.List;


public interface InventoryDAO {
    List<Item> getListOfItemsByUserId(String userId);
}
