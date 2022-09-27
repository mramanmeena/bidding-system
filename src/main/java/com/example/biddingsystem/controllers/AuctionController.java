package com.example.biddingsystem.controllers;
import com.example.biddingsystem.entity.Auction;
import com.example.biddingsystem.entity.Bid;
import com.example.biddingsystem.entity.EmailDetails;
import com.example.biddingsystem.entity.User;
import com.example.biddingsystem.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.biddingsystem.services.EmailService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    AuctionService AuctionService;
    @Autowired  EmailService emailService;


    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@RequestBody EmailDetails details)
    {   log.info("details - {}",details);
        String status = emailService.winningMail(details);
        log.info("status - {}",status);
        return new ResponseEntity<String>(status,HttpStatus.ACCEPTED);
    }
    @PostMapping ("/new")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) throws Exception {
        log.info("Auction created successfully!,{}",auction );
        if (!(auction == null || auction.getItemId() == null || auction.getEndTime() == null || auction.getStepRate() == null)) {
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
    public ResponseEntity<Iterable<Auction>> AllAuctions (@RequestParam (value= "pageNumber",defaultValue ="1",required = false)Integer pageNumber,
                                                          @RequestParam (value= "pageSize",defaultValue ="5",required = false)Integer pageSize
                                                          )  throws Exception {
        try {
;
            return new ResponseEntity<Iterable<Auction>>(AuctionService.AllAuctions(), HttpStatus.ACCEPTED);
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
    public ResponseEntity<User> GetWinner(@RequestParam String itemId) throws Exception {
        try {
            Optional<User> winner = AuctionService.getWinner(itemId);
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

}
