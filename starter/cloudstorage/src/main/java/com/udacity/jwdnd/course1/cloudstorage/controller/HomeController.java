package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;
    private UserService userService;

    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping()
    public String getHomePage(Model model) {
        List<File> files = this.fileService.getFiles();
        model.addAttribute("uploadFiles", this.fileService.getFiles());
        return "home";
    }

    @PostMapping()
    public String handleFileUpload(
            Authentication authentication,
            @ModelAttribute("file") File file,
            @RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContenttype(fileUpload.getContentType());
        file.setFilesize(String.valueOf(fileUpload.getSize()));
        file.setFiledata(fileUpload.getInputStream().readAllBytes());
        file.setUserid(userService.getUser(authentication.getName()).getUserid());
        fileService.addFile(file);

        model.addAttribute("uploadFiles", this.fileService.getFiles());

        return "home";
    }
}
