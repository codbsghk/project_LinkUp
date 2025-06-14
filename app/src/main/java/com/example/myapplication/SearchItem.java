package com.example.myapplication;

public class SearchItem {
    private String name;
    private String type; // "member" 또는 "group"

    public SearchItem(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }
    public String getType() { return type; }
}
