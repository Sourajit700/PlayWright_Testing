package Pages;

import com.microsoft.playwright.Page;

public class Log_inPage {
    private Page page;

    // Constructor
    public Log_inPage(Page page) {
        this.page = page;
    }

    // Action Method
    public void login(String username, String password) {
        page.fill("input[name='username']", username);
        page.fill("input[name='password']", password);
        page.click("input[value='Log In']");
    }

    // You can add other verifications here too
}
