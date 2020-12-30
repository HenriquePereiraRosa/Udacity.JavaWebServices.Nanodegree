package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Set;


@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public String homeView(Model model) {
        model.addAttribute("files", this.fileService.getFiles());
        return "home";
    }

    @GetMapping("/files")
    public Set<File> getFiles() {
        return fileService.getFiles();
    }

    @GetMapping("/logout")
    public String loginView() {
        return "login";
    }

    @GetMapping("/download")
    public @ResponseBody byte[] getFileContent(@Param(value="filename") String filename,
                                             Model model,
                                             HttpServletResponse res) {
        File file = fileService.getFileByName(filename);
        if(file != null) {
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
    public String uploadFile(@ModelAttribute MultipartFile reqFile, Model model) throws IOException {
        System.out.println("Post Mapping upload-file" + reqFile.getOriginalFilename()); // todo debug

        if(!reqFile.isEmpty()) {
            Integer userId = null; // todo
            File file = new File(null,
                    StringUtils.cleanPath(reqFile.getOriginalFilename()),
                    reqFile.getContentType(),
                    reqFile.getSize(),
                    userId,
                    reqFile.getBytes());
            Integer id = fileService.saveFile(file);
            model.addAttribute("files", this.fileService.getFiles());
        }
        return "home";
    }
}
