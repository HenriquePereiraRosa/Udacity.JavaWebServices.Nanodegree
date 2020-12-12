package com.udacity.jwdnd.c1.javawebdev.service;

import com.udacity.jwdnd.c1.javawebdev.model.dto.ChatForm;
import com.udacity.jwdnd.c1.javawebdev.model.entity.ChatMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {
    String message;

    private List<ChatMessage> chatMessages;

//    PREVIOUS LESSON (Bean order creation)
    public ChatMessageService(String message) {
        System.out.println(" -> ChatMessageService.messageService Bean Created");
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
        System.out.println(" -> Creating ChatMessageService bean");
        this.chatMessages = new ArrayList<>();
    }


    public List<ChatMessage> getMessages() {
        return this.chatMessages;
    }

    public void saveMessage(ChatForm chatForm) {

        ChatMessage newMessage = new ChatMessage();
        newMessage.setEmail(chatForm.getEmail());
        switch (chatForm.getMessageType()) {
            case "Say":
                newMessage.setMessage(chatForm.getMessageText());
                break;
            case "Shout":
                newMessage.setMessage(chatForm.getMessageText().toUpperCase());
                break;
            case "Whisper":
                newMessage.setMessage(chatForm.getMessageText().toLowerCase());
                break;
        }
        this.chatMessages.add(newMessage);
    }

    public void addMessage(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
    }
}
