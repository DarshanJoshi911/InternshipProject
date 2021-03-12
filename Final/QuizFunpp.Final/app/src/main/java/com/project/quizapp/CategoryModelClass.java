package com.project.quizapp;

//Model class to fetch the quiz subjects from the Cloud Firestone
public class CategoryModelClass {
    //private variable for ID and the name of
    private String id;
    private String name;

    //constructor
    public CategoryModelClass(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
