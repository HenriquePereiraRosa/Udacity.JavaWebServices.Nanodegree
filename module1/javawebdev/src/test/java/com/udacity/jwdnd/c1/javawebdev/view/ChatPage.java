package com.udacity.jwdnd.c1.javawebdev.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ChatPage {

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "messageText")
    private WebElement messageText;

    @FindBy(id = "messageType")
    private WebElement messageType;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement submit;

    @FindBy(id = "messages")
    private List<WebElement> messages;

    @FindBy(tagName = "title")
    private WebElement title;

    public ChatPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void sendMessage(String email, String message) {
        emailField.clear();
        emailField.sendKeys(email);
        messageText.clear();
        messageText.sendKeys(message);
        submit.click();
    }

    public String getTitle() {
        return this.title.getText();
    }

    public String getFirstMessage() {
        return this.messages.get(0).getText();
    }

}
