package pages.overstock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage{

	By grandTotalValue = By.xpath("//div[@class='cr-totals-item total-type-GRAND_TOTAL']//span[@class='cr-total-value']");
	String genericRemoveBtn_xpath = "//div[contains(@class,'cr-item-details-wrapper') and .//a[text()='<ITEM_NAME>']]//a[contains(text(),'Remove')]";
	public CartPage(WebDriver driver) {
		super(driver);
	}

	public double getGrandTotal(){
		String grandTotalPrice = getText(grandTotalValue).substring(1);
		return Double.parseDouble(grandTotalPrice);
	}
	
	public void removeItem(String itemName){
		String removeBtn_xpath = genericRemoveBtn_xpath.replace("<ITEM_NAME>", itemName);
		clickOn(By.xpath(removeBtn_xpath));
		waitInSeconds(2);
	}
}
