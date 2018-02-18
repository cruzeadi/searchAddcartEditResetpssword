package pages.overstock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import helper.ElementActions;

public class BasePage extends ElementActions{
	public BasePage(WebDriver driver){
		super(driver);
	}
	By accountMenu = By.linkText("Account");
	By signInOut = By.id("sign-in-out");
	By loginFrame = By.xpath("//iframe[contains(@src,'spage')]");
	By emailTxtBox = By.id("loginEmailInput");
	By passwordTxtBox = By.id("loginPasswordInput");
	By signInBtn = By.id("sign-in-button");
	By forgotPasswordLink = By.linkText("Forgot your password?");
	
	By searchTxtBox = By.id("search-input");
	By searchBtn = By.xpath("//label[@for='search-button']");
	
	public HomePage signIn(String username, String password){
		clickOn(accountMenu);
		waitInSeconds(3);
		switchFrame(loginFrame);
		sendText(emailTxtBox, username);
		sendText(passwordTxtBox, password);
		clickOn(signInBtn);
		switchDefaultFrame();
		waitInSeconds(2);
		return new HomePage(driver);
	}
	
	public SearchResultPage search(String searchTxt){
		sendText(searchTxtBox, searchTxt);
		clickOn(searchBtn);
		return new SearchResultPage(driver);
	}
	
	public LandingPage signOut(){
		mouseHover(accountMenu);
		clickOn(signInOut);
		return new LandingPage(driver);
	}
	
	public ForgotPasswordPage clickOnForgotPasswordLink(){
		clickOn(accountMenu);
		waitInSeconds(3);
		switchFrame(loginFrame);
		clickOn(forgotPasswordLink);
		switchDefaultFrame();
		waitInSeconds(2);
		return new ForgotPasswordPage(driver);
	}
}
