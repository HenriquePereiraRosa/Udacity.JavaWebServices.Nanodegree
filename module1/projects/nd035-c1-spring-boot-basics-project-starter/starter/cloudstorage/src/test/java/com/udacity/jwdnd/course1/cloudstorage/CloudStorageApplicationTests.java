package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.view.home.CredentialTab;
import com.udacity.jwdnd.course1.cloudstorage.view.home.NoteTab;
import com.udacity.jwdnd.course1.cloudstorage.view.login.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.view.signup.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    public static WebDriver driver;
    private String email;
    private String password;

    @Autowired
    NoteService noteService;
    @Autowired
    CredentialService credentialService;

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
    public void doSuccessfulLoginAndLogout() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        NoteTab noteTab = new NoteTab(driver, noteService);
        Assertions.assertEquals("Home", driver.getTitle());

        noteTab.logout();
        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));
        Assertions.assertEquals("Login", driver.getTitle());
    }


    @Test
    public void createNoteAndVerifyDisplayed() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        NoteTab noteTab = new NoteTab(driver, noteService);
        Assertions.assertEquals("Home", driver.getTitle());

        String noteTitle = "Note Title Test";
        String noteDescription = "Note Description Test";
//        wait.until(ExpectedConditions
//                .presenceOfElementLocated(By.id("btn-add-note")));
        noteTab.addNote(noteTitle, noteDescription);
        Assertions.assertTrue(noteTab.checkIfNoteExists(noteTitle));
        Assertions.assertTrue(noteTab
                .checkNoteDescription(noteTitle, noteDescription));
    }


    @Test
    public void createCredtialAndVerifyDisplayed() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        WebElement homeWait = wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        CredentialTab credentialTab = new CredentialTab(driver, credentialService);
        Assertions.assertEquals("Home", driver.getTitle());

        String url = "google.com";
        String username = "Note Title Test";
        String password = "123";
        credentialTab.addCredential(url, username, password);
        Assertions.assertTrue(credentialTab.checkIfCredentialExists(url));
    }

}
