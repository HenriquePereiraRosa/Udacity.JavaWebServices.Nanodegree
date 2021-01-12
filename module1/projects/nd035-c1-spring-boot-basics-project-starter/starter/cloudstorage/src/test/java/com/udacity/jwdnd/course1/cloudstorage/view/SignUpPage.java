package com.udacity.jwdnd.course1.cloudstorage.view;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    private final JavascriptExecutor js;

    @FindBy(id = "signup-success-msg")
    private WebElement successMsg;

    @FindBy(id = "signup-error-msg")
    private WebElement errorMsg;

    @FindBy(id = "signup-input-firstname")
    private WebElement inputFirstName;

    @FindBy(id = "signup-input-lastname")
    private WebElement inputLastName;

    @FindBy(id = "signup-input-username")
    private WebElement inputEmail;

    @FindBy(id = "signup-input-password")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signupButton;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
    }

    public void signup(String firstname,
                       String lastname,
                       String username,
                       String password) {
        js.executeScript("arguments[0].value='" + firstname + "';", inputFirstName);
        js.executeScript("arguments[0].value='" + lastname + "';", inputLastName);
        js.executeScript("arguments[0].value='" + username + "';", inputEmail);
        js.executeScript("arguments[0].value='" + password + "';", inputPassword);
        js.executeScript("arguments[0].click();", signupButton);
    }

    public String getSuccessMessage() {
        return this.successMsg.getText();
    }

    public String getErrorSuccessMessage() {
        return this.errorMsg.getText();
    }
}
