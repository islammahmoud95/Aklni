package com.aklni.islammahoud.aklnii.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by islam mahoud on 2/9/2018.
 */

public class Extra implements Parcelable {
    int extra_id;
    String name,price;

    protected Extra(Parcel in) {
        extra_id = in.readInt();
        name = in.readString();
        price = in.readString();
    }
    public Extra()
    {}

    public static final Creator<Extra> CREATOR = new Creator<Extra>() {
        @Override
        public Extra createFromParcel(Parcel in) {
            return new Extra(in);
        }

        @Override
        public Extra[] newArray(int size) {
            return new Extra[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getExtra_id() {
        return extra_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setExtra_id(int extra_id) {
        this.extra_id = extra_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(extra_id);
        dest.writeString(name);
        dest.writeString(price);
    }
}
