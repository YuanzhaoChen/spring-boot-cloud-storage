package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/home/add-credential")
    public String addOrUpdateCredential(Authentication authentication,
                                        @ModelAttribute CredentialForm credentialForm,
                                        Model model) {
        String username = authentication.getName(); // current signed-in user
        Integer userId = this.userService.getUser(username).getId();
        if (credentialForm.getId() == null) {
            this.credentialService.addCredential(new Credential(
                    credentialForm.getId(),
                    credentialForm.getUrl(),
                    credentialForm.getUserName(),
                    credentialForm.getKey(),
                    credentialForm.getRawPassword(),
                    userId
            ));
        } else {
            credentialForm.setUserId(userId);
            this.credentialService.updateCredential(credentialForm);
        }
        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("/home/delete-credential")
    public String delete(Authentication authentication, @RequestParam(value = "id") String id, Model model) {
        Integer credentialId = Integer.parseInt(id);
        String username = authentication.getName(); // current signed-in user
        Integer userId = this.userService.getUser(username).getId();
        if (!userId.equals(this.credentialService.getCredential(credentialId).getUserId())) {
            model.addAttribute("error", "User is not authorized to delete credentials");
        } else if (this.credentialService.deleteCredential(credentialId)!=0) {
            model.addAttribute("error", "Cannot delete credential, please try again");
        }else{
            model.addAttribute("success", true);
        }
        return "result";
    }
}
