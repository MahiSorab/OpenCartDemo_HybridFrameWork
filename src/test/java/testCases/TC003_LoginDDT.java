package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*
 * Data is valid - login success -test pass - logout.
 * Data is valid - login failed -test fail.
 * Data is Invalid - login success -test fail. - logout
 * Data is Invalid - login failed -test pass .
 * */
public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Datadriven") // getting
	// data-provider from different class and package and hence we need to import
	// it.
	public void verify_LoginDDT(String email, String pwd, String expected) {

		logger.info("*****Starting TC003_LoginDDT*****");
		try {
			// Homepage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// Loginpage
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			// MyAccount
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPageisExist();

			if (expected.equalsIgnoreCase("Valid")) {
				if (targetPage == true) {
					macc.clickLogoutlink();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			}
			if (expected.equalsIgnoreCase("Invalid")) {
				if (targetPage == true) {
					macc.clickLogoutlink();
					Assert.assertTrue(false);
				} else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("*****Finished TC003_LoginDDT*****");
	}
}
