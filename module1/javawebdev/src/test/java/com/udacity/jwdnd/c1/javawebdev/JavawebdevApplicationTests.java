package com.udacity.jwdnd.c1.javawebdev;

import com.udacity.jwdnd.c1.javawebdev.view.ChatPage;
import com.udacity.jwdnd.c1.javawebdev.view.LoginPage;
import com.udacity.jwdnd.c1.javawebdev.view.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JavawebdevApplicationTests {

	public static WebDriver driver;

	@LocalServerPort // (Spring) allow injection od server`s Port
	private Integer port;

	public String baseURL;


	@BeforeAll
	public static void loadDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void quitDriver() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = baseURL = "http://localhost:" + port;
	}



	public static void main(String[] args) throws InterruptedException {
//		driver.get("http://localhost:8080/animal");
//		WebElement inputAnimalText = driver.findElement(By.id("animalText"));
//		WebElement inputAdjective = driver.findElement(By.id("adjective"));
//		inputAnimalText.sendKeys("Animal");
//		inputAdjective.sendKeys("Bonito");
//		for (int counter = 0; counter < 5; counter++) {
//			WebElement inputSubmit = driver.findElement(By.xpath("//input[@type='submit']"));
//			inputSubmit.submit();
//		}
//		List<WebElement> results = driver.findElements(By.className("conclusionMessage"));
//		for (WebElement element : results) {
//			System.out.println(element.getText());
//		}
//		Thread.sleep(2000);
//		driver.quit();
	}

	@Test
	public void doLoginWithNonExistentUser() {

		driver.get(baseURL);
		LoginPage loginPage = new LoginPage(driver);

		loginPage.login("user@email.com", "123456");
		Assertions.assertEquals("Invalid email or password", loginPage.getErrorMsg());
	}

	@Test
	public void sendChatMesage() throws InterruptedException {

		driver.get(baseURL + "/signup");

		String firstname = "User";
		String lastname = "Muito Louco";
		String email = "user@email.com";
		String password = "123456";

		String message = "Hello Bugs!";

		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signup(firstname, lastname, email, password);

		Assertions.assertTrue(signUpPage.getSuccessMessage()
				.contains("You successfully signed up! Please continue to the"));

		driver.get(baseURL);

		LoginPage loginPage = new LoginPage(driver);

		loginPage.login("user@email.com", "123456");

		ChatPage chatPage = new ChatPage(driver);
//		Assertions.assertEquals("Chat", chatPage.getTitle());

		chatPage.sendMessage(email, message);
		Thread.sleep(200);
		Assertions.assertTrue(chatPage.getFirstMessage().contains(message));
	}

}
