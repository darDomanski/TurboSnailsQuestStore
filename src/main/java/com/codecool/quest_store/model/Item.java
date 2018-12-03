package com.codecool.quest_store.model;

public abstract class Item {

    protected Integer id;
    protected Integer access_level;
    protected String title;
    protected String description;
    protected Integer value;
    protected String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccess_level() {
        return access_level;
    }

    public void setAccess_level(Integer access_level) {
        this.access_level = access_level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
