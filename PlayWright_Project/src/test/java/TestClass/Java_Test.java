package TestClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.microsoft.playwright.Page;

import BaseClass.BaseClass;

public class Java_Test {
	private static Properties prop;
	private static FileInputStream fis;
	public static Page page;
	static BaseClass BaseClass = new BaseClass(page);
	public static void main(String[] args) throws IOException {
		Map<String, String> testData = BaseClass.dataTable("Data", "TestCase01.xlsx", "Test001");
		System.out.println(testData);
	}
	

}
