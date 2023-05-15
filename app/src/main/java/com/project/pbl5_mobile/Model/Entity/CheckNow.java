package com.project.pbl5_mobile.Model.Entity;

public class CheckNow {
    String time;
    String url;

    public CheckNow(String time, String url) {
        this.time = time;
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
