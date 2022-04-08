package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    // File Upload Tab
    @FindBy(id = "fileUpload")
    private WebElement fileSelectButton;

    @FindBy(id = "uploadButton")
    private WebElement uploadButton;

    // Note Tab
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "saveNoteButton")
    private WebElement saveNoteButton;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(tagName = "tbody/tr/th")
    private List<WebElement> noteTable;

    // Credential Tab
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id = "saveCredentialButton")
    private WebElement saveCredentialButton;

    @FindBy(id = "editCredentialButton")
    private List<WebElement> editCredentialButton;

    @FindBy(id = "deleteCredentialButton")
    private WebElement deleteCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(xpath = "//*[@id='credentialTable']/tbody/tr")
    private List<WebElement> credentialTableRows;

    // Home Page
    public void logout() {
        logoutButton.click();
    }

    // File Upload Tab
    public void fileSelectButton(String file) {
        fileSelectButton.sendKeys(file);
    }

    public void clickUploadButton() {
        uploadButton.click();
    }

    // Note Tab
    public void getNoteTab() {
        noteTab.click();
    }

    public void clickAddNoteButton() {
        addNoteButton.click();
    }

    public void clickSaveNoteButton() {
        saveNoteButton.click();
    }

    public void clickEditNoteButton() {
        editNoteButton.click();
    }

    public void clickDeleteNoteButton() {
        deleteNoteButton.click();
    }

    public void inputNoteTitle(String title) {
        noteTitle.click();
        noteTitle.clear();
        noteTitle.sendKeys(title);
    }

    public void inputNoteDescription(String description) {
        noteDescription.click();
        noteDescription.clear();
        noteDescription.sendKeys(description);
    }

    public int getNoteTableSize() {
        return noteTable.size();
    }

    // Credential Tab
    public void getCredentialTab() {
        credentialTab.click();
    }

    public void clickAddCredentialButton() {
        addCredentialButton.click();
    }

    public void clickSaveCredentialButton() {
        saveCredentialButton.click();
    }

    public void inputCredentialUrl(String url) {
        credentialUrl.click();
        credentialUrl.clear();
        credentialUrl.sendKeys(url);
    }

    public void inputCredentialUsername(String username) {
        credentialUsername.click();
        credentialUsername.clear();
        credentialUsername.sendKeys(username);
    }

    public void inputCredentialPassword(String password) {
        credentialPassword.click();
        credentialPassword.clear();
        credentialPassword.sendKeys(password);
    }


    private WebElement getCredentialTableRow(int i) {
        return credentialTableRows.get(i);
    }

    public int getCredentialTableSize() {
        return credentialTableRows.size();
    }

    public String  getCredentialTableUrl(int i) {
        return getCredentialTableRow(i).findElement(By.tagName(("th"))).getText();
    }

    public String getCredentialTableUsername(int i) {
        return getCredentialTableRow(i).findElements(By.tagName(("td"))).get(1).getText();
    }

    public String getCredentialTablePassword(int i) {
        return getCredentialTableRow(i).findElements(By.tagName(("td"))).get(2).getText();
    }

    public void clickEditCredentialButton(int i) {
        editCredentialButton.get(i).click();
    }

    public String getCredentialPassword() {
        return credentialPassword.getAttribute("value");
    }

    public void clickDeleteCredentialButton() {
        deleteCredentialButton.click();
    }
}
