package com.example.api.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.api.entity.JournalEntry;
import com.example.api.entity.UserEntry;
import com.example.api.enums.Sentiment;
import com.example.api.model.SentimentData;
import com.example.api.repository.UserRepositoryImpl;
import com.example.api.service.EmailService;
import com.example.api.service.SentimentAnalysisService;

@Component
public class UserSchedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "30 56 16 ? * SUN")
    public void fetchUserAndSendSaMail(){
        List<UserEntry> users = userRepositoryImpl.getUserForSA();
        for(UserEntry user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x->x.getDate().isAfter((LocalDateTime.now().minus(7,ChronoUnit.DAYS)))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment!=null){
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for Last 7 days" + mostFrequentSentiment).build();
                System.out.println(sentimentData);
                kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
            }
    }
}

}