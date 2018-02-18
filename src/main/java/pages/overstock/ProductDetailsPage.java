package pages.overstock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage{

	public ProductDetailsPage(WebDriver driver) {
		super(driver);
	}

	By addToCartBtn = By.id("addCartMain_addCartButton");
	
	public CartPage addToCart(){
		clickOn(addToCartBtn);
		return new CartPage(driver);
	}
	
}
