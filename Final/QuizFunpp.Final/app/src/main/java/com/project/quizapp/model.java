package com.project.quizapp;

public class model {
    String Name,Review;
    model(){

    }

    public model(String name, String review) {
        Name = name;
        Review = review;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }
}
