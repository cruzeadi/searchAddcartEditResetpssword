package helper;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementActions {
	protected WebDriver driver;
	protected Actions actions;
	protected WebDriverWait wait;
	protected JavascriptExecutor js;
	public ElementActions(WebDriver driver){
		this.driver = driver;
		actions = new Actions(driver);
		wait = new WebDriverWait(driver,20);
		js = (JavascriptExecutor)driver;
	}
	
	public void mouseHover(By locator){
		actions.moveToElement(driver.findElement(locator)).build().perform();
		waitInSeconds(1);
	}
	public void clickOn(By locator){
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		driver.findElement(locator).click();
	}
	
	public void waitInSeconds(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void clearText(By locator){
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		driver.findElement(locator).clear();
	}
	
	public void sendText(By locator, String text){
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		driver.findElement(locator).sendKeys(text+Keys.TAB);
	}
	
	public String getAttribute(By locator, String attributeName){
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElement(locator).getAttribute(attributeName);
	}
	
	public String getText(By locator){
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElement(locator).getText();
	}
	
	public void switchFrame(By frameBy){
		wait.until(ExpectedConditions.visibilityOfElementLocated(frameBy));
		driver.switchTo().frame(driver.findElement(frameBy));
	}
	
	public void switchDefaultFrame(){
		driver.switchTo().defaultContent();
	}
	
	public boolean verifyElement(By locator){
		boolean isFound = true;
		try{
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);
			element.getTagName();
		}catch(Exception e){
			isFound = false;
		}
		return isFound;
	}
	
}
