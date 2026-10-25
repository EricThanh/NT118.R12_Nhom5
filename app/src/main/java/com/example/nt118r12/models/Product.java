package com.example.nt118r12.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private double price;
    private boolean isFavorite;

    public Product(String name, double price, boolean isFavorite) {
        this.name = name;
        this.price = price;
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
