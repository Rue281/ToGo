package com.bricksai.togo.models;

/**
 * Created by Remonda on 5/15/2017.
 */

public class RestaurantModel {
    public RestaurantModel(int id, String name, String phone, String email, String city, String address, String img, String type, Double x, Double y,String open_time,String close_time){
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.city=city;
        this.address=address;
        this.img=img;
        this.type=type;
        this.x=x;
        this.y=y;
        this.open_time=open_time;
        this.close_time=close_time;
    }
    private int id;
    private String name;
    private String phone;
    private String email;
    private String city;
    private String address;
    private String img;
    private String type;

    public String getOpen_time() {
        return open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    private String open_time;
    private String close_time;
    private Double x,y;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getImg() {
        return img;
    }

    public String getType() {
        return type;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }


}
