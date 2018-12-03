package com.codecool.quest_store.model;


public class Quest extends Item {

    public Quest(Integer id, Integer access_level, String title, String decription, Integer value, String type) {
        this.id = id;
        this.access_level = access_level;
        this.title = title;
        this.description = decription;
        this.value = value;
        this.type = type;
    }



}
