package com.lasalle.second.part.propertycross.model;

import java.util.Date;

/**
 * Created by eduard on 05/02/2016.
 */
public class Comment {
    private String author;
    private Date date;
    private String text;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
