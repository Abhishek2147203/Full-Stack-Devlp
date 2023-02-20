package OrangeHRMTestNG;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellType;
//import org.apache.commons.math3.util.MultidimensionalCounter.Iterator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Recordset;

public class OrangeHRMAdmin  {

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
	 // Creating the Object of This Class
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
	
	
	
	
/////PHILo jar
	@DataProvider(name="excel-data")
	public Object[][] exceldata() throws IOException{
		FileInputStream file=new FileInputStream("/Users/abhishekanand/simplify3x-eclipse-workspace/OrangeHRMTestingTestNG/src/test/java/OrangeHRMTestNG/LoginCredential.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sh = wb.getSheet("Sheet1");
		Map<String,String> map=new HashMap<String, String>();
		for(int i=1;i<sh.getLastRowNum()+1;i++)
		{
			if(sh.getRow(i).getCell(0).getCellType()==CellType.STRING && sh.getRow(i).getCell(1).getCellType()==CellType.STRING )
			{
				String Username= sh.getRow(i).getCell(0).getStringCellValue();
				String value=sh.getRow(i).getCell(1).getStringCellValue();
				map.put(Username, value);
			}
			if(sh.getRow(i).getCell(0).getCellType()==CellType.NUMERIC && sh.getRow(i).getCell(1).getCellType()==CellType.NUMERIC)
			{
				String Username= String.valueOf(sh.getRow(i).getCell(0).getNumericCellValue());
				String password = String.valueOf(sh.getRow(i).getCell(1).getNumericCellValue());
				map.put(Username, password);
			}
			
		}
	/*
	sets can store values using entryset we will store it as key=value
	then using iterator we will iterate into this values by maping it into the set
	we need to iterate untill the it has next element that is till the end

	 */
		Object[][] arr = new Object[map.size()][2];
		Set entries = map.entrySet();
		Iterator entriesIterator = (Iterator) entries.iterator();

		int i = 0;
		while(entriesIterator.hasNext()){
			Map.Entry mapping = (Map.Entry) entriesIterator.next();
			arr[i][0] = mapping.getKey();
			arr[i][1] = mapping.getValue();
			i++;
		}
		return arr;
	}


	@Test()
	public void verifyHomepageTitle() throws Exception
	{
		dr.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		String expectedTitle = "OrangeHRM";
		String actualTitle = dr.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		System.out.println(actualTitle);		
	}
	@Test(dataProvider = "excel-data")
	public void Admin(String U,String P) throws IOException, InterruptedException
	{
		System.out.println("Usename"+U+"  "+"PassWord"+P);
		obj.login(dr, U, P); //props.getProperty("admin_uname"), props.getProperty("admin_pass"));
		obj.admin(dr);
		obj.assign_leave_days(dr);
	}
  @AfterSuite
  public void afterSuite() {
	  dr.close(); 
	  dr.quit();
	}

}




//@DataProvider(name ="excel-data")
//public Object[][] excelDP() throws IOException{
//	//We are creating an object from the excel sheet data by calling a method that reads data from the excel stored locally in our system
//	Object[][] arrObj = getExcelData("/Users/abhishekanand/simplify3x-eclipse-workspace/OrangeHRMTestingTestNG/src/test/java/OrangeHRMTestNG/LoginCredential.xlsx","Sheet1");
//	return arrObj;
//}
//
//
//
//
////This method handles the excel - opens it and reads the data from the respective cells using a for-loop & returns it in the form of a string array
//public String[][] getExcelData(String fileName, String sheetName){
//
//	String[][] data = null;   	
//	try
//	{
//		FileInputStream fis = new FileInputStream(fileName);
//		XSSFWorkbook wb = new XSSFWorkbook(fis);
//		XSSFSheet sh = wb.getSheet(sheetName);
//		XSSFRow row = sh.getRow(0);
//		int noOfRows = sh.getPhysicalNumberOfRows();
//		int noOfCols = row.getLastCellNum();
//		Cell cell;
//		data = new String[noOfRows-1][noOfCols];
//
//		for(int i =1; i<noOfRows;i++){
//			for(int j=0;j<noOfCols;j++){
////				row = sh.getRow(i);
//				cell= row.getCell(j);
//				if(sh.getRow(i).getCell(j).getCellType()==CellType.NUMERIC)
//				{
//					String value = String.valueOf(cell.getNumericCellValue());
//					data[i-1][j] = value; //cell.getNumericCellValue();
//				}
//				if(sh.getRow(i).getCell(j).getCellType()==CellType.STRING)
//				{
//					data[i-1][j] = sh.getRow(i).getCell(1).getStringCellValue();
//            
//				}
//				else
//				{
//					data[i-1][j] = sh.getRow(i).getCell(1).getStringCellValue();
//					
//				}
//				
//				
//			}
//		}
//	}
//	catch (Exception e) {
//		System.out.println("The exception is: " +e.getMessage());
//	}
//	return data;
//}
