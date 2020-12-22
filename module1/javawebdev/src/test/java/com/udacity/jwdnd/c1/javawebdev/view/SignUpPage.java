package com.udacity.jwdnd.c1.javawebdev.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputEmail")
    private WebElement inputEmail;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstname,
                       String lastname,
                       String email,
                       String password) {
        inputFirstName.clear();
        inputFirstName.sendKeys(firstname);
        inputLastName.clear();
        inputLastName.sendKeys(lastname);
        inputEmail.clear();
        inputEmail.sendKeys(email);
        inputPassword.clear();
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    public String getSuccessMessage() {
        return this.successMsg.getText();
    }

    public String getErrorSuccessMessage() {
        return this.errorMsg.getText();
    }
}
