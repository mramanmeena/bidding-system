package com.example.biddingsystem.entity;

import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "bid")

public class Bid {

    @Id
    private String bidId;
    private String userId;
    private Integer bidAmount;
    private String status;
    private String itemId;


    }

