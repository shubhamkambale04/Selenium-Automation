package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtFirstname;

	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtLastname;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;

	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txtTelephoneno;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPassword;

	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txtPasswordConfirm;

	@FindBy(xpath = "//label[normalize-space()='No']")
	WebElement chkdSubscribe;

	@FindBy(xpath = "//label[@for='input-agree']")
	WebElement chkdPolicy;

	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;

	public void setFirstName(String fname) {
		txtFirstname.sendKeys(fname);
	}

	public void setLastName(String lname) {
		txtLastname.sendKeys(lname);
	}

	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void setTelephoneNo(String tele) {
		txtTelephoneno.sendKeys(tele);
	}

	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}

	public void setPasswordConfirm(String pwdc) {
		txtPasswordConfirm.sendKeys(pwdc);
	}

	public void setSubscribe() {
		chkdSubscribe.click();
	}

	public void setPolicy() {
		chkdPolicy.click();
	}

	public void clickContinue() {
		btnContinue.click();
	}

	public String getConfirmMsg() {
		try {
			return (msgConfirmation.getText());

		} catch (Exception e) {
			return (e.getMessage());
		}
	}
}
