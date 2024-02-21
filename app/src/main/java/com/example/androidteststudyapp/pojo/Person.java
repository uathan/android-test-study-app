package com.example.androidteststudyapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private int id;
    private String fname;
    private String lname;
    private String age;

    //This creates records in database
    public Person(String fname, String lname, String age) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
    }
    //This reads records in the database
    public Person(int id, String fname, String lname, String age) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
    }

    public Person(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return getFname();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(fname);
        parcel.writeString(lname);
        parcel.writeString(age);
    }

    protected Person(Parcel in) {
        id = in.readInt();
        fname = in.readString();
        lname = in.readString();
        age = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

}
