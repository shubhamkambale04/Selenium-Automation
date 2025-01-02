package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[normalize-space()='My Account']")
	WebElement headermsg;
	
	@FindBy(xpath="//a[contains(text(),'Logout')]") // added for data-driven test
	WebElement btnLogout;

	public boolean MyAccountExists() {
		try {
			return headermsg.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	public void clickLogout() {
		btnLogout.click();
	}
}
