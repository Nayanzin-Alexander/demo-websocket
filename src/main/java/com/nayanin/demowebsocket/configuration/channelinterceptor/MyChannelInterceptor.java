package com.nayanin.demowebsocket.configuration.channelinterceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class MyChannelInterceptor implements ChannelInterceptor {

    private Logger logger = LogManager.getLogger();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        logger.info("StompCommand: " + command);
        logger.info("channel: " + channel);
        logger.info("message: " + message);
        return message;
    }
}
