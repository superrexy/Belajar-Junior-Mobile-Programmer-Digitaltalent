package com.dts.haripertama.DailyNote;

import androidx.annotation.NonNull;

import java.util.Date;

public class NoteModel {
    private int id;
    private String title;
    private String description;
    private String date;
    private String username;

    NoteModel(int id, @NonNull String title, @NonNull String description, @NonNull String date, @NonNull String username) {
        this.id = id != 0 ? id : (int) new Date().getTime();
        this.title = title;
        this.description = description;
        this.date = date;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @NonNull
    public String toString() {
        return "NoteModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", username='" + username + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"id\":" + id + "," +
                "\"title\":\"" + title + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"date\":\"" + date + "\"," +
                "\"author\":\"" + username + "\"" +
                "}";
    }
}
