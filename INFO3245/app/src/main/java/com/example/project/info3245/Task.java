package com.example.project.info3245;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Task {

    private int id;
    private String title;
    private Date date;
    private int priority;
    private String label;
    private boolean completed;

    public Task(int id, String title, Date date, int priority, boolean completed){
        this.id = id;
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.completed = completed;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isComplete() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDateFormatted(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
        return dateFormat.format(date);

    }
}