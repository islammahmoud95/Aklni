package com.aklni.islammahoud.aklnii.Data;

import java.io.Serializable;

/**
 * Created by islam mahoud on 1/19/2018.
 */

public class Client implements Serializable {
    int id,start,enditem;
    String name;
    String description;
    String address;
    String address2;
    String phone;
    String open;
    String logo;
    int active;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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

    public int getStart() {
        return start;
    }

    public int getEnditem() {
        return enditem;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnditem(int enditem) {
        this.enditem = enditem;
    }
}
