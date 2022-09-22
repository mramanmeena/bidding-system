package com.example.biddingsystem.entity;

import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;

@Document(value = "bid")

public class Bid {

    @Id
    private String bid_id;
    private String user_id;
    private Integer bid_amount;
    private String Status;

    private String item_id;

    public Bid() {
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public Bid(String bid_id, String user_id, Integer bid_amount, String status, String item_id) {
        this.bid_id = bid_id;
        this.user_id = user_id;
        this.bid_amount = bid_amount;
        Status = status;
        this.item_id = item_id;
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

    public Integer getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(Integer bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    }

