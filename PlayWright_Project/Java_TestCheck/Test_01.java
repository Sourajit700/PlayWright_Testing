package TestCase;

import java.util.ArrayList;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import WebPages.Log_in_page;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class Test_01 {
	static Playwright playwright;
	static Page page;
	static Browser browser;
	static Log_in_page loginpage;

	public static void main(String[] args) {

		try (Playwright playwright = Playwright.create()) {
			ArrayList<String> arguments = new ArrayList<String>();
			arguments.add("--start-maximized");
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false).setArgs(arguments));
			BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
		    page = context.newPage();
			page.navigate("http://way2automation.com");
			System.out.println(page.title());

			loginpage.login("", "");

		}
	}

}
