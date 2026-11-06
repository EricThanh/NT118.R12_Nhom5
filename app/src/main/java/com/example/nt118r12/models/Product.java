package com.example.nt118r12.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private double price;
    private boolean isFavorite;
    private int colorsCount;

    public Product() {
    }

    public Product(String name, double price, boolean isFavorite) {
        this(name, price, 3, isFavorite);
    }

    public Product(String name, double price, int colorsCount, boolean isFavorite) {
        this.name = name;
        this.price = price;
        this.colorsCount = colorsCount;
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

    public int getColorsCount() {
        return colorsCount;
    }

    public void setColorsCount(int colorsCount) {
        this.colorsCount = colorsCount;
    }
}
