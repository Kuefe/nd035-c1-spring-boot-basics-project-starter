package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("upload")
    public void handleFileUpload(
            Authentication authentication,
            @ModelAttribute("file") File file,
            @RequestParam("fileUpload") MultipartFile fileUpload,
            Model model,
            HttpServletResponse response) throws IOException {
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContenttype(fileUpload.getContentType());
        file.setFilesize(String.valueOf(fileUpload.getSize()));
        file.setFiledata(fileUpload.getInputStream().readAllBytes());
        file.setUserid(userService.getUser(authentication.getName()).getUserid());
        this.fileService.addFile(file);

        model.addAttribute("uploadFiles", this.fileService.getFiles());

        response.sendRedirect("/home");
    }

    @GetMapping("download/{id}")
    byte[] handleFileDownload(
            @PathVariable Integer id,
            HttpServletResponse response,
            Model model
    ) {
        if (id != null) {
            File file = this.fileService.getFileById(id);
            response.setContentType(file.getContenttype());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename = " + file.getFilename();
            response.setHeader(headerKey, headerValue);
            try {
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(file.getFiledata());
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return file.getFiledata();
        }
        return null;
    }

    @GetMapping("delete/{id}")
    public void handleDeleteFile(@PathVariable Integer id, Model model,
                                   HttpServletResponse response) throws IOException {
        if (id != null) {
            fileService.deleteFileById(id);
            List<File> files = this.fileService.getFiles();
            model.addAttribute("uploadFiles", this.fileService.getFiles());
        }
        response.sendRedirect("/home");
    }
}
