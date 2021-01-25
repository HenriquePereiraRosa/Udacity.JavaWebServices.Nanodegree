package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.view.home.CredentialTab;
import com.udacity.jwdnd.course1.cloudstorage.view.home.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.view.home.NoteTab;
import com.udacity.jwdnd.course1.cloudstorage.view.home.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.view.login.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.view.signup.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    public static WebDriver driver;
    private String email = "quazy.user@email.com";
    private String password = "123456";
    private String noteTitle = "Note Title Test";
    private String noteDescription = "Note Description Test";
    private String credUrl = "google.com";
    private String credUsername = "email@server.com";
    private String credPassword = "123";

    @Autowired
    NoteService noteService;
    @Autowired
    CredentialService credentialService;
    @Autowired
    EncryptionService encryptionService;

    @LocalServerPort // (Spring) allow injection od server`s Port
    private Integer port;
    public String baseURL;


    @BeforeAll
    public static void loadDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-minimized");
        driver = new ChromeDriver(options);
        driver.manage().window()
                .setPosition(new Point(-1100, 0));
        driver.manage().window().maximize();

    }

    @BeforeEach
    public void beforeAll() {
        baseURL = "http://localhost:" + port;

        String firstname = "User";
        String lastname = "Very Quazy";

        WebDriverWait wait = new WebDriverWait(driver, 2);
        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, credPassword);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));
        String titleTag = driver.getTitle();

        if (!titleTag.equals("Home")) {
            driver.get(baseURL + "/signup");
            SignUpPage signUpPage = new SignUpPage(driver);
            signUpPage.signup(firstname, lastname, email, credPassword);

        } else {
            HomePage homePage = new HomePage(driver);
            if (driver.getTitle().equals("Home"))
                homePage.logout();
        }
    }


    @AfterEach
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        HomePage homePage = new HomePage(driver);
        String titleTag = driver.getTitle();
        if (titleTag.equals("Home"))
            homePage.logout();

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));
        Assertions.assertEquals("Login", driver.getTitle());
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

        WebDriverWait wait = new WebDriverWait(driver, 2);
        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("non.existing.user@server.com", "111111");
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("login-error-msg")));
        String errorMsg = loginPage.getErrorMsg();
        Assertions.assertTrue(errorMsg
                .contains("Invalid username or password"));

        driver.get(baseURL + "/home");
        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void tryUnauthGetToHome() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        driver.get(baseURL + "/home");
        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void doSuccessfulLoginAndLogout() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, credPassword);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));
        Assertions.assertEquals("Home", driver.getTitle());

        HomePage homePage = new HomePage(driver);
        homePage.logout();
        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));
        Assertions.assertEquals("Login", driver.getTitle());
    }


    @Test
    public void createNoteAndVerifyDisplayed() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        String noteTitle = "Note Title Test";
        String noteDescription = "Note Description Test";

        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(this.email, this.credPassword);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        NoteTab noteTab = new NoteTab(driver, noteService);
        Assertions.assertEquals("Home", driver.getTitle());

        noteTab.addNote(noteTitle, noteDescription);
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.checkSuccessMessage());
        resultPage.clickContinue();

        Assertions.assertTrue(noteTab.checkIfNoteExists(noteTitle));
        Assertions.assertTrue(noteTab
                .checkNoteDescription(noteTitle, noteDescription));
    }

    @Test
    public void createNoteAndDeleteIt() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(this.email, this.credPassword);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        NoteTab noteTab = new NoteTab(driver, noteService);
        Assertions.assertEquals("Home", driver.getTitle());

        ResultPage resultPage = new ResultPage(driver);
        for (int i = 1; i < 3; i++) {
            noteTab.addNote(noteTitle + i, noteDescription + i);
            Assertions.assertTrue(resultPage.checkSuccessMessage());
            resultPage.clickContinue();
        }
        Assertions.assertTrue(noteTab.checkIfNoteExists(noteTitle + 1));
        Assertions.assertTrue(noteTab
                .checkNoteDescription(noteTitle + 1, noteDescription + 1));

        noteTab.deleteNote(noteTitle + 1);
        Assertions.assertTrue(resultPage.checkSuccessMessage());
        resultPage.clickContinue();
        Assertions.assertTrue(!noteTab.checkIfNoteExists(noteTitle + 1));
        Assertions.assertTrue(!noteTab
                .checkNoteDescription(noteTitle + 1, noteDescription + 1));
    }

    @Test
    public void createNoteAndEditIt() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(this.email, this.credPassword);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        NoteTab noteTab = new NoteTab(driver, noteService);
        Assertions.assertEquals("Home", driver.getTitle());

        ResultPage resultPage = new ResultPage(driver);
        for (int i = 1; i < 4; i++) {
            noteTab.addNote(noteTitle + i, noteDescription + i);
            Assertions.assertTrue(resultPage.checkSuccessMessage());
            resultPage.clickContinue();
        }
        Assertions.assertTrue(noteTab.checkIfNoteExists(noteTitle + 1));
        Assertions.assertTrue(noteTab
                .checkNoteDescription(noteTitle + 1, noteDescription + 1));

        String noteTitleEdited = noteTitle + " Edited";
        String noteDescEdited = noteDescription + " Edited";
        noteTab.editNote(noteTitle + 1, noteTitleEdited, noteDescEdited);
        Assertions.assertTrue(resultPage.checkSuccessMessage());
        resultPage.clickContinue();
        Assertions.assertTrue(noteTab.checkIfNoteExists(noteTitleEdited));
        Assertions.assertTrue(noteTab
                .checkNoteDescription(noteTitleEdited, noteDescEdited));
    }


    @Test
    public void createCredentialAndVerifyDisplayed() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        String credUrl = "google.com";
        String credUsername = "username";
        String credPassword = "pass123";

        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(this.email, this.credPassword);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        CredentialTab credentialTab = new CredentialTab(driver,
                credentialService, encryptionService);
        Assertions.assertEquals("Home", driver.getTitle());

        credentialTab.addCredential(credUrl, credUsername, credPassword);
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.checkSuccessMessage());
        resultPage.clickContinue();

        Assertions.assertTrue(credentialTab.checkIfCredentialExists(credUrl));
        Assertions.assertTrue(credentialTab
                .checkCredentialContent(credUrl, credUsername, credPassword));
    }

    @Test
    public void createCredentialAndDeleteIt() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        String credUrl = "google.com";
        String credUsername = "username";
        String credPassword = "pass123";

        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(this.email, this.credPassword);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        CredentialTab credentialTab = new CredentialTab(driver,
                credentialService, encryptionService);
        Assertions.assertEquals("Home", driver.getTitle());

        ResultPage resultPage = new ResultPage(driver);
        for (int i = 1; i < 3; i++) {
            credentialTab.addCredential(credUrl + i,
                    credUsername + i, credPassword + 1);
            Assertions.assertTrue(resultPage.checkSuccessMessage());
            resultPage.clickContinue();
        }
        Assertions.assertTrue(credentialTab.checkIfCredentialExists(credUrl + 1));
        Assertions.assertTrue(credentialTab
                .checkCredentialContent(credUrl + 1, credUsername + 1,
                        credPassword + 1));

        credentialTab.deleteCredential(credUrl + 1);
        Assertions.assertTrue(resultPage.checkSuccessMessage());
        resultPage.clickContinue();
        Assertions.assertTrue(!credentialTab.checkIfCredentialExists(credUrl + 1));
        Assertions.assertTrue(!credentialTab
                .checkCredentialContent(credUrl + 1,
                        credUsername + 1, credPassword + 1));
    }

    @Test
    public void createCredentialAndEditIt() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        String credUrl = "google.com";
        String credUsername = "username";
        String credPassword = "pass123";

        driver.get(baseURL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(this.email, this.credPassword);

        wait.until(webDriver ->
                webDriver.findElement(By.tagName("title")));

        CredentialTab credentialTab = new CredentialTab(driver,
                credentialService, encryptionService);
        Assertions.assertEquals("Home", driver.getTitle());

        ResultPage resultPage = new ResultPage(driver);
        for (int i = 1; i < 3; i++) {
            credentialTab.addCredential(credUrl + i,
                    credUsername + i, credPassword + 1);
            Assertions.assertTrue(resultPage.checkSuccessMessage());
            resultPage.clickContinue();
        }
        Assertions.assertTrue(credentialTab.checkIfCredentialExists(credUrl + 1));
        Assertions.assertTrue(credentialTab
                .checkCredentialContent(credUrl + 1,
                        credUsername + 1, credPassword + 1));

        String credUrlEdited = credUrl + " Edited";
        String credUsernameEdited = credUsername + " Edited";
        String credPassEdited = credPassword + " Edited";
        credentialTab.editCredential(credUrl + 1, credUrlEdited,
                credUsernameEdited, credPassEdited);
        Assertions.assertTrue(resultPage.checkSuccessMessage());
        resultPage.clickContinue();
        Assertions.assertTrue(credentialTab.checkIfCredentialExists(credUrlEdited));
        Assertions.assertTrue(credentialTab
                .checkCredentialContent(credUrlEdited,
                        credUsernameEdited, credPassEdited));
    }


}
