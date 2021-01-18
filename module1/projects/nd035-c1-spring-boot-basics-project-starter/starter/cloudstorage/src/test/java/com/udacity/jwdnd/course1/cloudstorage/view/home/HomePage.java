package com.udacity.jwdnd.course1.cloudstorage.view.home;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final JavascriptExecutor js;
    private final WebDriver driver;

    @FindBy(xpath = "//div//button[normalize-space()='Logout']")
    private WebElement btnLogout;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }

    public void logout() {
        js.executeScript("arguments[0].click();", btnLogout);
    }
}
