package com.project.pbl5_mobile.Model.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Student implements Parcelable {
    private Integer id;
    private String time;
    private String name;
    private String date;
    private String avatar;
    private Boolean sex;


    public Student(Integer id, String time, String name, String date, String avatar, Boolean sex) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.date = date;
        this.avatar = avatar;
        this.sex = sex;
    }

    public Student(Integer id, String time, String name, String date, Boolean sex) {
        this.id = id;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    protected Student(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        time = in.readString();
        name = in.readString();
        date = in.readString();
        avatar = in.readString();
        byte tmpSex = in.readByte();
        sex = tmpSex == 0 ? null : tmpSex == 1;
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
        dest.writeString(time);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(avatar);
        dest.writeByte((byte) (sex == null ? 0 : sex ? 1 : 2));
    }
}
