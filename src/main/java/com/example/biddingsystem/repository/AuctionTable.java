package com.example.biddingsystem.repository;
import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.biddingsystem.entity.Auction;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface AuctionTable extends ArangoRepository<Auction, String> {

    @Query(value = "insert @auction into AuctionTable")
    Auction createAuction(@Param("auction") Auction auction);


}