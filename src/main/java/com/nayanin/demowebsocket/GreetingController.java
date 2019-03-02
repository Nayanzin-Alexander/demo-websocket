package com.nayanin.demowebsocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    /*
    @MessageMapping ensures that if a message is sent to destination "/hello",
    then the greeting() method is called.
     */
    @MessageMapping("hello/{id}")

    /*
    The return value is broadcast to all subscribers to "/topic/greetings" as specified
    in the @SendTo annotation.
     */
    //@SendTo("/topic/greetings")
    public Greeting greeting(
            @DestinationVariable("id") String id,
            HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return Greeting.builder()
                .content(id +" Hello " + HtmlUtils.htmlEscape(message.getName()))
                .build();
    }
}
