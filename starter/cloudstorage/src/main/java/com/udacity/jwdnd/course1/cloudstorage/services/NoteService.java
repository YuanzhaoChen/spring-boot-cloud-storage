package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.enums.ServiceResponse;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

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

    public ServiceResponse addNote(Note note) {
        if (this.userService.getUserById(note.getUserId()) == null) {
            return ServiceResponse.USER_NOT_FOUND_ERROR;
        }
        try {
            int rowsAdded = this.noteMapper.insertNote(note);
            if (rowsAdded == 1) {
                return ServiceResponse.SUCCESS;
            } else {
                return ServiceResponse.DATABASE_ERROR;
            }
        } catch (Exception e) {
            return ServiceResponse.DATABASE_ERROR;
        }

    }

    public ServiceResponse deleteNote(Integer noteId) {
        if (this.noteMapper.getNote(noteId) == null) {
            return ServiceResponse.RECORD_NOT_FOUND_ERROR;
        }
        try {
            this.noteMapper.deleteNote(noteId);
            return ServiceResponse.SUCCESS;
        } catch (Exception e) {
            return ServiceResponse.DATABASE_ERROR;
        }
    }

    public ServiceResponse updateNote(Note note) {
        Integer noteId = note.getId();
        if (this.noteMapper.getNote(noteId) == null) {
            return ServiceResponse.USER_NOT_FOUND_ERROR;
        }
        try {
            this.noteMapper.updateNote(note);
            return ServiceResponse.SUCCESS;
        } catch (Exception e) {
            return ServiceResponse.DATABASE_ERROR;
        }
    }

    public Note getNote(Integer noteId) {
        return this.noteMapper.getNote(noteId);
    }

    public List<Note> getAllNotes(Integer userId) {
        return this.noteMapper.getAllNotes(userId);
    }

}

