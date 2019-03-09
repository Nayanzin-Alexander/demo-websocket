package com.nayanin.demowebsocket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

@RequiredArgsConstructor
@RestController
public class WebSocketStatsController {

    private final WebSocketMessageBrokerStats stats;

    @GetMapping("/ws-stats")
    public WebSocketMessageBrokerStats showStats() {
        return stats;
    }
}
