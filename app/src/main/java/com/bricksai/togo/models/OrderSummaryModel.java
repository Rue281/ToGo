package com.bricksai.togo.models;

/**
 * Created by Remonda on 5/18/2017.
 */

public class OrderSummaryModel {
    String id;
    String item;
    Double price;
    int quantity;

    public String getItem() {
        return item;
    }

    public Double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }


    public OrderSummaryModel(String item, Double price, int quantity){
        this.item=item;
        this.price=price;
        this.quantity=quantity;
    }
    public OrderSummaryModel(String id, String item, int quantity){
        this.item=item;
        this.quantity=quantity;
    }
}
