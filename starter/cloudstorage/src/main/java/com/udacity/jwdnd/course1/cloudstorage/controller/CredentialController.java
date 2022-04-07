package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService,
                                UserService userService,
                                EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("add")
    public String addCredential(Authentication authentication,
                                @ModelAttribute("credential") Credential credential,
                                Model model) {
        Integer userid = userService.getUser(authentication.getName()).getUserid();
        if (credential.getCredentialid() == null) {
            credential.setUserid(userid);
            credential.setKey(this.encryptionService.generateKey());
            credential.setPassword(
                    this.encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
            this.credentialService.addCredential(credential);
        } else {
            credential.setKey(this.encryptionService.generateKey());
            credential.setPassword(
                    this.encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
            this.credentialService.updateByCredentialId(credential);
        }
        return "redirect:/result?success";
    }

    @GetMapping("delete/{id}")
    public String deleteCredential(@PathVariable Integer id, Model model, Authentication authentication) {
        if (id != null) {
            Integer userid = userService.getUser(authentication.getName()).getUserid();

            credentialService.deleteCredentialById(id);
            List<Credential> credentials = this.credentialService.getCredentialsByUserid(userid);
            model.addAttribute("credentials", credentials);
            return "redirect:/result?success";
        } else {
            return "redirect:/home";
        }
    }
}
