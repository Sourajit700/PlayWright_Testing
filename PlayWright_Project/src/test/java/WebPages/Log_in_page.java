package WebPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import BaseClass.Base_Class;

public class Log_in_page extends Base_Class{
	
	public Log_in_page(Page page) {
		super(page);
	}
	Locator userNameField = page.locator("");
	Locator passwordField = page.locator("");
	public void login(String userName, String password) {
		userNameField.fill(userName);
		passwordField.fill(password);
	}
}
