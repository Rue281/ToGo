package com.bricksai.togo.models;

import android.graphics.Bitmap;

/**
 * Created by Remonda on 5/3/2017.
 */

public class MenuModel {
    int id;
    String name;
    Double price;
    String image;
    String description;

    public MenuModel(int id, String name, Double price, String image, String description){
        this.id =id;
        this.name=name;
        this.image=image;
        this.price=price;
        this.description=description;

    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
