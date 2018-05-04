package com.aklni.islammahoud.aklnii.Data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by islam mahoud on 1/30/2018.
 */

public class Cart implements Parcelable{
    int id;
    String name,num,price,comment,extra,foodcategory;
    int check;

    public String getFoodcategory() {
        return foodcategory;
    }

    public void setFoodcategory(String foodcategory) {
        this.foodcategory = foodcategory;
    }

    protected Cart(Parcel in) {
        id = in.readInt();
        name = in.readString();
        num = in.readString();
        price = in.readString();
        comment = in.readString();
        extra = in.readString();
        foodcategory=in.readString();
        check=in.readInt();

    }
public Cart(){}
    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getNum() {
        return num;
    }

    public String getExtra() {
        return extra;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(check);
        dest.writeString(name);
        dest.writeString(num);
        dest.writeString(price);
        dest.writeString(comment);
        dest.writeString(extra);
        dest.writeString(foodcategory);
    }
}
