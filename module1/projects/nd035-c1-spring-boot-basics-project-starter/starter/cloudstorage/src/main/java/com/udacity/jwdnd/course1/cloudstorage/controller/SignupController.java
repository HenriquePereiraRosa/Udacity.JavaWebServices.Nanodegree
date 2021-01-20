package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model,
                             RedirectAttributes redirectAttributes) {
        String error = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            error = "The username already exists.";
        }

        if (error == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                error = "There was an error signing you up. Please try again.";
            }
        }

        if (error != null) {
            model.addAttribute("error", error);
            return "signup";
        }

        return "redirect:/login?signup=true";
    }
}
