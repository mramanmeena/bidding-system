package com.example.biddingsystem.entity;
import java.util.*;

import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;

@Document(value = "AuctionTable")
public class Auction {

    @Id
    private String auctionId;
    private String base_price;
    private Date startTime;
    private Date endTime;
    private Integer step_rate;
    private String item_id;
    private String highest_bid;
    private List<Bid> Bids ;
    private List<User> Users;


    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> users) {
        Users = users;
    }



    public Auction(String auctionId, String base_price, Date startTime, Date endTime, Integer step_rate, String item_id, String highest_bid, List<User> Users, List<Bid> Bids) {
        this.auctionId = auctionId;
        this.base_price = base_price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.step_rate = step_rate;
        this.item_id = item_id;
        this.highest_bid = highest_bid;
        this.Users = Users;
        this.Bids = Bids;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getStep_rate() {
        return step_rate;
    }

    public void setStep_rate(Integer step_rate) {
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

    public List<Bid> getBids() {
        return Bids;
    }

    public void setBids(List<Bid> Bids) {
        this.Bids = Bids;
    }
    public String getBase_price() {
        return base_price;
    }

    public void setBase_price(String base_price) {
        this.base_price = base_price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
