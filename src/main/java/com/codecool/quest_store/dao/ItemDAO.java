package com.codecool.quest_store.dao;


import com.codecool.quest_store.model.Item;

import java.util.List;

public interface ItemDAO {

    List<Item> getAll();
    List<Item> getAllBasic();
    List<Item> getAllExtra();
    Item getById(Integer id);
    void add(Item item );
    void update(Integer id);
    void delete(Integer id);

}
