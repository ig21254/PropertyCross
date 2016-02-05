package com.lasalle.second.part.propertycross.model;

import android.net.Uri;

import java.util.Date;

/**
 * Created by eduard on 05/02/2016.
 */
public class Comment {
    private String author;
    private Date date;
    private String text;
    private Uri photo;

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

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }
}
