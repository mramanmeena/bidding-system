package com.example.biddingsystem.repository;
import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.example.biddingsystem.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.biddingsystem.entity.Auction;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository


public interface AuctionTable extends ArangoRepository<Auction, String> {


    Optional<Auction> findByAuctionId(String auction_id);
}