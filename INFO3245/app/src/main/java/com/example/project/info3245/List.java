package com.example.project.info3245;

import java.util.ArrayList;

public class List {

    private String title;
    private ArrayList<String> items;

    public List(String title, ArrayList<String> items){
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String>getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
}
