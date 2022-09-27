package com.example.biddingsystem.entity;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "AuctionTable")
public class Auction {

    @Id
    private String auctionId;
    private String basePrice;
    private Date startTime;
    private Date endTime;
    private Integer stepRate;
    private String itemId;
    private String status;
    private List<Bid> Bids ;
    private List<User> Users;
    private String winnerId;
    private Integer highestBid;

}
