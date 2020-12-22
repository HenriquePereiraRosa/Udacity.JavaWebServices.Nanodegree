package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

//    @Autowired
//    private ChatMessageService msgSrvc;
//
//    @GetMapping
//    public String getMessages(ChatForm chatForm, Model model) {
//        model.addAttribute("chatMessages", this.msgSrvc.getMessages());
//        return "chat";
//    }
//
//    @PostMapping
//    public String saveChatMessage(ChatForm chatForm, Model model) {
//        this.msgSrvc.saveMessage(chatForm);
//        chatForm.setMessageText("");
//        model.addAttribute("chatMessages", this.msgSrvc.getMessages());
//        return "chat";
//    }

    @ModelAttribute("allMessageTypes")
    public String[] allMessageTypes () {
        return new String[] { "Say", "Shout", "Whisper" };
    }
}
