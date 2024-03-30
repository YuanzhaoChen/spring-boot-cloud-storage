package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public NoteService(NoteMapper noteMapper, UserService userService, EncryptionService encryptionService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public int addNote(Note note){
        if (this.userService.getUserById(note.getUserId()) == null){ return -1; }
        return this.noteMapper.insertNote(note);
    }

    /**
     * Delete note stored in the database
     * @param noteId
     * @return 0 if note can be deleted successfully, otherwise 1
     * */
    public int deleteNote(Integer noteId){
        if (this.noteMapper.getNote(noteId)==null){ return 1; }
        this.noteMapper.deleteNote(noteId);
        return 0;
    }

    /**
     * Update note stored in the database
     * @param note
     * @return 0 if note can be updated successfully, otherwise 1
     * */
    public int updateNote(Note note){
        Integer noteId = note.getId();
        if (this.noteMapper.getNote(noteId)==null){ return 1; }
        this.noteMapper.updateNote(note);
        return 0;
    }

    public Note getNote(Integer noteId){
        return this.noteMapper.getNote(noteId);
    }

    public List<Note> getAllNotes(Integer userId){
        return this.noteMapper.getAllNotes(userId);
    }

}

