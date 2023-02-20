package OrangeHRMTestNG;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Recordset;

public class OrangeHRMEmployee {

	ExtentHtmlReporter htmlReporter;
	ExtentTest test;
	ExtentReports extent;
	Properties props = new Properties();
	userdefiend user = new userdefiend();
	XSSFWorkbook ExcelWBook = null;
	XSSFSheet ExcelWSheet;
	FileInputStream inputStream;
	XSSFRow Row;
	XSSFCell Cell;
	public WebDriver dr;
	Recordset recordset = null;
	Connection connection;
	String strSelectQuerry;
	Duration time = Duration.ofSeconds(30);
	OrangeHRMTest obj;

@BeforeSuite
	public void launchBrowser() throws IOException, FilloException {

		String PopFilePath ="/Users/abhishekanand/simplify3x-eclipse-workspace/OrangeHRMTestingTestNG/src/test/java/OrangeHRMTestNG/TestData.properties";
		FileReader reader = new FileReader(PopFilePath);
		props.load(reader);
		System.out.println("launching Chromes browser");
		System.setProperty("webdriver.chrome.driver", props.getProperty("driverpath"));
		dr = new ChromeDriver();
		dr.manage().window().maximize();
		dr.manage().deleteAllCookies();
		dr.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		dr.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
		dr.get(props.getProperty("url"));
		try
		{
			obj = new OrangeHRMTest();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test()
	public void verifyHomepageTitle() throws Exception
	{
		try
		{
			dr.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);

			String expectedTitle = "OrangeHRM";
			String actualTitle = dr.getTitle();
			AssertJUnit.assertEquals(actualTitle, expectedTitle);
			System.out.println(actualTitle);
		}
		catch(Exception e)
		{
			System.out.println("Home Page Title Not Right/Visisble");

		}
	}
	@Test
	public void Employee() throws InterruptedException, IOException
	{
		obj.login(dr, "abhi_ashu", "Cisco@9176");//abhi_ashu
		obj.apply_leave(dr);
	}
	@AfterSuite
	public void afterSuite() {
		dr.close(); 
		dr.quit();
	}
}
