package pages.overstock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import helper.ElementActions;

public class ForgotPasswordPage extends ElementActions{

	public ForgotPasswordPage(WebDriver driver) {
		super(driver);
	}
	By emailTxtBox = By.id("loginEmailInput");
	By resetPasswordBtn = By.id("resetButton");
	
	public void resetPasswordForEmail(String emailId){
		sendText(emailTxtBox, emailId);
		clickOn(resetPasswordBtn);
	}
	

}
