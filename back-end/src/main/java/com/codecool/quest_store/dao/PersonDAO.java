package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Person;

import java.util.List;

public interface PersonDAO {
    List<Person> getAll();
    void insert(Person person);
    void update(int id, String column, String newValue);
    void delete(int id);
}
