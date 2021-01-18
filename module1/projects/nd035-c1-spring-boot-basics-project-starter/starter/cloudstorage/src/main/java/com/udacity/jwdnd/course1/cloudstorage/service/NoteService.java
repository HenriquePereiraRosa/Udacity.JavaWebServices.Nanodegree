package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    NoteMapper mapper;

    public Note getByTitle(String noteTitle) {
        return mapper.getByTitle(noteTitle);
    }

    public List<Note> getAllByUserId(Integer id) {
        return mapper.getAllByUserId(id);
    }

    public Integer saveOne(Note note) {
        return mapper.insertOne(note);
    }

    public Integer updateOne(Note note) {
        return mapper.updateOne(note);
    }

    public Integer deleteById(Integer id) {
        return mapper.deleteById(id);
    }

    public Integer deleteByTitle(String noteTitle) {
        return mapper.deleteByTitle(noteTitle);
    }
}
