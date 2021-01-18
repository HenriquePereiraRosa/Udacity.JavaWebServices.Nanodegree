package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;


@Controller
public class CredentialController {

    @Autowired
    private UserService userService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private HashService hashService;

    @PostMapping("/credential")
    public String saveCredential(@ModelAttribute Credential reqCredential,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest req,
                                 HttpServletResponse res) {
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            redirectAttributes.addFlashAttribute("message","User not found!");
            return "redirect:/logout";
        }

        if (reqCredential.getUrl().isEmpty()
                || reqCredential.getUrl() == null
                || reqCredential.getUsername().isEmpty()
                || reqCredential.getUsername() == null
                || reqCredential.getPassword().isEmpty()
                || reqCredential.getPassword() == null) {
            redirectAttributes.addFlashAttribute("message", "Credential fields can't be void!");
        }
        if (reqCredential.getId() == null) {
            reqCredential.setUserid(user.getId());
            Integer id = credentialService.saveOne(reqCredential);
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/result";
        } else {
            Integer id = credentialService.updateOne(reqCredential);
            redirectAttributes.addFlashAttribute("success", true);
        }
        return "redirect:/result";
    }

    @GetMapping("/delete-credential/{id}")
    public String deleteCredential(@PathVariable Integer id,
                                   RedirectAttributes redirectAttributes,
                                   Model model) throws IOException {
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        Integer idDel = credentialService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/result";
    }
}
