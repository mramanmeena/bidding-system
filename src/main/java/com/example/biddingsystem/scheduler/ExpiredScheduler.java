package com.example.biddingsystem.scheduler;
import com.example.biddingsystem.entity.EmailDetails;
import com.example.biddingsystem.entity.User;
import com.example.biddingsystem.repository.AuctionTable;
import com.example.biddingsystem.repository.BidTable;
import com.example.biddingsystem.repository.UserTable;
import com.example.biddingsystem.services.AuctionService;
import com.example.biddingsystem.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import com.example.biddingsystem.entity.Auction;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExpiredScheduler {

    @Autowired
    AuctionService auctionService;
    @Autowired
    EmailService emailService;
    @Autowired
    AuctionTable auctiontable;
    @Autowired
    BidTable bidtable;
    @Autowired
    UserTable userTable;

    @Scheduled( cron = "${cron}" )
    public void markExpired() throws Exception {

        List<Auction> auctions = (List<Auction>) auctiontable.findAll();
        for (Auction auction: auctions) {
            if (auction.getStatus().equals("Live") && auction.getBids() == null &&(auction.getEndTime().toInstant().toEpochMilli() < Instant.now().toEpochMilli() )){
                auction.setStatus("Expired");
                auctiontable.deleteById(auction.getAuctionId());
                auctiontable.save(auction);
            }
            if (auction.getStatus().equals("Live") && auction.getBids() != null && (auction.getEndTime().toInstant().toEpochMilli() < Instant.now().toEpochMilli()) ){
                log.info("Sending Mail to the Winner of the auction with auction_id {}",auction.getAuctionId());
                Optional<User> winner  = auctionService.getWinner(auction.getItemId());
                EmailDetails details = new EmailDetails();
                if (winner != null && winner.isPresent()){
                    details.setRecipient(winner.get().getEmail());
                    details.setMsgBody("You Won");
                    details.setSubject("Congratulations");
                    emailService.winningMail(details);
                    }
                auction.setStatus("Expired");
                auctiontable.deleteById(auction.getAuctionId());
                auctiontable.save(auction);
            }
            else if (auction.getEndTime().toInstant().toEpochMilli() < Instant.now().toEpochMilli() && !(auction.getStatus().equals("Expired"))) {
//                log.info("Auction with auction_id {} Expired",auction.getAuctionId());
                auction.setStatus("Expired");
                auctiontable.deleteById(auction.getAuctionId());
                auctiontable.save(auction);
            }
            else  {
//                log.info("Auction with auction_id {} is live", auction.getAuctionId());
                if (! (auction.getStatus().equals("Live")))
                    auction.setStatus("Live");
                    auctiontable.deleteById(auction.getAuctionId());
                    auctiontable.save(auction);
            }
        }
    }
}
