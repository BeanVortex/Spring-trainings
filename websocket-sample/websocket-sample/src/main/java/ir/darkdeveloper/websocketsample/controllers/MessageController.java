package ir.darkdeveloper.websocketsample.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import ir.darkdeveloper.websocketsample.dto.Message;
import ir.darkdeveloper.websocketsample.dto.ResponseMessage;

@Controller
public class MessageController {

    // should call /app/message
    @MessageMapping("/hello")
    // send response to /topic/greeting
    @SendTo("/topic/greetings")
    public ResponseMessage getMessage(Message message) throws InterruptedException {
        Thread.sleep(1000);
        return new ResponseMessage(HtmlUtils.htmlEscape("hello " + message.getMessageContent()));
    }

}
