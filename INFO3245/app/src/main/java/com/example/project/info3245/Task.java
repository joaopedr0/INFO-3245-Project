package com.example.project.info3245;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    private String title;
    private Date date;
    private int priority;
    private String label;
    private boolean complete;

    public Task(String title, Date date, int priority){
        this.title = title;
        this.date = date;
        this.priority = priority;
        complete = false;
    }

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
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getDateFormatted(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
        return dateFormat.format(date);

    }
}
