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
import com.example.biddingsystem.entity.Auction;
import lombok.extern.slf4j.Slf4j;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

        List<Auction> auctions = (List<Auction>) auctionService.AllAuctions();
        for (Auction auction: auctions) {
            log.info("auctions list {}{}",auction.getAuctionId(),auction.getStatus());

            if ((auction.getStatus()==null  || auction.getStatus().isEmpty()) && (auction.getStartTime().toInstant().toEpochMilli() <= Instant.now().toEpochMilli())) {
                    log.info("Auction with auction_id {} is live", auction.getAuctionId());
                    auction.setStatus("Live");
                    auctiontable.save(auction);
            }
            else if (auction.getStatus().equals("Live") && auction.getWinnerId() == null &&(auction.getEndTime().toInstant().toEpochMilli() < Instant.now().toEpochMilli() )){
                auction.setStatus("Expired");
                auctiontable.save(auction);
            }
            else if ((auction.getStatus().equals("Live")) && (auction.getWinnerId() != null )&& (auction.getEndTime().toInstant().toEpochMilli() < Instant.now().toEpochMilli()) ){
                log.info("Sending Mail to the Winner of the auction with auction_id {} {}",auction.getAuctionId(),auction.getStatus());
                Optional<User> winner  = auctionService.getWinner(auction.getItemId());
                EmailDetails details = new EmailDetails();

                 if (winner.isPresent()){
                    details.setRecipient(winner.get().getEmail());
                    details.setMsgBody("You Won");
                    details.setSubject("Congratulations");
                    emailService.winningMail(details);
                    }
                auction.setStatus("Expired");
                auctiontable.save(auction);
            }

            else if (auction.getEndTime().toInstant().toEpochMilli() < Instant.now().toEpochMilli() && !(auction.getStatus().equals("Expired"))) {
//                log.info("Auction with auction_id {} Expired",auction.getAuctionId());
                auction.setStatus("Expired");
                auctiontable.save(auction);
            }

        }
    }
}
