package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups = { "Sanity", "Master" })
	public void verify_Login() {
		logger.info("*****Starting TC002_LoginTest*****");
		try {
			// Homepage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// Loginpage
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(prop.getProperty("email"));
			lp.setPassword(prop.getProperty("password"));
			lp.clickLogin();

			// MyAccount
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPageisExist();

//		Assert.assertEquals(targetPage, true, "Login failed");
			Assert.assertTrue(targetPage);
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("*****Completed TC002_LoginTest*****");
	}
}
