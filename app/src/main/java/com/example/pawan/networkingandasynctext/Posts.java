package com.example.pawan.networkingandasynctext;

/**
 * Created by Pawan on 01-10-2017.
 */

public class Posts {
    private int userId;
    private int Id;
    private String title;
    private String body;

    public Posts(int userId, int id, String title, String body) {
        this.userId = userId;
        Id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
