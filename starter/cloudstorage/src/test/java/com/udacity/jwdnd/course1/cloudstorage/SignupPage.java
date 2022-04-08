package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonSignUp")
    private WebElement buttonSignUp;

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    public void inputFirstName(String firstName) {
        inputFirstName.click();
        inputFirstName.clear();
        inputFirstName.sendKeys(firstName);
    }

    public void inputLastName(String lastName) {
        inputLastName.click();
        inputLastName.clear();
        inputLastName.sendKeys(lastName);
    }

    public void inputUsername(String userName) {
        inputUsername.click();
        inputUsername.clear();
        inputUsername.sendKeys(userName);
    }

    public void inputPassword(String password) {
        inputPassword.click();
        inputPassword.clear();
        inputPassword.sendKeys(password);
    }

    public void clickButtonSignup(){
        buttonSignUp.click();
    }

    public String getSuccessMessage() {
        return successMsg.getText();
    }
}
