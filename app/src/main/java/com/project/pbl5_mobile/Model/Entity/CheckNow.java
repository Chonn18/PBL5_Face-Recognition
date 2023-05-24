package com.project.pbl5_mobile.Model.Entity;

public class CheckNow {
    String url;
    String time;

    public CheckNow(String url, String time) {
        this.url = url;
        this.time = time;
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
