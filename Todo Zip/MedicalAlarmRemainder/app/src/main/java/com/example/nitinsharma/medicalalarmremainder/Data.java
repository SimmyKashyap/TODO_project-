package com.example.nitinsharma.medicalalarmremainder;

/**
 * Created by nitin sharma on 28-Nov-18.
 */

public class Data {
    String title;
    int hour;
    int minute;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    String status;
    String title_status;
    String time_status;

    public String getTitle_status() {
        return title_status;
    }

    public void setTitle_status(String title_status) {
        this.title_status = title_status;
    }

    public String getTime_status() {
        return time_status;
    }

    public void setTime_status(String time_status) {
        this.time_status = time_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data(int id,String title, int hour, int minute, String status, String time_status, String title_status) {
        this.id=id;
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
        this.time_status = time_status;
        this.title_status = title_status;
    }
    public Data(String title, int hour, int minute, String status, String time_status, String title_status) {
        this.id=id;
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
        this.time_status = time_status;
        this.title_status = title_status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

}
