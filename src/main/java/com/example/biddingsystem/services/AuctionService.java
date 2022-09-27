package com.example.biddingsystem.services;

import com.example.biddingsystem.entity.Auction;
import com.example.biddingsystem.entity.EmailDetails;
import com.example.biddingsystem.repository.AuctionTable;
import com.example.biddingsystem.repository.BidTable;
import com.example.biddingsystem.repository.UserTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.biddingsystem.entity.Bid;
import com.example.biddingsystem.entity.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuctionService {

    @Autowired
    EmailService emailService;
    @Autowired
    AuctionTable auctiontable;
    @Autowired
    BidTable bidtable;
    @Autowired
    UserTable userTable;

    public Auction createAuction(Auction auction) throws Exception {
        List < Auction > auctions = liveAuctions();

        for (Auction pr_check: auctions) {
            if (pr_check.getItemId().equals(auction.getItemId())|| (auction.getStartTime().toInstant().toEpochMilli() > auction.getEndTime().toInstant().toEpochMilli())) {
                return null;
            }
        }
        return auctiontable.save(auction);
    }

    public Optional < Auction > getAuction(String id) throws Exception {
        //       log.info("Auction {} is available " , getAuction(id));
        return auctiontable.findByAuctionId(id);
    }

    public List < Auction > AllAuctions() throws Exception {

//        Pageable p = PageRequest.of();
        return (List < Auction > ) auctiontable.findAll();
    }

    public List < User > fetchUsers() throws Exception {
        return (List < User > ) userTable.findAll();
    }
    //    public List<Bid> fetchBids() {
    //        return (List<Bid>) BidTable.findAll();
    //    }
    public List < Auction > liveAuctions() throws Exception {
        List < Auction > auctions =(List < Auction > ) auctiontable.findAll();
        List < Auction > liveAuctions = new ArrayList < Auction > ();
        for (Auction auction: auctions) {
            if (auction.getEndTime().toInstant().toEpochMilli() > Instant.now().toEpochMilli()) {
                liveAuctions.add(auction);
            }
        }
        return liveAuctions;
    }

    public boolean isLive(String auction_id) throws Exception {
        List < Auction > auctions = (List < Auction > ) auctiontable.findAll();
        List < Auction > liveAuctions = new ArrayList < Auction > ();
        for (Auction auction: auctions) {
            if (auction.getEndTime().toInstant().toEpochMilli() > Instant.now().toEpochMilli() && auction.getAuctionId().equals(auction_id)) {
                return true;
            }
        }
        return false;
    }

    public Auction getAuctionByitem(String item_id) throws Exception {
        List < Auction > auctions = (List < Auction > ) auctiontable.findAll();
        List < Auction > liveAuctions = new ArrayList < Auction > ();
        for (Auction auction: auctions) {
            if ((auction.getItemId() != null) && (auction.getItemId().equals(item_id))) {
                log.info("Auction product id : {}", auction);
                return auction;

            }
        }
        return null;
    }

    public Bid placeBid(Bid bid) throws Exception {
        String item_id = bid.getItemId();
        String user_id = bid.getUserId();
        Integer bid_amount = bid.getBidAmount();
        Auction auction = getAuctionByitem(item_id);

        if (isLive(auction.getAuctionId())) {

            Optional < User > user = userTable.findById(user_id);

            if (user.isPresent()) {

                if (auction.getUsers() == null) {
                    auction.setUsers(new ArrayList < > ());
                    auction.getUsers().add(user.get());
                }

                if (auction.getBids() == null) {
                    auction.setBids(new ArrayList < > ());
                    auction.setHighestBid(bid_amount);
                    auction.setWinnerId(user_id);
                    bid.setStatus("Accepted");
                } else {
                    if (!auction.getBids().contains(bid)) {
                        int max = maxBid(auction.getAuctionId());
                        if (bid_amount >= max + auction.getStepRate()) {
                            auction.setHighestBid(bid_amount);
                            auction.setWinnerId(user_id);
                            bid.setStatus("Accepted");
                            auction.getUsers().add(user.get());
                        } else {
                            bid.setStatus("Not Accepted");
                            auction.getUsers().add(user.get());
                        }

                    }
                }
            }
            auction.getBids().add(bid);
            auctiontable.deleteById(auction.getAuctionId());
            auctiontable.save(auction);
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