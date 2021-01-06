package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FileService {

    @Autowired
    FileMapper fileMapper;

    public File getFileByName(String filename) {
        File file = fileMapper.getFileByFilename(filename);
        return file;
    }

    public Set<File> getFiles() {
        return fileMapper.getFiles();
    }

    public Integer saveFile(File file) {
        return fileMapper.insertFile(file);
    }

    public Integer deleteFile(String filename) {
        return fileMapper.deleteFile(filename);
    }

    public Set<File> getFilesById(Integer id) {
        return fileMapper.getFilesById(id);
    }
}
