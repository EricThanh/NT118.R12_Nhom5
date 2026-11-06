package com.example.nt118r12.models;

import java.io.Serializable;

public class SellerOrder implements Serializable {
    public static final String STATUS_NEW = "New";
    public static final String STATUS_TO_SHIP = "To ship";
    public static final String STATUS_SHIPPING = "Shipping";
    public static final String STATUS_COMPLETE = "Complete";

    private String orderId;
    private String orderDate;
    private String buyerName;
    private double totalPrice;
    private String status;

    public SellerOrder() {
    }

    public SellerOrder(String orderId, String orderDate, String buyerName, double totalPrice, String status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.buyerName = buyerName;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
