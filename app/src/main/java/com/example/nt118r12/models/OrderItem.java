package com.example.nt118r12.models;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private String productName;
    private String size;
    private double price;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(String productName, String size, double price, int quantity) {
        this.productName = productName;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
