package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


@Controller
public class FileController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @GetMapping("/download")
    public @ResponseBody
    byte[] getFileContent(@Param(value = "filename") String filename,
                          Model model,
                          RedirectAttributes redirectAttributes,
                          HttpServletResponse res) {
        redirectAttributes.addFlashAttribute("activeTab", "files");
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
    public String deleteFile(@PathVariable String filename,
                             RedirectAttributes redirectAttributes,
                             Model model) throws IOException {
        redirectAttributes.addFlashAttribute("activeTab", "files");
        Integer id = fileService.deleteFile(filename);
        model.addAttribute("files", this.fileService.getFiles());
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/result";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@ModelAttribute MultipartFile reqFile,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest req,
                             HttpServletResponse res) {
        redirectAttributes.addFlashAttribute("activeTab", "files");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            redirectAttributes.addFlashAttribute("message","ERROR User not found!");
            return "redirect:/logout";
        }

        if (!reqFile.isEmpty()) {
            File fileDb = fileService.getFileByName(reqFile.getOriginalFilename());
            if (fileDb != null) {
                redirectAttributes.addFlashAttribute("message", "File already stored!");
                return "redirect:/result";
            }
            try {
                File file = new File(null,
                        StringUtils.cleanPath(reqFile.getOriginalFilename()),
                        reqFile.getContentType(),
                        reqFile.getSize(),
                        user.getId(),
                        reqFile.getBytes());
                Integer id = fileService.saveFile(file); // todo:  use ID returned
//                model.addAttribute("files", this.fileService.getFilesById(user.getId()));
                redirectAttributes.addFlashAttribute("success", true);
            } catch (FileSizeLimitExceededException e) {
                redirectAttributes.addFlashAttribute("message", "File greater than 10MB!");
                return "redirect:/result";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "Error uploading the file!");
                return "redirect:/result";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "File can't be void!");
        }
        return "redirect:/result";
    }
}
