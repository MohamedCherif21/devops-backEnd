package com.example.cocomarket.controller;

import com.example.cocomarket.entity.MSG;
import com.example.cocomarket.repository.MsgRepository;
import com.example.cocomarket.repository.UserRepository;
import com.example.cocomarket.services.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;


@Controller
public class ControllerMsg {


    @Autowired
    private MsgRepository msgRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MsgService msgService ;


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public MSG addUser(@Payload MSG msg, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if (sessionAttributes != null) {
            sessionAttributes.put("username", msg.getSender());
        } else {
            // Handle the case when sessionAttributes is null
            // You might want to log a warning or take appropriate action
        }

        msgRepository.save(msg);
        return msg;
    }




    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public MSG register(@Payload MSG chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if (sessionAttributes != null) {
            sessionAttributes.put("username", chatMessage.getSender());
        } else {
            // Handle the case when sessionAttributes is null
            // You might want to log a warning or take appropriate action
        }

        return chatMessage;
    }


    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public MSG sendMessage(@Payload MSG chatMessage , SimpMessageHeaderAccessor headerAccessor) {
        msgRepository.save(chatMessage);

       return chatMessage;
    }
    }














