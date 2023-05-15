package com.project.pbl5_mobile.Model.Entity;

public class UserCheck {
    String urlimg;
    String time;
    Integer id;
    String name;
    Boolean ischeck;

    public UserCheck(String urlimg, String time) {
        this.urlimg = urlimg;
        this.time = time;

    }

    public UserCheck() {

    }

    public UserCheck(String urlimg, String time, Integer id, String name, Boolean ischeck) {
        this.urlimg = urlimg;
        this.time = time;
        this.id = id;
        this.name = name;
        this.ischeck = ischeck;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIscheck() {
        return ischeck;
    }

    public void setIscheck(Boolean ischeck) {
        this.ischeck = ischeck;
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
