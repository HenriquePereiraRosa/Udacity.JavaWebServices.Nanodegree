package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("/home")
    public String homeView(Model model,
                           HttpServletRequest req,
                           HttpServletResponse res) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            model.addAttribute("errorUpload",
                    "ERROR User not found!");
            return "redirect:/logout";
        }

        Set<File> files = fileService.getFilesById(user.getId());
        List<Note> notes = noteService.getAllByUserId(user.getId());
        List<Credential> credentials = credentialService.getAllByUserId(user.getId());
        if (user == null) {
            model.addAttribute("errorUpload",
                    "Not files found from this Username!");
        }
        if (notes == null) {
            model.addAttribute("errorNote",
                    "Notes not found from this Username!");
            return null;
        }
        if (credentials == null) {
            model.addAttribute("errorCredentials",
                    "Credentials not found from this Username!");
            return null;
        }
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        return "home";
    }
}
