package pages.overstock;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultPage extends BasePage{

	public SearchResultPage(WebDriver driver) {
		super(driver);
	}
	By itemNameLinks = By.xpath("//div[@id='product-container']//div[contains(@class,'product-title')]");
	By itemPrices = By.xpath("//div[@id='product-container']//span[@class='from-price']");
	By nextPageLink = By.xpath("//a[@class='next']");
	
	static final int MAX_PAGE_COUNT=2;
	public ProductDetailsPage findAndOpenItem(String itemName, double price){
		List<WebElement> allItems = driver.findElements(itemNameLinks);
		
		for(int pageNum = 1;allItems.size()>0 && pageNum<=MAX_PAGE_COUNT;pageNum++){
			
			allItems = driver.findElements(itemNameLinks);
			for(int i = 0;i<allItems.size();i++){
				String actualitemName = allItems.get(i).getText();
				if(itemName.equals(actualitemName.trim())){
					WebElement itemPrice = driver.findElements(itemPrices).get(i);
					String actualPriceDollar = itemPrice.findElement(By.xpath("./span[@class='price-dollar']")).getText();
					String actualPriceCents = itemPrice.findElement(By.xpath("./span[@class='price-cent']")).getText();
					String expectedPriceDollar = String.valueOf((int)price);
					String expectedPriceCents = String.valueOf((int)(price*100)%100);
					
					if(actualPriceDollar.equals(expectedPriceDollar) && actualPriceCents.equals(expectedPriceCents)){
						allItems.get(i).click();
						return new ProductDetailsPage(driver);
					}
				}
			}
			if(verifyElement(nextPageLink)){
				clickOn(nextPageLink);
				waitInSeconds(10);
			}else{
				return null;
			}
			allItems = driver.findElements(itemNameLinks);
		}
		
		return null;
	}
	
	

}
