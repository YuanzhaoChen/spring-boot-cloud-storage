package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final UserService userService;
    private final NoteService noteService;

    public HomeController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/home")
    public String homeView(Authentication authentication,
                           @ModelAttribute Note note,
                           Model model){
        // fill login user info to html
        String username = authentication.getName();
        Integer userId = this.userService.getUser(username).getId();
        model.addAttribute("savedNotes", this.noteService.getAllNotes(userId));
        return "home";
    }

}
