package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	private CredentialService credentialService;



	@LocalServerPort
	private int port;

	private WebDriver driver;



	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();

	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}



	@Test
	public void unauthorizedLoginTest(){
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("sample");
		driver.findElement(By.id("inputPassword")).sendKeys("pass");
		driver.findElement(By.id("login-button")).click();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signUpLoginLogoutTest(){
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("firstname");
		driver.findElement(By.id("inputLastName")).sendKeys("lastname");
		driver.findElement(By.id("inputUsername")).sendKeys("username");
		driver.findElement(By.id("inputPassword")).sendKeys("password");
		driver.findElement(By.id("buttonSignUp")).click();
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("username");
		driver.findElement(By.id("inputPassword")).sendKeys("password");
		driver.findElement(By.id("login-button")).click();
		Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("logout-btn")).click();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void createChangeDeleteNoteTest() throws InterruptedException {

		//singning up a new user
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("firstname");
		driver.findElement(By.id("inputLastName")).sendKeys("lastname");
		driver.findElement(By.id("inputUsername")).sendKeys("username");
		driver.findElement(By.id("inputPassword")).sendKeys("password");
		driver.findElement(By.id("buttonSignUp")).click();

		//Login to home page
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("username");
		driver.findElement(By.id("inputPassword")).sendKeys("password");
		driver.findElement(By.id("login-button")).click();

		//create a new note and saving it
		Thread.sleep(2000);
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("add-note-btn")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("note-title")).sendKeys("title");
		driver.findElement(By.id("note-description")).sendKeys("description");
		driver.findElement(By.id("save-note")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("home-link")).click();
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);


		//Test if note is created and text fields have given values
		Assertions.assertEquals("title", driver.findElement(By.id("noteTitle")).getText());
		Assertions.assertEquals("description", driver.findElement(By.id("noteDescription")).getText());

		//Editing the note, saving it
		driver.findElement(By.id("edit_note_btn")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("note-title")).sendKeys(" changed");
		driver.findElement(By.id("note-description")).sendKeys(" changed");
		Thread.sleep(2000);
		driver.findElement(By.id("save-note")).click();
		driver.findElement(By.id("home-link")).click();
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);

		//Test if note is edited and text fields have edited values
		Assertions.assertEquals("title changed", driver.findElement(By.id("noteTitle")).getText());
		Assertions.assertEquals("description changed", driver.findElement(By.id("noteDescription")).getText());

		//Deleting the note
		driver.findElement(By.id("deleteNote")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("home-link")).click();

		//Checking if 'userTable' has a note with previously edited fields
		boolean noteNotDeleted = driver.findElement(By.id("userTable")).getText().contains("title changed");

		//Test if the note deleted
		Assertions.assertTrue(!noteNotDeleted);


	}
	@Test
	public void createChangeDeleteCredential() throws InterruptedException {

		//singning up a new user
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("firstname");
		driver.findElement(By.id("inputLastName")).sendKeys("lastname");
		driver.findElement(By.id("inputUsername")).sendKeys("username");
		driver.findElement(By.id("inputPassword")).sendKeys("password");
		driver.findElement(By.id("buttonSignUp")).click();

		//Login to home page
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("username");
		driver.findElement(By.id("inputPassword")).sendKeys("password");
		driver.findElement(By.id("login-button")).click();

		//create a new credential and saving it
		Thread.sleep(2000);
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);

		driver.findElement(By.id("add-credential-btn")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("credential-url")).sendKeys("url");
		driver.findElement(By.id("credential-username")).sendKeys("username");
		driver.findElement(By.id("credential-password")).sendKeys("password");
		Thread.sleep(2000);
		driver.findElement(By.id("save-credentials-btn")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("home-link")).click();
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);

		//Test if credential is created and text fields have previously given values
		Assertions.assertEquals("url", driver.findElement(By.id("credentialUrl")).getText());
		Assertions.assertEquals("username", driver.findElement(By.id("credentialUsername")).getText());
		Assertions.assertEquals("password", driver.findElement(By.id("credentialPassword")).getText());
		Thread.sleep(2000);

		//Editing the credential, saving it
		driver.findElement(By.id("credential-edit-btn")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("credential-url")).sendKeys(" changed");
		driver.findElement(By.id("credential-username")).sendKeys(" changed");
		driver.findElement(By.id("credential-password")).sendKeys(" changed");
		driver.findElement(By.id("save-credentials-btn")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("home-link")).click();
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);

		//Test if credential is edited and text fields have edited values
		Assertions.assertEquals("url changed", driver.findElement(By.id("credentialUrl")).getText());
		Assertions.assertEquals("username changed", driver.findElement(By.id("credentialUsername")).getText());
		Assertions.assertEquals("password changed", driver.findElement(By.id("credentialPassword")).getText());
		Thread.sleep(2000);

		//Extracting encrypted pass from credential obj from ui
		String encrypted_pass = driver.findElement(By.id("credential-delete-btn")).getAttribute("credential-enc-pass");

		//Testing if encrypted password of the same credential is different from not encrypted password
		Assertions.assertNotEquals(driver.findElement(By.id("credentialPassword")).getText(), encrypted_pass);

		//Delete credential
		driver.findElement(By.id("credential-delete-btn")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("home-link")).click();
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);

		//Checking if 'credentialTable' has a credential with previously edited fields
		boolean credentialNotDeleted = driver.findElement(By.id("credentialTable")).getText().contains("url changed");

		//Test if the credential deleted
		Assertions.assertTrue(!credentialNotDeleted);


	}






	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() throws InterruptedException {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
