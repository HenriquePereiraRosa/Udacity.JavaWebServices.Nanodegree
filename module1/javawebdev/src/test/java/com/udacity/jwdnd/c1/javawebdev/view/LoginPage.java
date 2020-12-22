package com.udacity.jwdnd.c1.javawebdev.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        usernameField.clear();
        usernameField.sendKeys(email);
        passwordField.clear();
        passwordField.sendKeys(password);
        submitButton.click();
    }

    public String getErrorMsg() {
        return this.errorMsg.getText();
    }

    public String getLogoutMsg() {
        return this.logoutMsg.getText();
    }
}
