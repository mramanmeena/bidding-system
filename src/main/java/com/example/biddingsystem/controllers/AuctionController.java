package com.example.biddingsystem.controllers;
import com.example.biddingsystem.entity.Auction;
import com.example.biddingsystem.repository.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    AuctionService AuctionService;
    @PostMapping ("/new")
    public Auction createAuction(@RequestBody Auction auction) throws Exception {
        if (auction == null || auction.getItem_id() == null || auction.getDuration() == null || auction.getStep_rate() == null) {
            throw new Exception("Missing Data Exception");
        } else {
            return AuctionService.createAuction(auction);
        }
    }

}
