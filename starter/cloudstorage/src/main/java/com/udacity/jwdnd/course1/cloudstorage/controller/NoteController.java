package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.enums.ResultModelAttribute;
import com.udacity.jwdnd.course1.cloudstorage.enums.ServiceResponse;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {
    private final UserService userService;
    private final NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/home/add-note")
    public String addOrUpdateNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        String username = authentication.getName(); // current signed-in user
        Integer userId = this.userService.getUser(username).getId();
        note.setUserId(userId);
        if (note.getId() != null) {
            return updateNote(note, model);
        } else {
            return addNote(note, model);
        }
    }

    public String addNote(Note note, Model model) {
        ServiceResponse response = this.noteService.addNote(note);
        if (response.equals(ServiceResponse.SUCCESS)) {
            model.addAttribute(ResultModelAttribute.SUCCESS.getStatusString(), true);
        } else {
            model.addAttribute(ResultModelAttribute.ERROR.getStatusString(), response.getStatusString());
        }
        return "result";
    }

    public String updateNote(Note note, Model model) {
        ServiceResponse response = this.noteService.updateNote(note);
        if (response.equals(ServiceResponse.SUCCESS)) {
            model.addAttribute(ResultModelAttribute.SUCCESS.getStatusString(), true);
        } else {
            model.addAttribute(ResultModelAttribute.ERROR.getStatusString(), response.getStatusString());
        }
        return "result";
    }

    @GetMapping("/home/delete-note")
    public String deleteNote(Authentication authentication, @RequestParam(value = "id") String id, Model model) {
        Integer noteId = Integer.parseInt(id);
        String username = authentication.getName(); // current signed-in user
        Integer userId = this.userService.getUser(username).getId();
        if (!this.noteService.getNote(noteId).getUserId().equals(userId)) {
            model.addAttribute(ResultModelAttribute.ERROR.getStatusString(), "User is not authorized to delete note.");
            return "result";
        }
        ServiceResponse response = this.noteService.deleteNote(noteId);
        if (response.equals(ServiceResponse.SUCCESS)) {
            model.addAttribute(ResultModelAttribute.SUCCESS.getStatusString(), true);
        } else {
            model.addAttribute(ResultModelAttribute.ERROR.getStatusString(), response.getStatusString());
        }
        return "result";
    }
}
