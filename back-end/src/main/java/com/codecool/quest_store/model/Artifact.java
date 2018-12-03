package com.codecool.quest_store.model;

public class Artifact extends Item {

    // fild from Item
//    protected Integer id;
//    protected Integer access_level;
//    protected String title;
//    protected String decription;
//    protected Integer value;
//    protected String type;

    public Artifact(Integer id, Integer access_level, String title, String description, Integer value, String type) {
        this.id = id;
        this.access_level = access_level;
        this.title = title;
        this.description = description;
        this.value = value;
        this.type = type;
    }



}
