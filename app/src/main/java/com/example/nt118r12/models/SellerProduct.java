package com.example.nt118r12.models;

import java.io.Serializable;

public class SellerProduct implements Serializable {
    public static final String STATUS_ACTIVE = "Active";
    public static final String STATUS_OUT_OF_STOCK = "Out of stock";
    public static final String STATUS_HIDDEN = "Hidden";

    private String name;
    private double price;
    private String status;
    private int stock;

    public SellerProduct(String name, double price, String status, int stock) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public int getStock() {
        return stock;
    }
}
