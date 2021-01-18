package com.udacity.jwdnd.course1.cloudstorage.view.home;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NoteTab {
    private final JavascriptExecutor js;
    private final WebDriver driver;
    private NoteService noteService;

    @FindBy(id = "nav-notes-tab")
    private WebElement tabNotes;

    @FindBy(id = "btn-add-note")
    private WebElement btnAddNote;

    @FindBy(id = "btn-edit-note")
    private WebElement btnEditNote;

    @FindBy(id = "btn-delete-note")
    private WebElement btnDeleteNote;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    @FindBy(xpath = "//div//button[normalize-space()='Logout']")
    private WebElement btnLogout;

    @FindBy(id = "notes")
    private List<WebElement> notes;

    public NoteTab(WebDriver driver, NoteService noteService) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.noteService = noteService;
    }

    public void addNote(String title, String description) {
        js.executeScript("arguments[0].click();", tabNotes);
        js.executeScript("arguments[0].click();", btnAddNote);
        js.executeScript("arguments[0].value='" + title + "';", noteTitle);
        js.executeScript("arguments[0].value='" + description + "';", noteDescription);
        js.executeScript("arguments[0].click();", noteSubmit);
    }

    public void editNote(String title, String newTitle, String newDescription) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement btnDeleteNote;

        js.executeScript("arguments[0].click();", tabNotes);
        WebElement homeWait = wait.until(webDriver ->
                webDriver.findElement(By.id("btn-delete-note")));
        for (WebElement note : notes) {
            String noteRowHTML = note.getAttribute("innerHTML");
            System.out.println(noteRowHTML);
            if (noteRowHTML.contains(title)) {
                WebElement btnEditItem =
                        note.findElement(By.id("btn-edit-credential"));
                js.executeScript("arguments[0].click();", btnEditItem);
                js.executeScript("arguments[0].click();", tabNotes);
                js.executeScript("arguments[0].value='" + newTitle + "';", noteRowHTML);
                js.executeScript("arguments[0].value='" + newDescription + "';", noteDescription);
                js.executeScript("arguments[0].click();", noteSubmit);
            }
        }
    }

    public void deleteNote(String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement btnDeleteNote;

        js.executeScript("arguments[0].click();", tabNotes);
        WebElement homeWait = wait.until(webDriver ->
                webDriver.findElement(By.id("btn-delete-note")));
        for (WebElement note : notes) {
            String noteTitle = note.getAttribute("innerHTML");
            System.out.println(noteTitle);
            if (noteTitle.contains(title)) {
                WebElement btnDelItem =
                        note.findElement(By.id("btn-delete-note"));
                js.executeScript("arguments[0].click();", btnDelItem);
                return;
            }
        }
    }

    public Integer getTableRowsNumber(String title) {
        return notes.size();
    }

    public Boolean checkIfNoteExists(String title) {
        js.executeScript("arguments[0].click();", tabNotes);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement homeWait = wait.until(webDriver ->
                webDriver.findElement(By.id("btn-delete-note")));
        for (WebElement note : notes) {
            String noteTitle = note.getAttribute("innerHTML");
            System.out.println(noteTitle);
            if (noteTitle.contains(title))
                return true;
        }
        return false;
    }

    public Boolean checkNoteDescription(String title, String description) {
        Note note = noteService.getByTitle(title);
        if (note != null
                && note.getNoteDescription() != null
                && note.getNoteDescription().equals(description))
            return true;
        return false;
    }

    public void logout() {
        js.executeScript("arguments[0].click();", btnLogout);
    }
}
