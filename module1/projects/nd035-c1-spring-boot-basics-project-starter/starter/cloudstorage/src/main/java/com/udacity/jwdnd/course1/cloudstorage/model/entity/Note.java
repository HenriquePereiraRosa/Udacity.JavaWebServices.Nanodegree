package com.udacity.jwdnd.course1.cloudstorage.model.entity;

public class Note {

    private Integer id;
    private String noteTitle;
    private String noteDescription;
    private Integer userid;

    public Note(Integer id, String noteTitle, String noteDescription, Integer userid) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserId() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
