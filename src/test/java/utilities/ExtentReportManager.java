package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";

		sparkReporter = new ExtentSparkReporter(".\\Reports\\" + repName);
		sparkReporter.config().setDocumentTitle("SeleniumAutomationProject");
		sparkReporter.config().setReportName("Ecommerce Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Shubham");

		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);

		List<String> incudedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!incudedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", incudedGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		// test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName() + " Test Passed");
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		// test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName() + " Test Failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			if (!imgPath.isEmpty()) {
				test.addScreenCaptureFromPath(imgPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		// test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " Test Skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();

		String pathofExtentReport = System.getProperty("user.dir") + "\\Reports\\" + repName;
		File extentReport = new File(pathofExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
			Thread.sleep(5000); // Allow some time for the report to open before attempting to close
			//closeBrowser();
			// mailSent();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void mailSent() {
		try {
			File reportFile = new File(System.getProperty("user.dir") + "\\Reports\\" + repName);
			URL url = reportFile.toURI().toURL();

			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("email@example.com", "password"));
			email.setSSLOnConnect(true);
			email.setFrom("sender@example.com").setSubject(repName).setMsg(repName); // Sender
			email.addTo("receiver@example.com"); // Receiver
			email.attach(reportFile).send();
		} catch (IOException | EmailException e) {
			e.printStackTrace();
		}
	}

	public void closeBrowser() {
		String os = System.getProperty("os.name").toLowerCase();
		try {
			if (os.contains("win")) {
				Runtime.getRuntime().exec("taskkill /F /IM chrome.exe"); // Kills Chrome browser
				Runtime.getRuntime().exec("taskkill /F /IM msedge.exe"); // Kills Edge browser
				Runtime.getRuntime().exec("taskkill /F /IM firefox.exe"); // Kills Firefox browser
			} else if (os.contains("mac")) {
				Runtime.getRuntime().exec("pkill -f 'Google Chrome'");
				Runtime.getRuntime().exec("pkill -f 'Safari'");
				Runtime.getRuntime().exec("pkill -f 'Firefox'");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}