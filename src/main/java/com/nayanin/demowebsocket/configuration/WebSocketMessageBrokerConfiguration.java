package com.nayanin.demowebsocket.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfiguration implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private List<ChannelInterceptor> channelInterceptors;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        int inboundChannelCorePoolSize = Runtime.getRuntime().availableProcessors() * 4;
        registration.interceptors(channelInterceptors.toArray(new ChannelInterceptor[channelInterceptors.size()]));
        registration.taskExecutor().corePoolSize(inboundChannelCorePoolSize);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        int outboundChannelCorePoolSize = Runtime.getRuntime().availableProcessors() * 4;
        registration.taskExecutor().corePoolSize(outboundChannelCorePoolSize);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setSendTimeLimit(15 * 1000)            // max amount of time ms allowed when sending (default 10sec).
                .setSendBufferSizeLimit(512 * 1024);    // amount of data to buffer (0 disable buffering).
    }

    /**
     * The registerStompEndpoints() method registers the "/gs-guide-websocket" endpoint, enabling SockJS fallback options
     * so that alternate transports may be used if WebSocket is not available. The SockJS client will attempt to connect
     * to "/gs-guide-websocket" and use the best transport available (websocket, xhr-streaming, xhr-polling, etc).
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS(); // Use SockJS for fallback options.
    }
}
