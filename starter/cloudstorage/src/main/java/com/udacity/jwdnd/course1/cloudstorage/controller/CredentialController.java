package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("add")
    public String addCredential(Authentication authentication,
                                @ModelAttribute("credential") Credential credential,
                                Model model) {
        Integer userid = userService.getUser(authentication.getName()).getUserid();
        if (credential.getCredentialid() == null) {
            credential.setUserid(userid);
            this.credentialService.addCredential(credential);
        } else {
            this.credentialService.updateByCredentialId(credential);
        }
        model.addAttribute("credentials", this.credentialService.getCredentialsByUserid(userid));
        return "redirect:/home";
    }

    @GetMapping("edit/{id}")
    public String editCredential(@PathVariable Integer id, @ModelAttribute("credential") Credential credential, Model model) {
        if (id != null) {
            model.addAttribute("note", this.credentialService.getCredentialById(id));
        }
        return "redirect:/home";
    }

    @GetMapping("delete/{id}")
    public String deleteCredential(@PathVariable Integer id, Model model, Authentication authentication) {
        if (id != null) {
            Integer userid = userService.getUser(authentication.getName()).getUserid();

            credentialService.deleteCredentialById(id);
            List<Credential> credentials = this.credentialService.getCredentialsByUserid(userid);
            model.addAttribute("credentials", credentials);
        }
        return "redirect:/home";
    }
}
