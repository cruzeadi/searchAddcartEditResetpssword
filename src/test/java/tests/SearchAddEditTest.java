package tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.overstock.CartPage;
import pages.overstock.ForgotPasswordPage;
import pages.overstock.HomePage;
import pages.overstock.LandingPage;
import pages.overstock.ProductDetailsPage;
import pages.overstock.SearchResultPage;
import util.Configuration;
import util.ExcelUtil;

public class SearchAddEditTest extends BaseTest{
	LandingPage landingPage;
	HomePage homePage;
	SearchResultPage searchResultPage;
	ProductDetailsPage productDetailsPage;
	CartPage cartPage;
	ForgotPasswordPage forgotPasswordPage;
	
	@Test(enabled=false)
	public void cartTest() throws InterruptedException{
		
		//Login
		
		landingPage = new LandingPage(driver);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@class='cb_close']")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		homePage = landingPage.signIn(Configuration.getEmail(), Configuration.getCurrentOverStockPassword());
		String dataFilePath = Configuration.getFilePath();
		String sheetName = Configuration.getSheetName();
		List<Object> searchTerms = ExcelUtil.getAllValuesForHeaderInAColumn(dataFilePath, sheetName, "Search term");
		List<Object> items = ExcelUtil.getAllValuesForHeaderInAColumn(dataFilePath, sheetName, "Item");
		List<Object> prices = ExcelUtil.getAllValuesForHeaderInAColumn(dataFilePath, sheetName, "Price");
		
		for(int i=0;i<searchTerms.size();i++){
			searchResultPage = homePage.search((String)searchTerms.get(i)+Keys.TAB);
			productDetailsPage = searchResultPage.findAndOpenItem((String)items.get(i), ((Double)prices.get(i)).doubleValue());
			Assert.assertNotNull(productDetailsPage);
			cartPage = productDetailsPage.addToCart();
		}
		
		double totalExpectedPrice=0;
		for(Object price: prices){
			totalExpectedPrice = totalExpectedPrice+ ((Double)price).doubleValue();
		}
		
		double totalCartValue = cartPage.getGrandTotal();
		Assert.assertEquals(totalCartValue, totalExpectedPrice);

		cartPage.removeItem((String)items.get(0));
		double updatedCartValue = cartPage.getGrandTotal();
		totalExpectedPrice = totalExpectedPrice - ((Double)prices.get(0)).doubleValue();
		Assert.assertEquals(updatedCartValue, totalExpectedPrice);
		
		landingPage = cartPage.signOut();
	}
	
	@Test(enabled=true)
	public void changePassword() throws InterruptedException{
		
		landingPage = new LandingPage(driver);	
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@class='cb_close']")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		forgotPasswordPage = landingPage.clickOnForgotPasswordLink();
		forgotPasswordPage.resetPasswordForEmail(Configuration.getEmail());
		driver.get("https://mail.google.com");
		
		//Is Account already registered
		if(driver.findElements(By.id("identifierId")).size()>0){
			//Enter user name
			driver.findElement(By.id("identifierId")).sendKeys(Configuration.getEmail());
			driver.findElement(By.xpath("//span[text()='Next']|//input[@value='Next']")).click();
			Thread.sleep(3000);
		}
			
		//Enter password
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Configuration.getEmailPassword());
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Next']|//input[@value='Sign in']")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//span[@class='bog']/b[text()='Overstock.com Password Reset Request']")).click();
		String currentHandle = driver.getWindowHandle();
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//a[text()='Reset password'])[1]")).click();
		
		for(String handle: driver.getWindowHandles()){
			if(!handle.equals(currentHandle)){
				driver.switchTo().window(handle);
				break;
			}
		}
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='reset-password-page page']/input[1]")).sendKeys(Configuration.getNewOverStockPassword());
		driver.findElement(By.xpath("//div[@class='reset-password-page page']/input[2]")).sendKeys(Configuration.getNewOverStockPassword());
		driver.findElement(By.xpath("//button[text()='Submit']")).click();
		
	}
}
