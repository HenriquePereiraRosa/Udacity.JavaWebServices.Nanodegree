package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @GetMapping
    public String homeView(Model model,
                           HttpServletRequest req,
                           HttpServletResponse res) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            model.addAttribute("errorUpload",
                    "ERROR User not found!");
            return this.logout(req, res);
        }

        Set<File> files = fileService.getFilesById(user.getId());
        List<Note> notes = noteService.getAllByUserId(user.getId());
        List<Credential> credentials = credentialService.getAllById(user.getId());
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

    @GetMapping("/files")
    public Set<File> getFilesByUsername(@Param(value = "username") String username,
                                        Model model) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            model.addAttribute("errorUpload",
                    "Files not found from this Username!");
            return null;
        }
        Set<File> files = fileService.getFilesById(user.getId());
        return files;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse res) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, res, auth);
        }
        return "redirect:/login";
    }

    @GetMapping("/download")
    public @ResponseBody
    byte[] getFileContent(@Param(value = "filename") String filename,
                          Model model,
                          HttpServletResponse res) {
        File file = fileService.getFileByName(filename);
        if (file != null) {
            res.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            res.setHeader("Content-Disposition",
                    "attachment; filename=" + file.getFilename());
            try {
                ServletOutputStream outputStream = res.getOutputStream();
                outputStream.write(file.getFiledata());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                // todo Add ERROR message here
            }
            return file.getFiledata();
        }
        return null;
    }

    @GetMapping("/delete-file/{filename}")
    public String deleteFile(@PathVariable String filename, Model model) throws IOException {
        Integer id = fileService.deleteFile(filename);
        model.addAttribute("files", this.fileService.getFiles());
        return "home";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@ModelAttribute MultipartFile reqFile,
                             Model model,
                             HttpServletRequest req,
                             HttpServletResponse res) {
        System.out.println("Post Mapping upload-file" + reqFile.getOriginalFilename()); // todo debug
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            model.addAttribute("errorUpload",
                    "ERROR User not found!");
            return this.logout(req, res);
        }

        if (user == null) {
            model.addAttribute("errorUpload", "User not found!");
            return "home";
        }

        if (!reqFile.isEmpty()) {
            File fileDb = fileService.getFileByName(reqFile.getOriginalFilename());
            if (fileDb != null) {
                model.addAttribute("errorUpload", "File already stored!");
                return "home";
            }
            try {
                File file = new File(null,
                        StringUtils.cleanPath(reqFile.getOriginalFilename()),
                        reqFile.getContentType(),
                        reqFile.getSize(),
                        user.getId(),
                        reqFile.getBytes());
                Integer id = fileService.saveFile(file); // todo:  use ID returned
                model.addAttribute("files", this.fileService.getFilesById(user.getId()));
            } catch (FileSizeLimitExceededException e) {
                model.addAttribute("errorUpload", "File greater than 10MB!");
                return "home";
            } catch (Exception e) {
                model.addAttribute("errorUpload", "Error uploading the file!");
                return "home";
            }
        } else model.addAttribute("errorUpload", "File can't be void!");
        return "home";
    }


    @PostMapping("/note")
    public String saveNote(@ModelAttribute Note reqNote,
                           Model model,
                           HttpServletRequest req,
                           HttpServletResponse res) {
        System.out.println("Post Mapping saveNote(..): " + reqNote.getNoteTitle()); // todo debug
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            model.addAttribute("errorNote",
                    "ERROR User not found!");
            return this.logout(req, res);
        }

        if (user == null) {
            model.addAttribute("errorNote", "User not found!");
            return "home";
        }

        if (!reqNote.getNoteTitle().isEmpty()
                || reqNote.getNoteDescription().isEmpty()) {
            model.addAttribute("errorNote", "Note fields can't be void!");
        }
            Note noteDb = noteService.getByName(reqNote.getNoteTitle());
            if (noteDb != null) {
                noteDb.setNoteTitle(reqNote.getNoteTitle());
                noteDb.setNoteDescription(reqNote.getNoteDescription());
                Integer id = noteService.updateOne(noteDb); // todo:  use ID returned
                model.addAttribute("notes", this.noteService.getAllByUserId(user.getId()));
                model.addAttribute("errorNote", null);
                return "home";
            }
            try {
                Note note = new Note(null,
                        reqNote.getNoteTitle(),
                        reqNote.getNoteDescription(),
                        user.getId());
                Integer id = noteService.saveOne(note); // todo:  use ID returned
                model.addAttribute("notes", this.noteService.getAllByUserId(user.getId()));
                model.addAttribute("errorNote", null);
            } catch (Exception e) {
                model.addAttribute("errorNote", "Error saving a note!");
                return "home";
            }
        return "home";
    }
}
