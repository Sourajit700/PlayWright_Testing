package BaseClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.WaitForSelectorOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.SelectOption;

import extentlisteners.ExtentListeners;
import utilities.ExcelReader;

public class BaseClass {
	private static Playwright playwright;
	private static Browser browser;
	public static Page page;
	private static Properties OR = new Properties();
	private static FileInputStream fis;
	private Logger log = Logger.getLogger(this.getClass());
	private static Properties prop;
	private static ThreadLocal<Playwright> pw = new ThreadLocal<>();
	private static ThreadLocal<Browser> br = new ThreadLocal<>();
	private static ThreadLocal<Page> pg = new ThreadLocal<>();
	private static BrowserContext context;

	public BaseClass(Page page) {
		this.page = page;
	}

	public static Playwright getPlaywright() {

		return pw.get();
	}

	public static Browser getBrowser() {

		return br.get();
	}

	public static Page getPage() {

		return pg.get();
	}

	@BeforeSuite
	public void setUp() {

		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
		log.info("Test Execution started !!!");

		try {
			fis = new FileInputStream("./src/test/resources/properties/OR.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			OR.load(fis);
			log.info("OR Properties file loaded.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@BeforeClass
	public static String loadConfig(String key) throws IOException {
		try {
			FileInputStream fis = new FileInputStream(
					".\\src\\test\\resources\\resources\\properties\\config.properties");
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}

	public void click(String locatorKey) {

		try {
			getPage().locator(OR.getProperty(locatorKey)).click();
			log.info("Clicking on an Element : " + locatorKey);
			ExtentListeners.getExtent().info("Clicking on an Element : " + locatorKey);
		} catch (Throwable t) {

			log.error("Error while clicking on an Element : " + t.getMessage());
			ExtentListeners.getExtent()
					.fail("Clicking on an Element : " + locatorKey + " error message is :" + t.getMessage());
			Assert.fail(t.getMessage());
		}
	}

	public boolean isElementPresent(String locatorKey) {

		try {
			getPage().waitForSelector(OR.getProperty(locatorKey), new WaitForSelectorOptions().setTimeout(2000));

			log.info("Finding an Element : " + locatorKey);
			ExtentListeners.getExtent().info("Finding an Element : " + locatorKey);
			return true;
		} catch (Throwable t) {

			ExtentListeners.getExtent().fail("Error while finding an Element : " + locatorKey);

			return false;
		}

	}

	public void type(String locatorKey, String value) {
		try {
			getPage().locator(OR.getProperty(locatorKey)).fill(value);
			log.info("Typing in an Element : " + locatorKey + " and entered the value as :" + value);
			ExtentListeners.getExtent()
					.info("Typing in an Element : " + locatorKey + " and entered the value as :" + value);
		} catch (Throwable t) {

			log.error("Error while typing in an Element : " + t.getMessage());
			ExtentListeners.getExtent().fail(
					"Error while typing in an Element : " + t.getMessage() + " error message is :" + t.getMessage());
			Assert.fail(t.getMessage());
		}
	}

	public void select(String locatorKey, String value) {
		try {
			getPage().selectOption(OR.getProperty(locatorKey), new SelectOption().setLabel(value));
			log.info("Selecting an Element : " + locatorKey + " and selected the value as :" + value);
			ExtentListeners.getExtent()
					.info("Selecting an Element : " + locatorKey + " and selected the value as :" + value);
		} catch (Throwable t) {

			log.error("Error while Selecting an Element : " + t.getMessage());
			ExtentListeners.getExtent().fail(
					"Error while Selecting an Element : " + t.getMessage() + " error message is :" + t.getMessage());
			Assert.fail(t.getMessage());
		}
	}

	public static void getBrowser(String browserName, String url) {
		playwright = Playwright.create();

		// Launch the browser based on the provided browser name
		switch (browserName.toLowerCase()) {
		case "chrome":
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
			break;
		case "edge":
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false));
			break;
		case "firefox":
			browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;
		default:
			throw new RuntimeException("Unsupported browser: " + browserName);
		}

		// Create a new context and page for the browser
		context = browser.newContext();
		page = context.newPage();

		// Navigate to the specified URL
		page.navigate(url);

	}

	public static Map<String, String> dataTable(String folderName, String fileName, String testCaseId) {
		try {
			String path = System.getProperty("user.dir") + "/src/test/resources/resources/" + folderName + "/" + fileName;
			Map<String, String> rowData = ExcelReader.getTestData(path, testCaseId);

			if (rowData == null || rowData.isEmpty()) {
				System.out.println(" No data found for test case ID: " + testCaseId);
			} else {
				System.out.println(" Data loaded for " + testCaseId + ": " + rowData);
			}

			return rowData;

		} catch (Exception e) {
			throw new RuntimeException("Failed to load Excel data: " + e.getMessage(), e);
		}

	}

	public void navigate(Browser browser, String url) {
		this.browser = browser;
		br.set(browser);
		page = getBrowser().newPage();
		pg.set(page);
		getPage().navigate(url);
		log.info("Navigated to : " + url);

		getPage().onDialog(dialog -> {

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dialog.accept();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(dialog.message());
		});

	}

	@AfterMethod
	public void quit() {

//		if (getPage() != null) {
//			getBrowser().close();
//			getPage().close();
//			// getPlaywright().close();
//		}
	}

	@AfterSuite
	public void quitPlaywright() {
		if (getPage() != null) {
			getPlaywright().close();
		}
	}
}
