package com.example.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class SeleniumTest {
    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/animal");
        WebElement inputAnimalText = driver.findElement(By.id("animalText"));
        WebElement inputAdjective = driver.findElement(By.id("adjective"));
        inputAnimalText.sendKeys("Animal");
        inputAdjective.sendKeys("Bonito");
        for (int counter = 0; counter < 5; counter++) {
            WebElement inputSubmit = driver.findElement(By.xpath("//input[@type='submit']"));
            inputSubmit.submit();
        }
        List<WebElement> results = driver.findElements(By.className("conclusionMessage"));
        for (WebElement element : results) {
            System.out.println(element.getText());
        }
        Thread.sleep(2000);
        driver.quit();

    }
}
