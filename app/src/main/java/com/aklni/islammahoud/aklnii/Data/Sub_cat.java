package com.aklni.islammahoud.aklnii.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by islam mahoud on 1/19/2018.
 */

public class Sub_cat implements Parcelable {
    int id ,foodCat_id,client_id;
    String itemName;
    String itemImage;
    String price;
    String itemDescription;
    String FoodCategory;
    String discount;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Sub_cat()
{}
    protected Sub_cat(Parcel in) {
        id = in.readInt();
        foodCat_id = in.readInt();
        client_id = in.readInt();
        itemName = in.readString();
        itemImage = in.readString();
        price = in.readString();
        itemDescription = in.readString();
        FoodCategory = in.readString();
        discount=in.readString();
    }

    public static final Creator<Sub_cat> CREATOR = new Creator<Sub_cat>() {
        @Override
        public Sub_cat createFromParcel(Parcel in) {
            return new Sub_cat(in);
        }

        @Override
        public Sub_cat[] newArray(int size) {
            return new Sub_cat[size];
        }
    };

    public int getId() {
        return id;
    }
    public int getFoodCat_id() {
        return foodCat_id;
    }
    public int getClient_id() {
        return client_id;
    }
    public String getItemName(){return itemName;}
    public String getItemImage(){return itemImage;}
    public String getPrice(){return price;}

    public String getItemDescription() {
        return itemDescription;
    }

    public void setId(int id){this.id=id;}
    public void setFoodCat_id(int foodCat_id){this.foodCat_id=foodCat_id;}
    public void setClient_id(int client_id){this.client_id=client_id;}
    public void setItemName(String itemName){this.itemName=itemName;}
    public void setItemImage(String itemImage){this.itemImage=itemImage;}
    public void setPrice(String price){this.price=price;}

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setFoodCategory(String foodCategory) {
        FoodCategory = foodCategory;
    }

    public String getFoodCategory() {
        return FoodCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(foodCat_id);
        dest.writeInt(client_id);
        dest.writeString(itemName);
        dest.writeString(itemImage);
        dest.writeString(price);
        dest.writeString(itemDescription);
        dest.writeString(FoodCategory);
        dest.writeString(discount);
    }
}
