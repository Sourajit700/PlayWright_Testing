package Step_Definations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;

import BaseClass.BaseClass;
import Pages.Log_inPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LogIn_StepDefination {
//	public Page page;
	BaseClass BaseClass ;
	Map<String, String> testData;
	@Given("I login to the Application {string}")
	public void i_login_to_the_application(String url) throws IOException {
		url = BaseClass.loadConfig("URL");
		String browser = BaseClass.loadConfig("browserName");
		System.out.println("Launching test on: " + browser + " at " + url);
		BaseClass.getBrowser(browser, url);
	}

	@When("I Execute {string} of {string} of {string} from Data Sheet")
	public void i_execute_of_of_from_data_sheet(String testCaseId, String folderName, String fileName)
			throws IOException {

		testData = BaseClass.dataTable("Data", "TestCase01.xlsx", "Test001");
		System.out.println("testData-->"+testData);
	}

	@Then("I goto fill the UserName and PassWord")
	public void i_goto_fill_the_user_name_and_pass_word() {
		Log_inPage loginPage = new Log_inPage(BaseClass.page);
        loginPage.login(testData.get("username"), testData.get("password"));
        BaseClass.page.waitForTimeout(5000);

	}
}
