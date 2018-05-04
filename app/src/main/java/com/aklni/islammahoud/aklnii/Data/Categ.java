package com.aklni.islammahoud.aklnii.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by islam mahoud on 1/19/2018.
 */

public class Categ implements Parcelable {
    int foodCat_id,client_id;
    String catName;
    String name;
    String description;
    String address;
    String address2;
    String phone;
    String open;
    String logo;
    int id;

    public Categ()
    {

    }
    public Categ(Parcel in) {
        foodCat_id = in.readInt();
        client_id = in.readInt();
        catName = in.readString();
        name = in.readString();
        description = in.readString();
        address = in.readString();
        address2 = in.readString();
        phone = in.readString();
        open = in.readString();
        logo = in.readString();
        id = in.readInt();
    }

    public static final Creator<Categ> CREATOR = new Creator<Categ>() {
        @Override
        public Categ createFromParcel(Parcel in) {
            return new Categ(in);
        }

        @Override
        public Categ[] newArray(int size) {
            return new Categ[size];
        }
    };

    public int getFoodCat_id() {
        return foodCat_id;
    }

    public String getCatName() {
        return catName;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setFoodCat_id(int foodCat_id) {
        this.foodCat_id = foodCat_id;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getPhone() {
        return phone;
    }

    public String getOpen() {
        return open;
    }

    public String getLogo() {
        return logo;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOpen(String open) {
        this.open = open;
    }


    public void setLogo(String logo){this.logo=logo;}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(foodCat_id);
        dest.writeInt(client_id);
        dest.writeString(catName);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(address);
        dest.writeString(address2);
        dest.writeString(phone);
        dest.writeString(open);
        dest.writeString(logo);
        dest.writeInt(id);
    }
}
