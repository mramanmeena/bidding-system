package com.example.biddingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.arangodb.springframework.annotation.Document;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(value = "item")
public class item {

    @Id
    private String id;
    private String name;
}
