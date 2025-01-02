package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups = { "Regression", "Master" })
	public void verify_account_registration() {
		logger.info("Started TC001_AccountRegistration_Test");

		try {
			HomePage hp = new HomePage(driver);
			// step 2
			hp.clickMyAccount();
			logger.info("Clicked on MyAccount link");
			// step 3
			hp.clickRegister();
			logger.info("Clicked on Register link");

			// step 4
			AccountRegistrationPage arp = new AccountRegistrationPage(driver);

			logger.info("Providing customer details..");
			// arp.setFirstName("John");
			arp.setFirstName(randomString().toUpperCase()); // Randomly generate firstname

			// arp.setLastName("Dixit");
			arp.setLastName(randomString().toUpperCase()); // Randomly generate lastname

			// arp.setEmail("john@gmail.com");
			arp.setEmail(randomString() + "@gmail.com"); // Randomly generated the email

			// arp.setTelephoneNo("26879866");
			arp.setTelephoneNo(randomNumber());

			// arp.setPassword("password");
			// arp.setPasswordConfirm("password");

			// Randomly generate password
			String password = randomAlphaNumber();
			arp.setPassword(password);
			arp.setPasswordConfirm(password);

			// step 5
			arp.setSubscribe();

			// step 6
			arp.setPolicy();

			// step 7
			arp.clickContinue();

			// step 8
			logger.info("Validating expected msg..");
			String confmsg = arp.getConfirmMsg();
			if (confmsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.error("Test failed..");
				logger.debug("Debug logs..");
				Assert.assertTrue(false);
			}
			// Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("Finished TC001_AccountRegistration_Test");
	}

}
