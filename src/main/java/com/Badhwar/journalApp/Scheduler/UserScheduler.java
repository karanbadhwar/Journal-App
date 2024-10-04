package com.Badhwar.journalApp.Scheduler;

import com.Badhwar.journalApp.Cache.AppCache;
import com.Badhwar.journalApp.entity.JournalEntry;
import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.enums.Sentiment;
import com.Badhwar.journalApp.model.SentimentData;
import com.Badhwar.journalApp.repository.UserRespositoryImpl;
import com.Badhwar.journalApp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRespositoryImpl userRespository;

//    @Autowired
//    private SentimentsAnalysisService sentimentsAnalysisService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 * * Sun")
//    @Scheduled(cron = "0 * * ? * * ")
    public void fetchUserAndSendSaMail() {
        List<User> users = userRespository.getUserforSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).toList();

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if(mostFrequentSentiment != null)
            {
             SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days "+mostFrequentSentiment).build();
             try {
//                 emailService.sendEmail(user.getEmail(), "Sentiments for last 7 days ", mostFrequentSentiment.toString());
                 emailService.sendEmail(sentimentData.getEmail(), "sentiment for previous week", sentimentData.getSentiment());
             } catch(Exception e)
             {
                 kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
             }
            }

            // Kafka Cloud account not created !!!! CREDIT CARD ISSUE
//            if(mostFrequentSentiment != null)
//            {
//              SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days "+mostFrequentSentiment).build();
//              kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
//            }

//            String sentiment = sentimentsAnalysisService.sentiments(entry);
//            emailService.sendEmail(user.getEmail(), "Sentiments for last 7 days", entry);
        }
//        emailService.sendEmail("codediscount36@gmail.com", "Scheduling", "Hi");
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
