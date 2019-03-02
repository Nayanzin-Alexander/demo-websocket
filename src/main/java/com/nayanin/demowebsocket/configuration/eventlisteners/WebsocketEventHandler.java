package com.nayanin.demowebsocket.configuration.eventlisteners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebsocketEventHandler {

    Logger logger = LogManager.getLogger();

    @EventListener(BrokerAvailabilityEvent.class)
    public void BrokerAvailabilityEventHandler(BrokerAvailabilityEvent event) {
        logger.info("BrokerAvailabilityEvent: " + event);
    }

    @EventListener(SessionConnectedEvent.class)
    public void SessionConnectedEventHandler(SessionConnectedEvent event) {
        logger.info("SessionConnectedEvent: " + event);
    }

    @EventListener(SessionDisconnectEvent.class)
    public void SessionDisconnectEventHandler(SessionDisconnectEvent event) {
        logger.info("SessionDisconnectEvent: " + event);
    }
}
