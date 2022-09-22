package com.example.biddingsystem.entity;


import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;

@Document(value = "item")
public class item {

    @Id
    private String id;

    private String name;

    public item(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
