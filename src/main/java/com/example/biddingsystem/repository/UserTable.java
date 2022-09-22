package com.example.biddingsystem.repository;
import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.example.biddingsystem.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.biddingsystem.entity.Auction;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface UserTable extends ArangoRepository<User, String> {

}