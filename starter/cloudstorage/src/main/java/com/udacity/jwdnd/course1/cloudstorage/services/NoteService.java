package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes() {
        return noteMapper.getNotes();
    }

    public List<Note> getNOtesByUserid(Integer userid) {
        return noteMapper.getNoteByUserid(userid);
    }

    public void addNote(Note note) {
        noteMapper.insert(note);
    }

    public Note getNoteById(Integer id) {
        return noteMapper.getNoteByNoteid(id);
    }

    public int updateNoteById(Note note) {
        return noteMapper.updateNoteByNoteid(note);
    }

    public int deleteNoteById(Integer id) {
        return noteMapper.deleteNoteByNoteid(id);
    }
}
