package com.example.biddingsystem;

import com.arangodb.ArangoDB;
import com.arangodb.Protocol;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import org.springframework.context.annotation.Configuration;
import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * ArangoDB configuration class
 * To configure and provide the bean to inject later for database interactions
 * @author dassiorleando
 */
@Configuration
@EnableArangoRepositories(basePackages = { "com.arangodb.spring.demo" })
public class ArangoDBConfiguration implements ArangoConfiguration {

    @Override
    public ArangoDB.Builder arango() {
        ArangoDB.Builder arango = new ArangoDB.Builder()
                .host("127.0.0.1", 8529)
                .useProtocol(Protocol.HTTP_JSON)
                .user("root")
                .password("root");
        return arango;
    }
    @Override
    public String database() {
        return "nothing";
    }

}
