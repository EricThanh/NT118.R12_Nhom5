package com.example.nt118r12.models;

import java.io.Serializable;

public class Order implements Serializable {

    public static final int STATUS_PENDING = 0;
    public static final int STATUS_SHIPPING = 1;
    public static final int STATUS_DELIVERED = 2;
    public static final int STATUS_CANCELLED = 3;

    private final String id;
    private final String date;
    private final String productName;
    private final String productMeta;
    private final double productPrice;
    private final int itemCount;
    private final double total;
    private final int status;

    public Order(String id, String date, String productName, String productMeta,
                 double productPrice, int itemCount, double total, int status) {
        this.id = id;
        this.date = date;
        this.productName = productName;
        this.productMeta = productMeta;
        this.productPrice = productPrice;
        this.itemCount = itemCount;
        this.total = total;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductMeta() {
        return productMeta;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public double getTotal() {
        return total;
    }

    public int getStatus() {
        return status;
    }
}
