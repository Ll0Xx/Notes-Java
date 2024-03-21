package com.antont.notesjava.db.entity;

public class Note {
    private Integer uid = 0;
    private String title;
    private String content;

    public Note(Integer uid, String title, String content) {
        this.uid = uid;
        this.title = title;
        this.content = content;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}