package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups = { "Regression", "Master" })
	public void verify_account_registration() {
		logger.info("*********Started TC001_AccountRegistrationTest execution************");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("clicked on MyAccount link");
			hp.clickRegister();
			logger.info("clicked on Register link");

			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			logger.info("Providing customer details ");
			regpage.setFirstName(randomeString().toUpperCase());
			regpage.setLastname(randomeString().toUpperCase());
			regpage.setEmail(randomeString() + "@gmail.com");
			regpage.setTelephone(randomeNumber());

			String password = randomeAlphaNumeric();

			regpage.setPassword(password);
			regpage.setConfirmPassword(password);

			regpage.clickPrivacypolicy();
			logger.info("Accept the privacy policy");
			regpage.clickContinue();
			logger.info("clicked on continue button");

			logger.info("validating expected message");
			String confmsg = regpage.getConfirmationMsg();
			if (confmsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.error("Test failed");
				logger.debug("Debug logs..");
				Assert.assertTrue(false);
			}
//			Assert.assertEquals(confmsg, "Your Account Has Been Created!!!");
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("******Finished TC001_AccountRegistrationTest execution********");
	}

}
