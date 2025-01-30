package com.example.sqlapp;

public class Comment {
    private String name;
    private String body;

    public Comment(String body, String name) {
        this.body = body;
        this.name = name;
    }

    public Comment(){

    }

    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
