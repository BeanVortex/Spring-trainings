package ir.darkdeveloper.websocketsample.controllers;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import ir.darkdeveloper.websocketsample.dto.Message;
import ir.darkdeveloper.websocketsample.dto.ResponseMessage;

@Controller
public class MessageController {

    @MessageExceptionHandler
    @SendTo("/topic/err")
    public ResponseMessage handleException(Exception e) {
        return new ResponseMessage(HtmlUtils.htmlEscape(
                "Something went wrong with request: " + NestedExceptionUtils.getMostSpecificCause(e).getMessage()));
    }

    // should call /app/message
    @MessageMapping("/hello")
    // send response to /topic/greeting
    @SendTo("/topic/greetings")
    public ResponseMessage getMessage(Message message) throws InterruptedException {
        Thread.sleep(1000);
        var msg = message.getMessageContent();
        if (!Character.isUpperCase(message.getMessageContent().charAt(0)))
            throw new IllegalArgumentException("content must start with uppercase letter");
        return new ResponseMessage(HtmlUtils.htmlEscape("hello " + msg));
    }

}
