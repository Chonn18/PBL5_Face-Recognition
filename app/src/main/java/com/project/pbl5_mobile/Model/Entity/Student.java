package com.project.pbl5_mobile.Model.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Student implements Parcelable {
    private Integer id;
    private Integer idclass;
    private String name;
    private String date;
    private String avatar;
    private String sex;


    public Student(Integer id, Integer time, String name, String date, String avatar, String sex) {
        this.id = id;
        this.idclass = time;
        this.name = name;
        this.date = date;
        this.avatar = avatar;
        this.sex = sex;
    }

    public Student(Integer id, Integer time, String name, String date, String sex) {
        this.id = id;
        this.idclass = time;
        this.name = name;
        this.date = date;
        this.sex = sex;
    }

    public Student(){
        this.avatar="";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassid() {
        return idclass;
    }

    public void setClassid(Integer classid) {
        this.idclass = classid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    protected Student(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
            idclass = in.readInt();
        }

        name = in.readString();
        date = in.readString();
        avatar = in.readString();
        sex = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(String.valueOf(idclass));
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(avatar);
        dest.writeString(sex);
    }
}
