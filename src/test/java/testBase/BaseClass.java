package testBase;

import java.io.File;
import java.io.FileInputStream;
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
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;
	public Properties pro;
	public Logger logger; // log4j

	@BeforeClass(groups = { "Sanity", "Regression", "Master", "Datadriven" })
	@Parameters({ "os", "browser" })
	public void setup(@Optional("windows")String os,@Optional("chrome") String browserName) throws IOException {
		// Load config
		File src = new File("./src/test/resources/config.properties");
		FileInputStream fis = new FileInputStream(src);
		pro = new Properties();
		pro.load(fis);

		logger = LogManager.getLogger(this.getClass());

		if (pro.getProperty("execution_env").equalsIgnoreCase("remote")) {
			// Remote WebDriver setup
			DesiredCapabilities cap = new DesiredCapabilities();
			if (os.equalsIgnoreCase("windows")) {
				cap.setPlatform(Platform.WIN10);
			} else if (os.equalsIgnoreCase("mac")) {
				cap.setPlatform(Platform.MAC);
			} else if (os.equalsIgnoreCase("linux")) {
				cap.setPlatform(Platform.LINUX);
			} else {
				System.out.println("No matching OS");
				return;
			}
			switch (browserName.toLowerCase()) {
			case "chrome":
				cap.setBrowserName("chrome");
				break;
			case "edge":
				cap.setBrowserName("MicrosoftEdge");
				break;
			case "firefox":
				cap.setBrowserName("firefox");
				break;
			default:
				System.out.println("No matching browser");
				return;
			}
			String hubUrl="http://localhost:4444/wd/hub";
			driver = new RemoteWebDriver(new URL(hubUrl), cap);
		} else {
			// Local WebDriver setup
			switch (browserName.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			default:
				return;
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get(pro.getProperty("appUrl"));
		driver.manage().window().maximize();
	}

	public String randomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	public String randomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}

	public String randomAlphaNumber() {
		// Password with special character
		// String generatedstring=RandomStringUtils.randomAlphabetic(3);
		// String generatednumber=RandomStringUtils.randomNumeric(3);
		// return (generatedstring+"@"+generatednumber);

		// only Alphanumeric password
		return RandomStringUtils.randomAlphanumeric(6);
	}

	public String captureScreen(String tname) throws IOException {
		try {
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			String targetFilePath = System.getProperty("user.dir") + "\\Screenshots\\" + tname + "_" + timeStamp
					+ ".png";
			File targetFile = new File(targetFilePath);
			sourceFile.renameTo(targetFile);
			return targetFilePath;
		} catch (WebDriverException e) {
			System.out.println("Failed to capture screenshot: " + e.getMessage());
			// Optionally, log the error and continue with the test execution
			return "";
		}
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master","Datadriven" })
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
