package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping()
    public void getNotes() {
        System.out.println("NoteController GetMapping");
    }

    @PostMapping("add")
    public String addNote(Authentication authentication,
                          @ModelAttribute("note") Note note,
                          Model model) {
        System.out.println(note.getNoteid());
        if (note.getNoteid() == null) {
            note.setUserid(userService.getUser(authentication.getName()).getUserid());
            this.noteService.addNote(note);
        } else {
            this.noteService.updateNoteById(note);
        }
        model.addAttribute("notes", this.noteService.getNotes());
        return "redirect:/home";
    }

    @GetMapping("edit/{id}")
    public String editNote(@PathVariable Integer id, @ModelAttribute("note") Note note, Model model) {
        if (id != null) {
            model.addAttribute("note", this.noteService.getNoteById(id));
        }
        return "redirect:/home";
    }

    @GetMapping("delete/{id}")
    public String deleteNote(@PathVariable Integer id, Model model) {
        if (id != null) {
            noteService.deleteNoteById(id);
            List<Note> notes = this.noteService.getNotes();
            model.addAttribute("notes", this.noteService.getNotes());
        }
        return "redirect:/home";
    }
}
