package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[@role='button']//span[@class='title'][normalize-space()='My account']")
	WebElement lnkMyaccount;

	@FindBy(xpath = "//a[contains(text(),'Register')]")
	WebElement lnkRegister;
	
	@FindBy(linkText="Login")
	WebElement lnkLogin;

	public void clickMyAccount() {
		lnkMyaccount.click();
	}

	public void clickRegister() {
		lnkRegister.click();
	}
	
	public void clickLogin()
	{
		lnkLogin.click();
	}
}
