package com.Badhwar.journalApp.entity;

//POJO File

public class JournalEntry {
    private int id;

    private String title;

    private String content;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setContent(String content) {
        this.content = content;
    }
}
