package com.udacity.jwdnd.c1.javawebdev.service;

import com.udacity.jwdnd.c1.javawebdev.model.dto.ChatForm;
import com.udacity.jwdnd.c1.javawebdev.model.general.ChatMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    String message;

    private List<ChatMessage> chatMessages;

//    PREVIOUS LESSON (Bean order creation)
    public MessageService(String message) {
        System.out.println(" -> MessageService.messageService Bean Created");
        this.message = message;
    }

    public String messageUpper() {
        return this.message.toUpperCase();
    }

    public String messageLower() {
        return this.message.toLowerCase();
    }


    @PostConstruct
    public void postConstruct() {
        System.out.println(" -> Creating MessageService bean");
        this.chatMessages = new ArrayList<>();
    }


    public List<ChatMessage> getMessages() {
        return this.chatMessages;
    }

    public void saveMessage(ChatForm chatForm) {

        ChatMessage newMessage = new ChatMessage();
        newMessage.username = chatForm.getUsername();
        switch (chatForm.getMessageType()) {
            case "Say":
                newMessage.message = chatForm.getMessageText();
                break;
            case "Shout":
                newMessage.message = chatForm.getMessageText().toUpperCase();
                break;
            case "Whisper":
                newMessage.message = chatForm.getMessageText().toLowerCase();
                break;
        }
        this.chatMessages.add(newMessage);
    }
}
