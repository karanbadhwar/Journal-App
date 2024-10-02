//package com.Badhwar.journalApp.services;
//
//import com.Badhwar.journalApp.model.SentimentData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SentimentConsumerService {
//
//    @Autowired
//    private EmailService emailService;
//
//    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiments-group")
//    public void consume(SentimentData sentimentData) {
//        sendEmail(sentimentData);
//    }
//
//    private void sendEmail(SentimentData sentimentData) {
//        emailService.sendEmail(sentimentData.getEmail(), "sentiment for previous week", sentimentData.getSentiment());
//    }
//}
