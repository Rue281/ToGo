package com.bricksai.togo.models;

/**
 * Created by Remonda on 5/4/2017.
 */

public class OrderHistoryModel {

    String time;
    String number;
    String price;

    public OrderHistoryModel(String number,String price,String time){
        this.time=time;
        this.price=price;
        this.number=number;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public String getNumber() {
        return number;
    }
}
