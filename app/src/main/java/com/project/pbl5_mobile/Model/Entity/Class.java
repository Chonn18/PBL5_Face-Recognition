package com.project.pbl5_mobile.Model.Entity;

public class Class {
    int idclass;
    String classname;

    public int getIdclass() {
        return idclass;
    }

    public void setIdclass(int idclass) {
        this.idclass = idclass;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Class(int idclass, String classname) {
        this.idclass = idclass;
        this.classname = classname;
    }
}
