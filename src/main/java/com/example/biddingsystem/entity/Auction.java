package com.example.biddingsystem.entity;
import java.util.*;

import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;

@Document
public class Auction {

    @Id
    private String auction_id;
    private String base_price;
    private String duration;
    private String step_rate;
    private String item_id;
    private String highest_bid;
    private List<bid> bids = new ArrayList<bid>();

    public Auction(String auction_id, String base_price, String duration, String base_price1, String step_rate, String item_id, String highest_bid, List<bid> bids) {
        this.auction_id = auction_id;
        this.base_price = base_price;
        this.duration = duration;
        this.step_rate = step_rate;
        this.item_id = item_id;
        this.highest_bid = highest_bid;
        this.bids = bids;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getStep_rate() {
        return step_rate;
    }

    public void setStep_rate(String step_rate) {
        this.step_rate = step_rate;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getHighest_bid() {
        return highest_bid;
    }

    public void setHighest_bid(String highest_bid) {
        this.highest_bid = highest_bid;
    }

    public List<bid> getBids() {
        return bids;
    }

    public void setBids(List<bid> bids) {
        this.bids = bids;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
