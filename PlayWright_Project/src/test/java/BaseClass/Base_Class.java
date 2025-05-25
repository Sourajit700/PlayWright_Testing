package BaseClass;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Base_Class {
	public Page page;
	public Browser browser;
	public Playwright playwright;
	
	public Base_Class(Page page) {
		this.page = page;
	}
}
