package com.example.cocomarket.Controller;

import com.example.cocomarket.Entity.MSG;
import com.example.cocomarket.Repository.Msg__Repository;
import com.example.cocomarket.Repository.User_Repository;
import com.example.cocomarket.Services.Msg__Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class controllerMSG {


    @Autowired
    private Msg__Repository msgRepository;
    @Autowired
    private User_Repository UserRepo;
    @Autowired
    private Msg__Service msgService ;


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public MSG addUser(@Payload MSG msg, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session

        headerAccessor.getSessionAttributes().put("username", msg.getSender());

        msgRepository.save(msg);
        return msg;
    }



   @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public MSG register(@Payload MSG chatMessage, SimpMessageHeaderAccessor headerAccessor) {
       headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public MSG sendMessage(@Payload MSG chatMessage , SimpMessageHeaderAccessor headerAccessor) {
        msgRepository.save(chatMessage);

       return chatMessage;
    }
    }














