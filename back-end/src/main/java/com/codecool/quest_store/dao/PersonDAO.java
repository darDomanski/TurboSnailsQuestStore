package com.codecool.quest_store.dao;

public interface PersonDAO {
    List<Person> getAll();
    void insert(Person person);
    void update(int id, String column, String newValue);
    void delete(int id);
}
