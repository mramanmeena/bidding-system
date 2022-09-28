package com.example.biddingsystem.services;

import com.example.biddingsystem.entity.Auction;
import com.example.biddingsystem.repository.AuctionTable;
import com.example.biddingsystem.repository.BidTable;
import com.example.biddingsystem.repository.UserTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.biddingsystem.entity.Bid;
import com.example.biddingsystem.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuctionService {

    @Autowired
    EmailService emailService;
    @Autowired
    AuctionTable auctiontable; //name change
    @Autowired
    BidTable bidtable;
    @Autowired
    UserTable userTable;

    public Auction createAuction(Auction auction) throws Exception {
        List < Auction > auctions = auctiontable.createHelper(auction.getItemId());
        for (Auction pr_check: auctions) {
            if (pr_check.getItemId().equals(auction.getItemId())|| (auction.getStartTime().toInstant().toEpochMilli() > auction.getEndTime().toInstant().toEpochMilli())) {
                return null;
            }
        }
        return auctiontable.save(auction);
    }

    public Optional < Auction > getAuction(String id) throws Exception {
               log.info("Auction {} is available " , getAuction(id));
        return auctiontable.findByAuctionId(id);
    }

    public List < Auction > AllAuctions() throws Exception {
        return (List < Auction > ) auctiontable.findAll();
    }

    public List < User > fetchUsers() throws Exception {
        return (List<User>) userTable.findAll();
    }
    public List < Auction > liveAuctions() throws Exception {
        log.info("Live auctions {}",auctiontable.findAllByStatus("Live") );
        if( auctiontable.findAllByStatus("Live") != null) {
            List < Auction > live = auctiontable.findAllByStatus("Live");
        return live;
        }
        return null;
    }

    public String isLive(String auction_id) throws Exception {
        Optional<String> bool = auctiontable.liveOrNot(auction_id);

        if (bool.isPresent())
        return bool.get() ;
        return null;
    }

    public Auction getAuctionByitem(String item_id) throws Exception {
        if (auctiontable.findByItemId(item_id).isPresent())
        {Auction auction = auctiontable.findByItemId(item_id).get();
        return auction;}
        return null;
    }

    public Bid placeBid(Bid bid) throws Exception {
        String item_id = bid.getItemId();
        String user_id = bid.getUserId();
        Integer bid_amount = bid.getBidAmount();
        Auction auction = getAuctionByitem(item_id);

        if (isLive(auction.getAuctionId()).equals("Live")) {

            Optional<User> user = userTable.findById(user_id);

            if (user.isPresent()) {
                if (auction.getWinnerId() == null) {
                    auction.setHighestBid(bid_amount);
                    auction.setWinnerId(user_id);
                    bid.setStatus("Accepted");
                } else {
                    int max = maxBid(auction.getAuctionId());
                    if (bid_amount >= max + auction.getStepRate()) {
                        auction.setHighestBid(bid_amount);
                        auction.setWinnerId(user_id);
                        bid.setStatus("Accepted");
                    } else {
                        bid.setStatus("Not Accepted");
                    }
                    auctiontable.save(auction);
                }
            }
            return bid;
        }
        return null;
    }
    public Optional < User > getWinner(String item_id) throws Exception {
        log.info("c1 : {}", item_id);
        Auction auction = getAuctionByitem(item_id);
        if ( auction.getWinnerId() != null && !auction.getWinnerId().equals(""))
            return userTable.findById(auction.getWinnerId());
        return null;

    }

    public int maxBid(String auction_id) throws Exception {
        Optional < Auction > auction = auctiontable.findById(auction_id);
        if (auction.get().getHighestBid()!=null) {
            return auction.get().getHighestBid();
        }
        return 0;
    }

    public User loginUser(User user) {
        return userTable.save(user);
    }

}