package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	public static WebDriver driver;
	public Logger logger; // log4j
	public Properties prop;

	@SuppressWarnings("deprecation")
	@BeforeClass(groups = { "Sanity", "Regression", "Master" })
	@Parameters({ "os", "browser" })
	public void setUp(String os, String br) throws IOException {
		logger = LogManager.getLogger(this.getClass());

		FileReader f = new FileReader(".//src//test//resources//config.properties");
		prop = new Properties();
		prop.load(f);

		// os
		if (prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else if (os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			} else {
				System.out.println("No matching Operating System...");
				return;
			}

			// browser
			switch (br.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			default:
				System.out.println("No Matching of Browser..");
				return;
			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}

		if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (br.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				System.out.println("Invalid Browser Name..");
				return;
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get(prop.getProperty("appURL")); // read URL from properties file
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master" })
	public void tearDown() {
		driver.quit();
	}

	public String captureScreen(String tname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp for the
																							// screenshot.
		TakesScreenshot ts = (TakesScreenshot) driver;
		File sourcefile = ts.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp;
		File targetfile = new File(targetFilePath);

		sourcefile.renameTo(targetfile);
		return targetFilePath;
	}

	@SuppressWarnings("deprecation")
	public String randomeString() {
		String generartedstring = RandomStringUtils.randomAlphabetic(5);
		return generartedstring;
	}

	@SuppressWarnings("deprecation")
	public String randomeNumber() {
		String generartednumber = RandomStringUtils.randomNumeric(10);
		return generartednumber;
	}

	@SuppressWarnings("deprecation")
	public String randomeAlphaNumeric() {
		String generartedstring = RandomStringUtils.randomAlphabetic(4);
		String generartednumber = RandomStringUtils.randomNumeric(3);
		return (generartedstring + "@" + generartednumber);
	}

}
