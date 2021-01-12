package com.udacity.jwdnd.course1.cloudstorage.view;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HomePage {
    private final JavascriptExecutor js;

    //  NOTE SECTION
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


    //  CREDENTIAL SECTION
    @FindBy(id = "nav-credentials-tab")
    private WebElement tabCredentials;

    @FindBy(id = "btn-add-credential")
    private WebElement btnAddCredential;

    @FindBy(id = "btn-edit-credential")
    private WebElement btnEditCredential;

    @FindBy(id = "btn-delete-credential")
    private WebElement btnDeleteCredential;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    private List<Credential> credentials;

    @Autowired
    NoteService noteService;
    @Autowired
    CredentialService credentialService;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
    }

    public void addNote(String title, String description) {
        js.executeScript("arguments[0].click();", btnAddNote);
        js.executeScript("arguments[0].value='" + title + "';", noteTitle);
        js.executeScript("arguments[0].value='" + description + "';", noteDescription);
        js.executeScript("arguments[0].click();", noteSubmit);
    }

    public Boolean checkIfNoteExists(String title) {
        Note note = noteService.getByTitle(title);
        if (note == null
                || note.getNoteTitle().isEmpty()
                || note.getNoteDescription().isEmpty())
            return false;
        return true;
    }

    public void addCredential(String url,
                              String username,
                              String password) {
        js.executeScript("arguments[0].click();", btnAddCredential);
        js.executeScript("arguments[0].value='" + url + "';", credentialUrl);
        js.executeScript("arguments[0].value='" + username + "';", credentialUsername);
        js.executeScript("arguments[0].value='" + password + "';", credentialPassword);
        js.executeScript("arguments[0].click();", credentialSubmit);
    }

    public Boolean checkIfCredentialExists(String url) {
        Credential credential = credentialService.getByUrl(url);
        if (credential == null
                || credential.getUsername().isEmpty()
                || credential.getPassword().isEmpty())
            return false;
        return true;
    }
}
