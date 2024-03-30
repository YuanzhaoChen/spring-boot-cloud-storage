package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/signup")
public class SignUpController {
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model){
        String successAttributeName = "signupSuccess";
        String errorAttributeName = "signupError";
        String username = user.getUsername();
        if (!this.userService.isUserNameAvailable(username)){
            model.addAttribute(successAttributeName, false);
            model.addAttribute(errorAttributeName, "The username already exists.");
            return "signup";
        }
        int rowsAdded = userService.createUser(user);
        if (rowsAdded < 0) {
            model.addAttribute(successAttributeName, false);
            model.addAttribute(errorAttributeName, "There was an error signing you up. Please try again.");
            return "signup";
        }
        model.addAttribute(successAttributeName, true);
        model.addAttribute(errorAttributeName, null);
        return "signup";
    }
}
