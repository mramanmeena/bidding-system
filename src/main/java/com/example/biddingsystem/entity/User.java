package com.example.biddingsystem.entity;
import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document

public class User {

    @Id
    private String id;

    private String email;

    private String name;


}




