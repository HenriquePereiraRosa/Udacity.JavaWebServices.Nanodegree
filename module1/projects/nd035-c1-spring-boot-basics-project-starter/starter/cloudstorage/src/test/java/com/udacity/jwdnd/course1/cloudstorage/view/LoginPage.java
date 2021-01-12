package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final JavascriptExecutor js;

    @FindBy(id = "login-error-msg")
    private WebElement errorMsg;

    @FindBy(id = "login-logout-msg")
    private WebElement logoutMsg;

    @FindBy(id = "login-input-username")
    private WebElement usernameField;

    @FindBy(id = "login-input-password")
    private WebElement passwordField;

    @FindBy(id = "login-input-password")
    private WebElement loginButton;

    @FindBy(id = "login-signup-link")
    private WebElement signupLink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
    }

    public void login(String username, String password) {
        js.executeScript("arguments[0].value='" + username + "';", usernameField);
        js.executeScript("arguments[0].value='" + password + "';", passwordField);
        js.executeScript("arguments[0].click();", loginButton);
    }

    public String getErrorMsg() {
        return this.errorMsg.getText();
    }
    public WebElement getErrorElement() {
        return this.errorMsg;
    }

    public String getLogoutMsg() {
        return this.logoutMsg.getText();
    }
}
