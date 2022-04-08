package com.udacity.jwdnd.course1.cloudstorage;

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
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static WebDriverWait webDriverWait;

    private HomePage homePage;
    private LoginPage loginPage;
    private SignupPage signupPage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 2);
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/signup");
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        homePage = new HomePage(driver);
    }

    @AfterAll
    public static void afterAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Write a test that verifies that an unauthorized user can only access the login
    // and signup pages.
    @Test
    public void checkAccessOfUnauthorizedUser() {
        //verifies that an unauthorized user can only access the login
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
        // and signup pages
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
        // but not the home page
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
    }

    // Write a test that signs up a new user, logs in, verifies that the home page is accessible,
    // logs out, and verifies that the home page is no longer accessible.
    @Test
    public void logoutUserCannotAccessHomepage() {
        // Signs up a new user
        doMockSignUp("logoutUser", "logoutUser", "logoutUser", "logoutUser");
        // Logs in
        doLogIn("logoutUser", "logoutUser");
        //verifies that the home page is accessible
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
        // Logs out
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        homePage.logout();
        // verifies that the home page is no longer accessible
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
    }

    void doMockNote(String title, String description) {
        // select note tab.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        homePage.getNoteTab();

        // Click on add note button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));
        homePage.clickAddNoteButton();

        // fill out note
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        homePage.inputNoteTitle(title);
        homePage.inputNoteDescription(description);

        // Attempt to save saveNoteButton
        homePage.clickSaveNoteButton();

        // Check if saving the note was successful
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
    }

    // Write a test that creates a note, and verifies it is displayed.
    @Test
    void addAndVerifyNote() {
        // Create a test account and login
        doMockSignUp("addNote", "addNote", "addNote", "addNote");
        doLogIn("addNote", "addNote");

        // Create a note
        doMockNote("Title", "Description");

        // Go back to home page and select note tab
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        homePage.getNoteTab();

        // Click on edit note button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNoteButton")));
        homePage.clickEditNoteButton();

        // Verify the data
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        Assertions.assertEquals("Title", driver.findElement(By.id("note-title")).getAttribute("value"));
        Assertions.assertEquals("Description", driver.findElement(By.id("note-description")).getAttribute("value"));

        // Logout user
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        homePage.logout();
    }

    // Write a test that edits an existing note and verifies that the changes are displayed.
    @Test
    public void editNoteAndVerifyChanges() throws InterruptedException {
        // Create a test account
        doMockSignUp("changeNote", "changeNote", "changeNote", "changeNote");
        doLogIn("changeNote", "changeNote");

        // Create a note
        doMockNote("Title", "Description");

        // Go back to home page and select note tab
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        homePage.getNoteTab();

        // Click on edit note button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNoteButton")));
        homePage.clickEditNoteButton();

        // Change title and description
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        homePage.inputNoteTitle("New Title");
        homePage.inputNoteDescription("New Description");

        // Attempt to save saveNoteButton
        homePage.clickSaveNoteButton();

        // Check if saving the note was successful
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));

        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        homePage.getNoteTab();

        // Click on edit note button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNoteButton")));
        homePage.clickEditNoteButton();

        // Verify the data
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        Assertions.assertEquals("New Title", driver.findElement(By.id("note-title")).getAttribute("value"));
        Assertions.assertEquals("New Description", driver.findElement(By.id("note-description")).getAttribute("value"));

        // Logout user
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        homePage.logout();
    }

    // Write a test that deletes a note and verifies that the note is no longer displayed.
    @Test
    public void deleteNoteAndVerifyNoLongerDisplayed() {
        // Create a test account
        doMockSignUp("deleteNote", "deleteNote", "deleteNote", "deleteNote");
        doLogIn("deleteNote", "deleteNote");

        // Create a note
        doMockNote("Title", "Description");

        // Go back to home page and select note tab
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        homePage.getNoteTab();

        // Click on delete note button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNoteButton")));
        homePage.clickDeleteNoteButton();

        // Check if saving the note was successful
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));

        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        homePage.getNoteTab();

        // tbody of the table is empty
        Assertions.assertEquals(0, homePage.getNoteTableSize());

        // Logout user
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        homePage.logout();
    }

    public void doMockListOfCredentials(String[] inputCredential) {

        homePage.inputCredentialUrl(inputCredential[0]);
        homePage.inputCredentialUsername(inputCredential[1]);
        homePage.inputCredentialPassword(inputCredential[2]);

        // Attempt to save saveCredentialButton
        homePage.clickSaveCredentialButton();

        // Check if saving the credential was successful
        Assertions.assertTrue(driver.findElement(By.id("success")).getText().contains("Success"));
    }

    // Write a test that creates a set of credentials, verifies that they are displayed,
    // and verifies that the displayed password is encrypted.
    @Test
    public void addAndVerifyCredentialList() {
        // Create a test account
        doMockSignUp("addCred", "addCred", "addCred", "addCred");
        doLogIn("addCred", "addCred");

        // Create a list of credentials inputdata
        List<String[]> inputCredentials = List.of(
                new String[]{"Url1", "User1", "Password1"},
                new String[]{"Url2", "User2", "Password2"},
                new String[]{"Url3", "User3", "Password3"}
        );

        for (String[] inputCredential : inputCredentials) {
            // select credential tab.
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
            homePage.getCredentialTab();

            // Click on add credential button
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));
            homePage.clickAddCredentialButton();

            // fill out credential
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));

            doMockListOfCredentials(inputCredential);

            // Go back to home page
            driver.get("http://localhost:" + this.port + "/home");
        }

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        homePage.getCredentialTab();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        int countOfRows = homePage.getCredentialTableSize();
        for (int i = 0; i < countOfRows; i++) {
            // Check url
            Assertions.assertEquals(inputCredentials.get(i)[0], homePage.getCredentialTableUrl(i));
            // Check username
            Assertions.assertEquals(inputCredentials.get(i)[1], homePage.getCredentialTableUsername(i));
            // Check password
            Assertions.assertNotEquals(inputCredentials.get(i)[2], homePage.getCredentialTablePassword(i));
        }

        // Logout user
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        homePage.logout();
    }

    // Write a test that views an existing set of credentials,
    // verifies that the viewable password is unencrypted,
    // edits the credentials, and verifies that the changes are displayed.
    @Test
    public void editCredentialAndVerifyChanges() {
        // Create a test account
        doMockSignUp("editCred", "editCred", "editCred", "editCred");
        doLogIn("editCred", "editCred");

        // Create a list of credentials inputdata
        List<String[]> inputCredentials = List.of(
                new String[]{"Url1", "User1", "Password1"},
                new String[]{"Url2", "User2", "Password2"},
                new String[]{"Url3", "User3", "Password3"}
        );
        for (String[] inputCredential : inputCredentials) {
            // select credential tab.
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
            homePage.getCredentialTab();

            // Click on add credential button
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));
            homePage.clickAddCredentialButton();

            // fill out credential
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
            doMockListOfCredentials(inputCredential);

            // Go back to home page
            driver.get("http://localhost:" + this.port + "/home");
        }

        // Create a list of credentials inputdata
        List<String[]> inputNewCredentials = List.of(
                new String[]{"new Url1", "new User1", "new Password1"},
                new String[]{"new Url2", "new User2", "new Password2"},
                new String[]{"new Url3", "new User3", "new Password3"}
        );

        int rowCount = 0;
        for (String[] inputCredential : inputNewCredentials) {

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
            homePage.getCredentialTab();

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editCredentialButton")));
            homePage.clickEditCredentialButton(rowCount);

            // verifies that the viewable password is unencrypted
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
            Assertions.assertEquals(homePage.getCredentialPassword(), inputCredentials.get(rowCount)[2]);

            // fill out credential
            doMockListOfCredentials(inputCredential);

            // Go back to home page
            driver.get("http://localhost:" + this.port + "/home");

            rowCount++;
        }

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        homePage.getCredentialTab();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        int countOfRows = homePage.getCredentialTableSize();

        for (int i = 0; i < countOfRows; i++) {
            // Check url
            Assertions.assertEquals(inputNewCredentials.get(i)[0], homePage.getCredentialTableUrl(i));
            // Check username
            Assertions.assertEquals(inputNewCredentials.get(i)[1], homePage.getCredentialTableUsername(i));
            // Check password
            Assertions.assertNotEquals(inputNewCredentials.get(i)[2], homePage.getCredentialTablePassword(i));
        }

        // Logout user
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());

        // Attempt to logout.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        homePage.logout();
    }

    // Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
    @Test
    public void deleteCredentialAndVerifyNoLongerDisplayed() {
        // Create a test account
        doMockSignUp("deleteCred", "deleteCred", "deleteCred", "deleteCred");
        doLogIn("deleteCred", "deleteCred");

        // Create a list of credentials inputdata
        List<String[]> inputCredentials = List.of(
                new String[]{"Url1", "User1", "Password1"},
                new String[]{"Url2", "User2", "Password2"},
                new String[]{"Url3", "User3", "Password3"}
        );

        for (String[] inputCredential : inputCredentials) {
            // select credential tab.
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
            homePage.getCredentialTab();

            // Click on add credential button
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));
            homePage.clickAddCredentialButton();

            // fill out credential
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
            doMockListOfCredentials(inputCredential);

            // Go back to home page
            driver.get("http://localhost:" + this.port + "/home");
        }

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        homePage.getCredentialTab();

        for (String[] inputCredential : inputCredentials) {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCredentialButton")));
            homePage.clickDeleteCredentialButton();

            // Go back to home page
            driver.get("http://localhost:" + this.port + "/home");

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
            homePage.getCredentialTab();

            // Check if credential is delete
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
            int countOfRows = homePage.getCredentialTableSize();

            for (int i = 0; i < countOfRows; i++) {
                // Check url
                Assertions.assertNotEquals(inputCredential[0], homePage.getCredentialTableUrl(i));

                // Check username
                Assertions.assertNotEquals(inputCredential[1], homePage.getCredentialTableUsername(i));
            }
        }

        // tbody of the table is empty
        Assertions.assertEquals(0, homePage.getCredentialTableSize());

        // Attempt to logout.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        homePage.logout();
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        signupPage.inputFirstName(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        signupPage.inputLastName(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        signupPage.inputUsername(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        signupPage.inputPassword(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        signupPage.clickButtonSignup();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
        Assertions.assertTrue(signupPage.getSuccessMessage().contains("You successfully signed up!"));
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        loginPage.inputUsername(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        loginPage.inputPassword(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        loginPage.clickLoginButton();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
        doLogIn("UT", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));

        driver.get("http://localhost:" + this.port + "/home");
        // Attempt to logout.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        homePage.logout();
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning FileMapper Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large FileMapper", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        homePage.fileSelectButton(new File(fileName).getAbsolutePath());

        homePage.clickUploadButton();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large FileMapper upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

        // Attempt to logout.
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        homePage.logout();
    }
}
