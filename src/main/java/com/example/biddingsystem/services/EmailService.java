package com.example.biddingsystem.services;
import com.example.biddingsystem.entity.EmailDetails;


// Importing required classes
// Interface
public interface EmailService {

    String winningMail(EmailDetails details);
}