package com.example.biddingsystem.entity;

public class bid {
    private String bid_id;
    private String user_id;
    private String bid_amount;
    private String Status;

    public bid(String bid_id, String user_id, String bid_amount, String status) {
        this.bid_id = bid_id;
        this.user_id = user_id;
        this.bid_amount = bid_amount;
        Status = status;
    }

    public String getBid_id() {
        return bid_id;
    }

    public void setBid_id(String bid_id) {
        this.bid_id = bid_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(String bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
