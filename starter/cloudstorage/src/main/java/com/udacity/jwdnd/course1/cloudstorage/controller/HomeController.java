package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller

public class HomeController {
    private CredentialService credentialService;
    private FileService fileService;
    private NoteService noteService;
    private UserService userService;
    private EncryptionService encryptionService;

    public HomeController(CredentialService credentialService,
                          FileService fileService,
                          NoteService noteService,
                          UserService userService,
                          EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String getHomePage(Note note,
                              Credential credential,
                              Model model,
                              Authentication authentication) {
        Integer userid = userService.getUser(authentication.getName()).getUserid();

        List<Credential> credentials = this.credentialService.getCredentialsByUserid(userid);
        List<File> files = this.fileService.getFilesByUserid(userid);
        List<Note> notes = this.noteService.getNotesByUserid(userid);

        model.addAttribute("credentials", credentials);
        model.addAttribute("uploadFiles", files);
        model.addAttribute("notes", notes);
        model.addAttribute ("encryptionService", encryptionService);

        return "/home";
    }
}
