package com.example.biddingsystem.repository;

import com.example.biddingsystem.entity.Auction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.biddingsystem.repository.AuctionTable;

@Service
public class AuctionService {

    @Autowired
    AuctionTable auctiontable;

   public Auction createAuction(Auction auction) throws Exception {

       return auctiontable.createAuction(auction);
   };
}
