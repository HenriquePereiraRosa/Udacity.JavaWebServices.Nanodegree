package com.udacity.jwdnd.c1.javawebdev.contoller;

import com.udacity.jwdnd.c1.javawebdev.model.dto.ChatForm;
import com.udacity.jwdnd.c1.javawebdev.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatMessageService msgSrvc;

    @GetMapping
    public String getMessages(ChatForm chatForm, Model model) {
        model.addAttribute("chatMessages", this.msgSrvc.getMessages());
        return "chat";
    }

    @PostMapping
    public String saveChatMessage(ChatForm chatForm, Model model) {
        this.msgSrvc.saveMessage(chatForm);
        chatForm.setMessageText("");
        model.addAttribute("chatMessages", this.msgSrvc.getMessages());
        return "chat";
    }

    @ModelAttribute("allMessageTypes")
    public String[] allMessageTypes () {
        return new String[] { "Say", "Shout", "Whisper" };
    }
}
