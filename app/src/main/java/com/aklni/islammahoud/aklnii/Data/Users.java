package com.aklni.islammahoud.aklnii.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by islam mahoud on 1/19/2018.
 */

public class Users implements Parcelable {
    int id;
    String name;
    String phone;

    public Users()
    {}
    protected Users(Parcel in) {
        id = in.readInt();
        name = in.readString();
        phone = in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(phone);
    }
}
