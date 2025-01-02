package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups={"Sanity", "Master"})
	public void verify_login() {
		logger.info("Started TC002_Login_Test");

		try {
			// HomePage
			HomePage hp = new HomePage(driver);
			// step 2
			hp.clickMyAccount();
			logger.info("Clicked on MyAccount link");
			// step 3
			hp.clickLogin();
			logger.info("Clicked on Login link");

			// loginPage
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(pro.getProperty("email"));
			lp.setPassword(pro.getProperty("password"));
			lp.clickLogin();

			// MyAccount
			MyAccountPage map = new MyAccountPage(driver);
			boolean targetPage = map.MyAccountExists();

			Assert.assertTrue(targetPage);
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("Finished TC002_Login_Test");
	}
}
