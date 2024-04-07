package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
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
    private final CredentialService credentialService;
    private final FileService fileService;

    public HomeController(UserService userService, NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String homeView(Authentication authentication,
                           @ModelAttribute Note note,
                           @ModelAttribute CredentialForm credentialForm,
                           Model model) {
        // fill login user info to html
        String username = authentication.getName();
        Integer userId = this.userService.getUser(username).getId();
        model.addAttribute("savedNotes", this.noteService.getAllNotes(userId));
        model.addAttribute("savedCredentials", this.credentialService.getAllCredentialForms(userId));
        model.addAttribute("savedFiles", this.fileService.getAllFiles(userId));
        return "home";
    }

}
