package OrangeHRMTestNG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OrangeHRMTest {
	/*********************************----------Global Variable Deceleration----------******************************/

	static Properties props = new Properties(); // Here I created a object of Properties File
	static userdefiend user = new userdefiend(); // I created one object of my Class file where i stored all in built
	// function
	static Duration time = Duration.ofSeconds(30); // This one is time defination for Exlicit Wait
	WebElement ele; // Just define one global WebElement variable for future use in this program
	String PopFilePath ="/Users/abhishekanand/simplify3x-eclipse-workspace/OrangeHRMTestingTestNG/src/test/java/OrangeHRMTestNG/TestData.properties";
	
	public OrangeHRMTest() throws IOException
	{
		FileReader reader = new FileReader(PopFilePath); // Creating
		props.load(reader);
	}
	
	/*
	 * WebDriver Initialization method
	 */
	/*************************************-----------Universal Method-------------
	 * @throws IOException **********************************/

	//public WebDriver initiate() throws InterruptedException {
	//	System.setProperty("webdriver.chrome.driver", props.getProperty("driverpath"));
		//WebDriver driver = new ChromeDriver(); // Here I define Chrome driver As a Browser
		//driver.get(props.getProperty("url")); // Specified Url for The website we use for testing
		//driver.manage().window().maximize(); // This function is used to maximize the webPage
		//return driver; // Return driver as a object
	//}

	// UserDefined function for Login page Validation and Login
	public boolean login(WebDriver driver, String UserName, String PassWord) throws IOException // Here I pass 2 arguments such as username
	// and PassWord
	{
		
		
		boolean flag = false;
		String[][] Uname_pass = { { "abhi_ashu", "Cisco@9176" },{"simplifyadmin", "SimplifyQA@12DB" }, { "Abhi12", "Abhi@12march" } };
		try 
		{
			//Timeouts implicitlyWait = driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // Here I define
			// a
			// implicitlyWait
			WebElement name = user.findelement(driver,props.getProperty("username")); // UserName
			WebElement pass = user.findelement(driver,props.getProperty("password")); // PassWord
			user.sendKeys(driver, name, time, UserName); // Send the username value
			user.sendKeys(driver, pass, time, PassWord); // Send the password value
			for(int i=0;i<3;i++)
			{
				if(Uname_pass[i][0].equals(UserName) && Uname_pass[i][1].equals(PassWord))
				{
					flag = true;
					break;
				}

			}
			if(flag)
			{
				//this.screenshot(driver,"Login");
				user.findelement(driver,props.getProperty("subbtn")).sendKeys(Keys.RETURN);
			}
			else
			{
				throw new IllegalArgumentException("Login credential are Wrong! Please check once");
			}
			//this.screenshot(driver,"Login1");
		} 
		catch (IllegalArgumentException e) {
			System.out.println(e);
			return false;     
		}
		return true; 

	}

	// LogOut function
	public boolean logout(WebDriver dr) 
	{
		try
		{
			user.click(dr, user.findelement(dr, props.getProperty("welcome_btn")));
			user.click(dr, user.findelement(dr, props.getProperty("logout_btn")));
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	 public void screenshot(WebDriver dr,String filename)
     {
   	  File screenshot = ((TakesScreenshot) dr).getScreenshotAs(OutputType.FILE);
         
         //Copy the file to a location and use try catch block to handle exception   /Users/abhishekanand/Desktop/
         try {
             FileUtils.copyFile(screenshot, new File("/Users/abhishekanand/Desktop/"+filename+".png"));
         } catch (IOException e) {
             System.out.println(e.getMessage());
         }
     }
	/*********************************-------------Admin Method---------------**************************************/

	/*
	 * Home Page Starts 1. Click on Admin Button
	 */
	public void admin(WebDriver dr) throws InterruptedException {
		Thread.sleep(2000); // Here we use Fluent Wait for see the flow how it is Working
		ele = dr.findElement(By.xpath(props.getProperty("adminbtn")));
		ele.click();
		this.screenshot(dr,"Admin");
	}

	// Method for validate UserName Exit or nor
	public boolean validateuser(WebDriver dr, String username, String First_name,String Last_name) {
		try
		{
			dr.findElement(By.xpath(props.getProperty("checkusername"))).sendKeys(username);
			String EmpName = First_name.concat(Last_name);
			dr.findElement(By.xpath(props.getProperty("empname"))).sendKeys(EmpName);
			WebElement ele = dr.findElement(By.xpath(props.getProperty("status")));
			ele.click();
			user.selectValueFromDropDownByValue(ele, "1"); // Here we Select Enabled from dropDown
			
			dr.findElement(By.xpath(props.getProperty("searchbtn"))).click();
			this.screenshot(dr,"Validate");
			/*
			 * This Condition Will return user is Exit or Not if Exit then this function
			 * will return false OtherWise Return True
			 */
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Method For hover And Also Static because We use very less This function use
	 * inside the function where we need hover Action
	 */

	// Method To go PIM and select Add Employee and click
	public boolean addEmployee(WebDriver dr, String username, String FirstName, String LastName)
			throws InterruptedException {
		try
		{
			if(this.validateuser(dr, username, FirstName, LastName))
			{
				System.out.println("User is already Exit therefor I proceed to add superVisor!");
				try
				{
					
					this.clickOnEmpList(dr);
					
					this.addSupervisor(dr);
					
					return true;
				}
				catch(Exception e)
				{
					System.out.println("Error in Add_supervisor() Method");
					e.printStackTrace();
					return false;
				}
			}
			else
			{
				System.out.println("User doesn't exit so i proceed for add Employee");
				try
				{
					ele = dr.findElement(By.xpath(props.getProperty("pim")));
					user.moveToElement(dr, ele);
					dr.findElement(By.xpath(props.getProperty("addEmployee"))).click();
					dr.findElement(By.xpath(props.getProperty("FullName_FN"))).sendKeys(FirstName);
					dr.findElement(By.xpath(props.getProperty("FullName_LN"))).sendKeys(LastName);
					dr.findElement(By.xpath(props.getProperty("photograph"))).sendKeys(props.getProperty("photopath"));
					dr.findElement(By.xpath(props.getProperty("checkbox"))).click();
					// Thread.sleep(2000);
					dr.findElement(By.xpath(props.getProperty("Uname"))).sendKeys(username);
					dr.findElement(By.xpath(props.getProperty("user_pass"))).sendKeys("Abhi@12march");
					dr.findElement(By.xpath(props.getProperty("con_pass"))).sendKeys("Abhi@12march");
					// Thread.sleep(1000);
					dr.findElement(By.xpath(props.getProperty("savebtn"))).click();
					this.screenshot(dr,"Add_Employee1");
					Thread.sleep(2000);
					dr.findElement(By.xpath(props.getProperty("edit_btn"))).click();
					dr.findElement(By.xpath(props.getProperty("DriveryLic"))).sendKeys("BR0120192023");
					dr.findElement(By.xpath(props.getProperty("Lic_Exp_date"))).clear();
					WebElement lic_exp_date = dr.findElement(By.xpath(props.getProperty("Lic_Exp_date")));//.sendKeys("2025-10-20");
					user.selectDateByJS(dr,lic_exp_date,"2025-10-20");
					dr.findElement(By.xpath(props.getProperty("radio_man"))).click();
					WebElement ele2 = dr.findElement(By.xpath(props.getProperty("Marital_status")));
					user.selectValueFromDropDownByValue(ele2, props.getProperty("marital_value"));
					WebElement nationality = dr.findElement(By.xpath(props.getProperty("nationality")));
					user.selectValueFromDropDownByValue(nationality, props.getProperty("nationality_value"));
					dr.findElement(By.xpath(props.getProperty("DOB"))).clear();
					dr.findElement(By.xpath(props.getProperty("DOB"))).sendKeys("2001-10-20");
					this.screenshot(dr,"Add_Employee2");
					Thread.sleep(3000);
					dr.findElement(By.xpath(props.getProperty("btn_save"))).click();
					
					this.validateuser(dr, username, FirstName, LastName);
					return true;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}

	}

	// Click on employee List
	public void clickOnEmpList(WebDriver dr) {
		ele = dr.findElement(By.xpath(props.getProperty("pim")));
		user.moveToElement(dr, ele);
		this.screenshot(dr,"EmpList");
		
		dr.findElement(By.xpath(props.getProperty("Employee_list"))).click();
	}
	// Check SuperVisior has Been already assigned?
	public int checkSuper(WebDriver dr)
	{
		try
		{
			List<WebElement> supervisiors =user.findelements(dr, props.getProperty("multiple_super"));
			String checkSuper = supervisiors.get(6).getAttribute("value");
			String[] multipleSuper = checkSuper.split(",");
		    int i = multipleSuper[1].length();
			//int supervisor_Count = Integer.valueOf(checkSuper);
		    //System.out.println(checkSuper);
			System.out.println(i);
			return i;
		}
		catch(Exception e)
		{
			//System.out.println("Error Generate because Already Supervisor has been assigned to that Employee!");
			return 0;
		}
	}
	// Add Supervisor
	public boolean addSupervisor(WebDriver dr) {

		try
		{
			if(this.checkSuper(dr)<0)
			{
				dr.findElement(By.xpath(props.getProperty("serch_by_id"))).sendKeys(props.getProperty("id"));// 3610  
				dr.findElement(By.xpath(props.getProperty("search_button"))).click();

				dr.findElement(By.xpath(props.getProperty("click_on_id"))).click();
				dr.findElement(By.xpath(props.getProperty("click_on_report_to"))).click();
				dr.findElement(By.xpath(props.getProperty("click_on_add_supervisor"))).click();
				WebElement name = dr.findElement(By.xpath(props.getProperty("sup_name")));
				if(user.sendKeys(dr, name, time, props.getProperty("SuperVisor_Name")))
				{
					name.sendKeys(Keys.RETURN);
					WebElement ele = dr.findElement(By.xpath(props.getProperty("reporting_method")));
					ele.click();
					user.selectValueFromDropDownByValue(ele, "1");

				}
				this.screenshot(dr,"Supervisor");
				user.click(dr,dr.findElement(By.xpath(props.getProperty("btnSaveReport"))));
				//			if(user.displayElement(dr.findElement(By.xpath(props.getProperty("requird")))))
				//			{
				//				
				//				System.out.println("Some Error Occured on Name Input Box");
				//				return false;
				//			}
				//name.sendKeys("Aryan Anand");
				// name.sendKeys(Keys.RETURN);
				//if(user.displayElement(dr.findElement(By.xpath(props.getProperty("requird")))))
				System.out.println(props.getProperty("SuperVisor_Name")+" is Added Thank you");
				
			}
			else
			{
                  throw new IllegalArgumentException("Error Generate because Already Supervisor has been assigned to that Employee!");
			}
			
			return true;

		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
			return false;
		}
		catch(Exception e)
		{
			System.out.println("Error in Add SuperVisor Method plzz solve first");
			e.printStackTrace();
			return false;
		}
		//		} else {
		//			System.out.println(
		//					"User Does Not Exist Please Take Another User ID otherThan Add Employeee! Add SuperVisor()");
		//		}

	}
	// Asssign leave days balance This is done by Admin
	public boolean assign_leave_days(WebDriver dr) throws InterruptedException {
		try
		{
			if(this.addEmployee(dr, props.getProperty("UName"), props.getProperty("Emp_First_Name"),props.getProperty("Emp_Last_Name")))
			{
				ele = dr.findElement(By.xpath(props.getProperty("leave")));
				user.moveToElement(dr, ele);
				WebElement ele1 = dr.findElement(By.xpath(props.getProperty("leave_Entilement")));
				user.moveToElement(dr, ele1);
				dr.findElement(By.xpath(props.getProperty("add_Empliments"))).click();
				// dr.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
				Thread.sleep(2000);
				WebElement Emp = dr.findElement(By.xpath(props.getProperty("Employee_Name")));
				// new WebDriverWait(dr,
				// time).until(ExpectedConditions.visibilityOfAllElements(Emp));

				Emp.clear();
				Emp.sendKeys("Abhishek Anand");
				Emp.sendKeys(Keys.ENTER);
				Emp.sendKeys(Keys.ENTER);
				WebElement leave_type = dr.findElement(By.xpath(props.getProperty("leave_Type_Entilement")));
				leave_type.click();
				user.selectValueFromDropDownByValue(leave_type, "3");
				WebElement leave_period = dr.findElement(By.xpath(props.getProperty("leave_period")));
				leave_period.click();
				user.selectValueFromDropDownByValue(leave_period, "2023-04-01$$2024-03-31");
				dr.findElement(By.xpath(props.getProperty("Entilement"))).sendKeys("30");
				this.screenshot(dr,"Assign_Leave_days");
				dr.findElement(By.xpath(props.getProperty("entilement_save_btn"))).click();
				WebElement confirm = dr.findElement(By.xpath(props.getProperty("confirm_btn")));
				this.screenshot(dr,"PopUp");
				confirm.click();
				return true;
			}
			return true;

		}
		catch(StaleElementReferenceException e)
		{
			System.out.println("Confirm button is not interectable!");
			return false;
		}
		catch(ElementNotInteractableException e)
		{
			System.out.println("Confirm button is not interectable!");
			return false;
		}
		catch(Exception e)
		{
			System.out.println("Assign leave does not work properly just try again");
			e.printStackTrace();
			return false;
		}

	}
	/***********************************-----------SuperVisor Method-------------***********************************/

	// Method to assign leave This method done by SuperVisior
	public boolean assignLeave(WebDriver dr) throws InterruptedException {

		try
		{
			if(this.approve_leave(dr))
			{
				dr.findElement(By.xpath(props.getProperty("Assign_leave_afterapproval"))).click();
				dr.findElement(By.xpath(props.getProperty("Emp_Name"))).sendKeys("Abhishek Aryan");
				WebElement ele = dr.findElement(By.xpath(props.getProperty("leave_Type")));
				ele.click();
				user.selectValueFromDropDownByValue(ele, props.getProperty("leave_type_value"));
				
				
				String[] FromDate = user.splitStr(props.getProperty("Emp_from_date_leave")); // This function used to split date into year, Month, Day and stored into Array String
				int From_month = Integer.valueOf(FromDate[1]);
				user.datePicker(dr, dr.findElement(By.xpath(props.getProperty("from_date"))), props.getProperty("assign_year"),props.getProperty("assign_month"), props.getProperty("assign_date_from"),FromDate[0],FromDate[1],FromDate[2]);
				
				
				String[] ToDate = user.splitStr(props.getProperty("Emp_to_date_leave"));
				int To_month = Integer.valueOf(ToDate[1]);
				if(From_month<To_month)
				{
					user.datePicker(dr, dr.findElement(By.xpath(props.getProperty("to_date"))), props.getProperty("assign_year"),props.getProperty("assign_month"), props.getProperty("assign_date_from"),ToDate[0],ToDate[1],ToDate[2]);
				}
				else
				{
					System.out.println("To date is Less than from date");
					return false;
					
				}
//				user.datePicker(dr, dr.findElement(By.xpath(props.getProperty("from_date"))), props.getProperty("assign_year"),props.getProperty("assign_month"), props.getProperty("assign_date_from"),"2023","4","23");
//				
//				user.datePicker(dr, dr.findElement(By.xpath(props.getProperty("to_date"))), props.getProperty("assign_year"),props.getProperty("assign_month"), props.getProperty("assign_date_from"),"2023","4","23");
//				
				
				dr.findElement(By.xpath(props.getProperty("textarea"))).sendKeys("plzz sir assign some leave its is mandetory thanku sir");

				dr.findElement(By.xpath(props.getProperty("assign_btn"))).click();
//				if (dr.findElement(By.xpath(props.getProperty("overlaping_leave_days"))).isDisplayed()) {
//					System.out.println("overlaping_leave_days found please continue after date change thank you!");
//					dr.findElement(By.xpath(props.getProperty("after_verlaping_click_assign"))).click();
//				}
			}
			else
			{
				throw new IllegalArgumentException("Error Genereted in approve_leave() Method!"); 
			}

			return true;
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
			return false;
		}
		catch(Exception e)
		{
			System.out.println("Error Genereted in assignLeave() Method!");
			//e.printStackTrace();
			return false;
		}
	}

	// Approved Leave
	public boolean approve_leave(WebDriver dr) {
		try
		{
			dr.findElement(By.xpath(props.getProperty("leave_list"))).click();
			List<WebElement> ele = dr.findElements(By.xpath(props.getProperty("select_action_list")));
			List<WebElement> approve = dr.findElements(By.xpath(props.getProperty("approve")));
			List<WebElement> cancel = dr.findElements(By.xpath(props.getProperty("cancle")));
			System.out.println(ele.size());
			if(ele.size()==0)
			{
				System.out.println("There is no  leave for approval and Cancle");
				//return true;
			}
//			System.out.println(approve.size());
//			System.out.println(cancel.size());
			else
			{
			for (int i = 0; i < ele.size(); i++) {
				user.Clickableelement(dr, ele.get(i), time);
				ele.get(i).click();	
				
//				String app = approve.get(i).getText();	
//				System.out.println(app);
//				String can = cancel.get(i).getText();
//				System.out.println(can);
//				if(app.equals("Approve")))
//				{
//					user.selectValueFromDropDownByText(ele.get(i), props.getProperty("slect_action_value_approve"));
//				}
//				else
					user.selectValueFromDropDownByText(ele.get(i), props.getProperty("slect_action_value_cancel"));
				//user.Visibleelement(dr, time, props.getProperty("approve"));
				
			}
			}
			// selectValueFromDropDownByValue(ele,props.getProperty("slect_action_value"));
			dr.findElement(By.xpath(props.getProperty("save_app_btn"))).click();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Error genereted in approve_leave() Method");
			//e.printStackTrace();
			return false;
		}
	}

	



	/*************************************-----------Employee Method-------------***********************************/

	// Verify you have leave and how much
	public double leave_balance(WebDriver dr) {
		try
		{
			WebElement emp_entitlements = dr.findElement(By.xpath(props.getProperty("emp_entitlements")));
			user.moveToElement(dr, emp_entitlements);
			dr.findElement(By.xpath(props.getProperty("emp_my_entitlements"))).click();
			WebElement leave_count = dr.findElement(By.xpath(props.getProperty("leave_balance")));
			String count = leave_count.getText();
			double leave = Double.parseDouble(count);
			return leave;
		}
		catch(Exception e)
		{
			System.out.println("Error Genreted in Leave balance() Method! Correct it first");
			//e.printStackTrace();
			return 0;
		}
	}

	// verify given date has been already leave taken
	public static boolean verify_date_you_want_to_leave(WebDriver dr) {
		/*
		 * Create List of element for Selecting All Approval By the SuperVisor
		 */
		try
		{
			List<WebElement> whole_pre_odd_dates = user.findelements(dr, props.getProperty("whole_pre_odd_dates"));
			List<WebElement> whole_pre_even_dates = user.findelements(dr, props.getProperty("whole_pre_even_dates"));
			int odd_dates = whole_pre_odd_dates.size();
			int even_dates = whole_pre_even_dates.size();
			if (odd_dates > 1 && even_dates > 1) 
			{
				System.out.println("Sorry for inconvenience" + "\n" + "Those date you want leave is already applied/Taken!");
				return false;
			}
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Error genereted In verify_date_you_want_to_leave() Method please check first");
			//e.printStackTrace();
			return false;
		}
	}

	// Method for Apply Leave
	public boolean apply_leave(WebDriver dr) throws InterruptedException 
	{
		//Thread.sleep(5000);
		try
		{
			dr.findElement(By.xpath(props.getProperty("Apply_leave"))).click();
			// if(dr.findElement(By.xpath(props.getProperty("leave_not_avl"))).isEnabled())
			// {
			// System.out.println("You cann't apply for leave because Admin doesn't assign
			// leave YET!" + "/n" + "First Talk to Admin");
			// }
			// else
			// {
			double leave_balance_left = this.leave_balance(dr); // Storing the Leave Days Before Apply Apply Leave by the
			// Employee

			if(leave_balance_left > 1.00) {
				dr.findElement(By.xpath(props.getProperty("agin_click_Apply"))).click();
				ele = dr.findElement(By.xpath(props.getProperty("selct_leave_type")));
				user.selectValueFromDropDownByValue(ele, props.getProperty("selct_leave_type_value"));
				//datePicker(WebDriver dr,WebElement ele,String xPathOfYear,String xPathOfMonth,String xPathOfDay,String year,String month,String day)		
				
				
				
				String[] FromDate = user.splitStr(props.getProperty("Emp_from_date_leave"));
				int From_month = Integer.valueOf(FromDate[1]);
				user.datePicker(dr, dr.findElement(By.xpath(props.getProperty("from_date_leave"))), props.getProperty("assign_year"),props.getProperty("assign_month"), props.getProperty("assign_date_from"),FromDate[0],FromDate[1],FromDate[2]);
				
				
				String[] ToDate = user.splitStr(props.getProperty("Emp_to_date_leave"));
				int To_month = Integer.valueOf(ToDate[1]);
				if(From_month<To_month)
				{
					user.datePicker(dr, dr.findElement(By.xpath(props.getProperty("to_date_leave"))), props.getProperty("assign_year"),props.getProperty("assign_month"), props.getProperty("assign_date_from"),ToDate[0],ToDate[1],ToDate[2]);
					
				}
				else
				{
					System.out.println("To date is Less than from date");
					return false;
					
				}
				
				
				
				WebElement ele1 = dr.findElement(By.xpath(props.getProperty("apply_for_leave")));
				ele1.click();
				//ele1.click();
			} else {
				System.out.println("Sorry for inconvenience" + "\n"
						+ "But You have Less than 1 leave so you cant apply for leave Now!");
			}
				if(user.displayElement(user.findelement(dr, props.getProperty("valdiate_heading"))))
				    verify_date_you_want_to_leave(dr);
			
			return true;
		}
		catch(NoSuchElementException e)
		{
			
			System.out.println("This is New Request from Employeee! Ok I talk to SuperVisor/Admin");
			return false;
		}
		
//		catch (ParseException e) {
//            e.printStackTrace();
//            return "15.01.2010";
//        }
		catch(Exception e)
		{
			//System.out.println("Error Occurred on apply leave Please be sure and check all details are correct!");
			e.printStackTrace();
			return false;
		}
		
	}
//Rough Work
	
	


	/***********************************-------------Main Method--------------*************************************/

//	public static void main(String[] args) throws IOException, InterruptedException {
//		// TODO Auto-generated method stub
//		try
//		{
//			Scanner sc = new Scanner(System.in); // Create A object of Scanner Class for taking Console Input
//			FileReader reader = new FileReader("/Users/abhishekanand/simplify3x-eclipse-workspace/OrangeHRMTesting/src/test/java/OrangeHRM/TestData.properties"); // Creating
//
//			props.load(reader); // Loading the properties File using object of FilReader Class
//			OrangeHRMTest obj = new OrangeHRMTest(); // Creating the Object of This Class
//            
//			WebDriver dr; // Initialize WebDriver reference variable
//			
//			/*
//			 * Here We use Do-While Loop because do while check condition after Executing
//			 * first
//			 */
//
//			do {
//				System.out.println("Press 1 for Admin" + "\n" + "Press 2 for Employee" + "\n" + "Press 3 for Supervisor"
//						+ "\n" + "Press 4 for EXIT");
//				int user = sc.nextInt();
//				switch (user) {
//				case 1:
//					dr = obj.initiate();
//					if(obj.login(dr, props.getProperty("admin_uname"), props.getProperty("admin_pass")))
//					{
//						obj.admin(dr);
//						obj.assign_leave_days(dr);
//						//obj.logout(dr);
////						obj.clickOnEmpList(dr);
////						obj.checkSuper(dr);
//					}
//					else
//					{
//						throw new IllegalArgumentException("Login Method will Not work Check and then run the program");
//					}
//
//					break;
//				case 2:
//					dr = obj.initiate();
//					if(obj.login(dr, "abhi_ashu", "Cisco@9176"))//abhi_ashu
//					{
//						obj.apply_leave(dr);
//						//obj.logout(dr);
//					}
//					else
//					{
//						throw new IllegalArgumentException("Login Method will Not work Check and then run the program");
//					}
//
//					break;
//				case 3:
//					dr = obj.initiate();
//
//					if(obj.login(dr, props.getProperty("super_uname"), props.getProperty("super_pass")))//Aryan12
//					{
//						obj.assignLeave(dr);
//						//obj.approve_leave(dr);
//						//obj.logout(dr);
//					}
//					else
//					{
//						throw new IllegalArgumentException("Login Method will Not work Check and then run the program");
//					}
//					break;
//				case 4:
//					System.exit(0);
//				default:
//					System.out.println("Please Enter only those role Specify in the above" + "\n" + "Thank you");
//
//				}
//			} while (true); 
//			/* This Will return always True 
//			 * So user Will decide When 
//			 * program will Stop 
//			 * The Execution 
//			 * */
//		}
//		catch (IllegalArgumentException e) {
//			System.out.println(e);
//		}
//
//
//	}

}
