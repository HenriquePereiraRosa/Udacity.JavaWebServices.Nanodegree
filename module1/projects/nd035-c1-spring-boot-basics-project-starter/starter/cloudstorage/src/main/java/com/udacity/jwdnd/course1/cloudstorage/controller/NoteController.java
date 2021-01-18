package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class NoteController {

    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;


    @PostMapping("/note")
    public String saveNote(@ModelAttribute Note reqNote,
                           Model model,
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest req,
                           HttpServletResponse res) {
        redirectAttributes.addFlashAttribute("activeTab", "notes");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            redirectAttributes.addFlashAttribute("message","User not found!");
            return "redirect:/logout";
        }
        if (reqNote.getNoteTitle() == null
                || reqNote.getNoteTitle().isEmpty()
                || reqNote.getNoteDescription() == null
                || reqNote.getNoteDescription().isEmpty()) {
            model.addAttribute("message", "Note fields can't be void!");
        }
//        if(noteService.getByTitle(reqNote.getNoteTitle()) == null) {
//            model.addAttribute("notes", this.noteService.getAllByUserId(user.getId()));
//            redirectAttributes.addFlashAttribute("message", "Note Already exists!");
//            return "redirect:/result";
//        }
        Note noteDb = noteService.getById(reqNote.getId());
        if (reqNote.getId() == null) {
            reqNote.setUserid(user.getId());
            Integer id = noteService.saveOne(reqNote);
            redirectAttributes.addFlashAttribute("success", true);
        } else {
            reqNote.setUserid(user.getId());
            Integer id = noteService.updateOne(reqNote);
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/result";
        }
        return "redirect:/result";
    }

    @GetMapping("/delete-note/{id}")
    public String deleteNote(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes,
                             Model model) throws IOException {
        redirectAttributes.addFlashAttribute("activeTab", "notes");
        Integer idDel = noteService.deleteById(id);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = (String) auth.getPrincipal();
//        User user = userService.getUserByUsername(username);
//        model.addAttribute("notes", this.noteService.getAllByUserId(user.getId()));
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/result";
    }
}
