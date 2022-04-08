package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id="login-button")
    private WebElement loginButton;

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

    public void clickLoginButton() {
        loginButton.click();
    }
}
