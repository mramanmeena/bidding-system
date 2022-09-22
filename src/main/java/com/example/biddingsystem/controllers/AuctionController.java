package com.example.biddingsystem.controllers;
import com.example.biddingsystem.entity.Auction;
import com.example.biddingsystem.entity.Bid;
import com.example.biddingsystem.entity.User;
import com.example.biddingsystem.repository.AuctionService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    AuctionService AuctionService;

    @PostMapping ("/new")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) throws Exception {
        log.info("Auction created successfully!,{}",auction );
        if (!(auction == null || auction.getItem_id() == null || auction.getEndTime() == null || auction.getStep_rate() == null)) {
            return new ResponseEntity<Auction> (AuctionService.createAuction(auction),HttpStatus.CREATED);

        } else {
            return new ResponseEntity<Auction>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/{auction_id}")
    public ResponseEntity<Optional<Auction>> LiveAuctions(@PathVariable("auction_id") String auction_id)  {
        try{
            log.info("Auction returned {}", AuctionService.getAuction(auction_id));
        return new ResponseEntity<>(AuctionService.getAuction(auction_id), HttpStatus.ACCEPTED);}
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/live")
    public ResponseEntity<Iterable<Auction>> LiveAuctions () throws Exception {
        try {
            log.info("LiveAuctions returned {}", AuctionService.liveAuctions());

            return new ResponseEntity<Iterable<Auction>>(AuctionService.liveAuctions(),HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            return new ResponseEntity<Iterable<Auction>>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Auction>> AllAuctions    ()  throws Exception {
        try {
            return new ResponseEntity<Iterable<Auction>>(AuctionService.liveAuctions(), HttpStatus.ACCEPTED);
        }
        catch (Exception e) {   return new ResponseEntity<Iterable<Auction>>(HttpStatus.BAD_REQUEST);}

    }

    @PostMapping("/place")
    public ResponseEntity<Bid> placeBid(@RequestBody Bid bid){
       try {
           return new ResponseEntity<>(AuctionService.placeBid(bid), HttpStatus.ACCEPTED);
       }
       catch (Exception e) {
           return new ResponseEntity<Bid>(HttpStatus.BAD_REQUEST);

       }
    }

    @GetMapping("/winner")
    public ResponseEntity<User> GetWinner(@RequestBody String item_id) throws Exception {
        try {
            log.info("looser");

            Optional<User> winner = AuctionService.getWinner(item_id);
            log.info("winner");
            return new ResponseEntity<User>( winner.get(),HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/Login")
    public ResponseEntity<User> login(@RequestBody User user) throws Exception {
        try {
            return new ResponseEntity<User>(AuctionService.loginUser(user), HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> fetch () throws Exception {
        try {
            log.info("LiveAuctions returned {}", AuctionService.liveAuctions());

            return new ResponseEntity<Iterable<User>>(AuctionService.fetchUsers(),HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            return new ResponseEntity<Iterable<User>>(HttpStatus.BAD_REQUEST);
        }

    }

//    @GetMapping("/bids")
//    public ResponseEntity<Iterable<Bid>> Bids () throws Exception {
//        try {
//            log.info("LiveAuctions returned {}", AuctionService.liveAuctions());
//
//            return new ResponseEntity<Iterable<Bid>>(AuctionService.fetchBids(),HttpStatus.ACCEPTED);
//        }
//        catch (Exception e) {
//            return new ResponseEntity<Iterable<Bid>>(HttpStatus.BAD_REQUEST);
//        }
//
//    }
}
