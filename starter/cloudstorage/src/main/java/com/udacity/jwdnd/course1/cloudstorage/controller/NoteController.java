package com.udacity.jwdnd.course1.cloudstorage.controller;

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
            if (this.noteService.updateNote(note) != 0) {
                model.addAttribute("error", "Cannot add note, please try again.");
                return "result";
            }
        } else {
            if (this.noteService.addNote(note) < 0) {
                model.addAttribute("error", "Cannot add note, please try again.");
                return "result";
            }
        }
        model.addAttribute("savedNotes", this.noteService.getAllNotes(userId));
        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("/home/delete-note")
    public String deleteNote(Authentication authentication, @RequestParam(value = "id") String id, Model model) {
        Integer noteId = Integer.parseInt(id);
        String username = authentication.getName(); // current signed-in user
        Integer userId = this.userService.getUser(username).getId();
        if (userId != this.noteService.getNote(noteId).getUserId()) {
            model.addAttribute("error", "User is not authorized to delete note.");
        } else if (this.noteService.deleteNote(noteId) != 0) {
            model.addAttribute("error", "Cannot delete note, please try again.");
        } else {
            model.addAttribute("success", true);
            model.addAttribute("savedNotes", this.noteService.getAllNotes(userId));
        }
        return "result";
    }
}
