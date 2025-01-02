package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	
	@Test(priority = 1, dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = {"Datadriven","Master"})
	public void verify_loginDDT(String username, String password, String res) {

		logger.info("Starting_TC_003_LoginDDT");
		try {
			// HomePage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// loginPage
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(username);
			lp.setPassword(password);
			lp.clickLogin();

			// MyAccount
			MyAccountPage map = new MyAccountPage(driver);
			boolean targetPage = map.MyAccountExists();

			// Valid login scenario
			if (res.equalsIgnoreCase("Valid")) {
				if (targetPage) {
					map.clickLogout();
					Assert.assertTrue(true, "Login was successful, and logout succeeded.");
				} else {
					Assert.fail("Login failed even with valid credentials.");
				}
			}

			// Invalid login scenario
			if (res.equalsIgnoreCase("Invalid")) {
				if (targetPage) {
					map.clickLogout();
					Assert.fail("Login succeeded with invalid credentials.");
				} else {
					Assert.assertTrue(true, "Login correctly failed with invalid credentials.");
				}
			}
		} catch (Exception e) {
			logger.error("Test failed due to exception: " + e.getMessage());
			Assert.fail("Test failed due to unexpected exception.");
		}
		logger.info("Finished_TC_003_LoginDDT");
	}
}
