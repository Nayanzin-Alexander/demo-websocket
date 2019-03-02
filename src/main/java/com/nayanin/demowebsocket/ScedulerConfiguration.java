package com.nayanin.demowebsocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScedulerConfiguration {

    @Autowired
    private SimpMessagingTemplate template;

    private Logger log = LogManager.getLogger();

    @Scheduled(fixedDelay = 1000)
    public void spam() {
        String destination = "/topic/hello";
        Greeting payload = Greeting.builder()
                .content("Spam")
                .build();
        template.convertAndSend(destination, payload);
    }
}
