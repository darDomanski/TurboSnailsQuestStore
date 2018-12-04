package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Person;

public interface LoginDAO {

    Person getPersonByLoginPassword(String login, String password);
    void addPerson(int id, String login, String password);
    void updatePerson(int id, String value, String valueType);
    void deletePerson(int id);

    String getUserTypeById(int userId);

}
