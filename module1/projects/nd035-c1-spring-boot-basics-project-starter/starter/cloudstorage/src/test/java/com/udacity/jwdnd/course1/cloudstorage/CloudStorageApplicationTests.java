package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.view.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.view.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.view.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    public static WebDriver driver;
    private String email;
    private String password;

    @LocalServerPort // (Spring) allow injection od server`s Port
    private Integer port;

    public String baseURL;


    @BeforeAll
    public static void loadDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void beforeAll() {
        baseURL = "http://localhost:" + port;
        driver.get(baseURL + "/signup");

        String firstname = "User";
        String lastname = "Very Quazy";
        email = "quazy.user@email.com";
        password = "123456";

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signup(firstname, lastname, email, password);

        Assertions.assertTrue(signUpPage.getSuccessMessage()
                .contains("You successfully signed up! Please continue to the"));
    }

    @AfterAll
    public static void quitDriver() {
        driver.quit();
        driver = null;
    }

    @Test
    public void getLoginPage() {
        driver.get(baseURL + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void doLoginWithNonExistentUser() {

        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("non.existing.user@server.com", "111111");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("login-error-msg")));
        String errorMsg = loginPage.getErrorMsg();
        Assertions.assertTrue(errorMsg
                .contains("Invalid username or password"));
    }

    @Test
    public void doSuccessfulLogin() throws InterruptedException {
        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        HomePage homePage = new HomePage(driver);
        Assertions.assertEquals("Home", driver.getTitle());
    }


    @Test
    public void createNoteAndVerifyDisplayed() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        WebElement homeWait = wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        HomePage homePage = new HomePage(driver);
        Assertions.assertEquals("Home", driver.getTitle());

        String noteTitle = "Note Title Test";
        String noteDescription = "Note Description Test";
//        wait.until(ExpectedConditions
//                .presenceOfElementLocated(By.id("btn-add-note")));
        homePage.addNote(noteTitle, noteDescription);
        Assertions.assertTrue(homePage.checkIfNoteExists(noteTitle));
    }

}
