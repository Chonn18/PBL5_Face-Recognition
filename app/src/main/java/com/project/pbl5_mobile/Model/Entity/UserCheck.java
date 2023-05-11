package com.project.pbl5_mobile.Model.Entity;

public class UserCheck {
    String urlimg;
    String time;

    public UserCheck(String urlimg, String time) {
        this.urlimg = urlimg;
        this.time = time;
    }

    public UserCheck() {

    }

    public String getUrlimg() {
        return urlimg;
    }

    public void setUrlimg(String urlimg) {
        this.urlimg = urlimg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
