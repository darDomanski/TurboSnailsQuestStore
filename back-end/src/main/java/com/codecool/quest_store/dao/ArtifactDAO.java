package com.codecool.quest_store.dao;


import com.codecool.quest_store.model.Item;
import com.codecool.quest_store.model.Quest;

import java.util.ArrayList;
import java.util.List;

public class ArtifactDAO implements ItemDAO {


    List<Quest> artifacts = new ArrayList<>();


    @Override
    public List<Item> getAll() {
        return null;
    }


    @Override
    public Item getById(Integer id) {
        return null;
    }

    @Override
    public void add(Item item ) {

    }

    @Override
    public void update(Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
