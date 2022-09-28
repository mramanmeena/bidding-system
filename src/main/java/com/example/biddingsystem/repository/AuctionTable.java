package com.example.biddingsystem.repository;
import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.example.biddingsystem.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.biddingsystem.entity.Auction;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionTable extends ArangoRepository <Auction, String> {


    Optional<Auction> findByAuctionId(String auction_id);
    List<Auction> findAllByStatus(String status);

    @Query(value = "For auction in AuctionTable " +
            "Filter auction.startTime > auction.endTime " +
            "And auction._key == @auctionId " +
            "return auction")
    List<Auction> createHelper(@Param("auctionId") String auctionId);
   @Query(value = "For a in AuctionTable " + "FILTER a._key == @auctionId " + " return a.status")
    Optional<String> liveOrNot(@Param("auctionId") String auction_id);

    Optional<Auction> findByItemId(String item_id);

}