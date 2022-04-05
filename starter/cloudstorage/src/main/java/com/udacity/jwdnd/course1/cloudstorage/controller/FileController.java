package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
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
        Integer userid = userService.getUser(authentication.getName()).getUserid();
        String filename = fileUpload.getOriginalFilename();

        if (filename.length()>0 && this.fileService.checkFilename(userid, filename) == 0) {
            file.setFilename(filename);
            file.setContenttype(fileUpload.getContentType());
            file.setFilesize(String.valueOf(fileUpload.getSize()));
            file.setFiledata(fileUpload.getInputStream().readAllBytes());
            file.setUserid(userid);
            this.fileService.addFile(file);

            model.addAttribute("uploadFiles", this.fileService.getFiles());
            response.sendRedirect("/result?success");
        } else {
            if (filename.length()==0) {
                response.sendRedirect("/result?nofileselect");
            } else {
                response.sendRedirect("/result?erroruploadfile");
            }
        }
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
    public void handleDeleteFile(@PathVariable Integer id,
                                 Model model,
                                 HttpServletResponse response,
                                 Authentication authentication) throws IOException {
        if (id != null) {
            Integer userid = userService.getUser(authentication.getName()).getUserid();

            fileService.deleteFileById(id);
            List<File> files = this.fileService.getFilesByUserid(userid);
            model.addAttribute("uploadFiles", files);
            response.sendRedirect("result?success");
        } else {
            response.sendRedirect("/home");
        }
    }
}
