package com.example.biddingsystem.repository;

import com.example.biddingsystem.entity.Auction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    AuctionTable auctiontable;
    @Autowired
    BidTable bidtable;
    @Autowired
    UserTable userTable;


   public Auction createAuction(Auction auction) throws Exception {
       List<Auction> auctions = liveAuctions() ;
       for (Auction pr_check : auctions)
       {
           if (pr_check.getItem_id() == auction.getItem_id() || (auction.getStartTime().toInstant().toEpochMilli() > auction.getEndTime().toInstant().toEpochMilli())){
               return null;
           }
       }
       return auctiontable.save(auction);
   }

   public Optional<Auction> getAuction(String id) throws Exception {
//       log.info("Auction {} is available " , getAuction(id));
       return auctiontable.findByAuctionId(id);
   }

   public List<Auction> AllAuctions() throws Exception {
       return (List<Auction>) auctiontable.findAll();
   }

    public List<User> fetchUsers() throws Exception {
        return (List<User>) userTable.findAll();
    }
//    public List<Bid> fetchBids() {
//        return (List<Bid>) BidTable.findAll();
//    }
    public List<Auction> liveAuctions() throws Exception {
        List<Auction> auctions = AllAuctions() ;
        List<Auction> liveAuctions = new ArrayList<Auction>();
        for (Auction auction : auctions) {
            if (auction.getEndTime().toInstant().toEpochMilli() >  Instant.now().toEpochMilli()) {
                liveAuctions.add(auction);
            }
        }
        return liveAuctions;
    }

    public boolean isLive(String auction_id) throws Exception {
        List<Auction> auctions = AllAuctions() ;
        List<Auction> liveAuctions = new ArrayList<Auction>();
        for (Auction auction : auctions) {
            if (auction.getEndTime().toInstant().toEpochMilli() >  Instant.now().toEpochMilli() &&  auction.getAuctionId().equals(auction_id)) {
                return true;
            }
            }
       return false;
    }

    public Auction getAuctionByitem (String item_id) throws Exception {
        List<Auction> auctions = AllAuctions() ;
        List<Auction> liveAuctions = new ArrayList<Auction>();
        for (Auction auction : auctions) {
            if ((auction.getEndTime().toInstant().toEpochMilli() >  Instant.now().toEpochMilli()) && (auction.getItem_id().equals(item_id))) {
                log.info("Auction product id : {}", auction);
                return auction;

            }
        }
        return null;
    }


    public Bid placeBid(Bid bid ) throws Exception {
       log.info("Bid: {}", bid);
        String item_id = bid.getItem_id();
        String user_id = bid.getUser_id();
        Integer bid_amount = bid.getBid_amount();

        log.info("Bid_values :{} {} {}", item_id,user_id,bid_amount);

        Auction auction = getAuctionByitem(item_id);
        log.info("Auction: {}", auction);

        if (isLive(auction.getAuctionId())){

            Optional<User> user = userTable.findById(user_id);

            if (user.isPresent()){

            if(auction.getUsers() == null){
                auction.setUsers(new ArrayList<>());
                auction.getUsers().add(user.get());
               log.info("c1: {}", auction);}

            if(auction.getBids() == null){
                auction.setBids(new ArrayList<>());
                auction.getBids().add(bid);}

            else {
                log.info("c4: {}", auction);
                if(!auction.getBids().contains(bid)) {
                    int maxBid = maxBid(auction.getAuctionId());
                    if( bid_amount >= maxBid+ auction.getStep_rate()){
                        auction.getBids().add(bid);
                        bid.setStatus("Accepted");
                        auction.getUsers().add(user.get());
                        log.info("c5 : {} {}", user,bid);}
                    else {
                        auction.getBids().add(bid);
                        bid.setStatus("Not Accepted");
                        auction.getUsers().add(user.get());
                    }

                }
            }}
            auctiontable.deleteById(auction.getAuctionId());
            auctiontable.save(auction);
    }
        return null;
    }

    public Optional<User> getWinner(String item_id) throws Exception {
        log.info("c1 : {}",item_id);

        Auction auction = getAuctionByitem("10");
        int MAX_BID = 0 ;
        String WINNER_ID = null;
        log.info("c1 : {}",auction);
        List<Bid> bids = auction.getBids();
        log.info("c2 : {}",bids);

        for (Bid bid : bids) {
            log.info("{}  {} {}",bid.getBid_amount(),MAX_BID,bid.getStatus());
            if (bid.getBid_amount() > MAX_BID && bid.getStatus().equals("Accepted")) {
                MAX_BID = bid.getBid_amount();
                WINNER_ID = bid.getUser_id();
                log.info("c1 : {}",WINNER_ID);

            }
        }
        return userTable.findById(WINNER_ID);
   }

    public int maxBid(String auction_id) throws Exception {
        Optional<Auction> auction = auctiontable.findById(auction_id);
        int MAX_BID = 0 ;
        String WINNER_ID = null;
        if (auction.isPresent()) {
            Auction new_auction = auction.get();
            List<Bid> bids = new_auction.getBids();
            for (Bid bid : bids) {
                if (bid.getBid_amount() > MAX_BID && bid.getStatus() == "Accepted") {
                    MAX_BID = bid.getBid_amount();
                    WINNER_ID = bid.getUser_id();
                }
            }
            return MAX_BID;
        }
        return 0;
    }


    public User loginUser(User user) {
          return   userTable.save(user);
    }



}
