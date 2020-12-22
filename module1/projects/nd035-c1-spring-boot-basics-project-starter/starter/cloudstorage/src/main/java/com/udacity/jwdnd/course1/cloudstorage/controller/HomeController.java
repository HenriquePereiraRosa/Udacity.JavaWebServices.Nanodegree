package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String homeView() {
        return "home";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model) {
        Signup signup = new Signup();
//        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signup.setError("The username already exists.");
        }

        if (signup.getError() == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signup.setError("There was an error signing you up. Please try again.");
            }
        }

        if (signup.getError() == null) {
            model.addAttribute("signup.success", true);
        } else {
            model.addAttribute("signup.error", signup.getError());
        }

        return "signup";
    }

    private class Signup {
        private String error;
        private String success;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }
    }
}
