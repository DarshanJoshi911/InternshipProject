package com.project.quizapp;

public class histmodel {
    String cat, email, score, set;

    //empty constructor for firebase instance o work properly
    public histmodel(){

    }
    public histmodel(String cat, String email, String score, String set) {
        this.cat = cat;
        this.email = email;
        this.score = score;
        this.set = set;
    }
    //getter setter methods
    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }
}
