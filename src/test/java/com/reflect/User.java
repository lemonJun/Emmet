package com.reflect;

public class User {
    private int id;
    private String name;

    public String getName(int i) {
        return name + i;
    }

    public String getName(String i) {
        return name + i;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
