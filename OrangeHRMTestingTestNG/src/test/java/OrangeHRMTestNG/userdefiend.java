package OrangeHRMTestNG;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.TakesScreenshot;


public class userdefiend {
	//ValidateText using getAttribute function
	public static boolean valdiatetext(WebDriver driver,WebElement element,String expectedstring)
	{
		String ele = element.getAttribute("value");
		if(ele.equals(expectedstring))
		{
			return true;
		}
		return false;
	}

	//Switching to Frame 
	public static void switchToFrame(WebDriver driver)
	{
		driver.switchTo().frame("mainpanel");
	}

	//Method for finding multiple elements
	public  List<WebElement> findelements(WebDriver driver,String xpath)
	{
		List<WebElement> elements = (List<WebElement>) driver.findElements(By.xpath(xpath));
		return elements;
	}
	// Method for checkBox   
	public static void multiplecheckbox(WebElement element, List<WebElement> checkboxes,String value)
	{

		for(int i=0; i<checkboxes.size(); i++)        
		{   
			String mainvalue = checkboxes.get(i).getAttribute("value");
			System.out.println(value);
			if (mainvalue.equalsIgnoreCase(value))

			{       
				//userdefiend.findelement(dr,props.getProperty("Brandcheckbox")).click();
				break;
			}
		}
	}

	//Method for Element finding
	public  WebElement findelement(WebDriver driver,String xpath)
	{
		WebElement element = driver.findElement(By.xpath(xpath));
		return element;

	}
	// Explicit wait until visibilityOfAllElementsLocatedBy
	public boolean Clickableelement(WebDriver driver, WebElement element, Duration timeout)
	{
		try
		{
			new WebDriverWait(driver, timeout).
			until(ExpectedConditions.elementToBeClickable(element));
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exceeded Wait time of Clickable Function");
			e.printStackTrace();
			return false;
		}
	
	}
	//Method for Visible element
	public boolean Visibleelement(WebDriver driver, Duration timeout,String xpath)
	{
		try
		{
			new WebDriverWait(driver, timeout).
			until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exceeded Wait time of Visible Function");
			e.printStackTrace();
			return false;
		}
		
	}

	//Explicit Wait to Click on any Element
	public boolean clickOn(WebDriver driver, WebElement element, Duration timeout)
	{
		try
		{
			new WebDriverWait(driver, timeout).
			until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	//Method to Click on Element
	public boolean click(WebDriver driver, WebElement element)
	{
		try {
			element.click();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Errors on click Method");
			e.printStackTrace();
			return false;
		}
		
	}
	//Method foe sending text to Text field
	public boolean sendkeysvalue(WebDriver driver,WebElement element, String keysvalue)
	{
		try
		{
			element.sendKeys(keysvalue);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Add Supervisor name input box doesn't take value plzz  check once method!");
			e.printStackTrace();
			return false;
		}
	}

	//Explicit Wait for Sending Data to any Element.
	public boolean sendKeys(WebDriver driver, WebElement element, Duration timeout, String value)
	{
		try
		{
			new WebDriverWait(driver, timeout).
			until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(value);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Scroll to Particular Element
	public boolean scrollSpecificElement(WebDriver driver,WebElement element)
	{
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", element);
			//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Element Display or Not
	public boolean displayElement(WebElement element)
	{
		boolean elementDisplayed = element.isDisplayed();
		if(elementDisplayed)
		{
			System.out.println("Element is Displayed");
			return true;
		}
		else
		{
			System.out.println("Element is not Displayed");
		}
		return false;
	}
	
	// Method for find not display element
	public boolean NotdisplayElement(WebElement element)
	{
		boolean elementDisplayed = element.isDisplayed();
		if(elementDisplayed)
		{
			System.out.println("Element is Displayed");
			return false;
		}
		else
		{
			System.out.println("Element is not Displayed");
		}
		return true;
	}
	//Element is Enable or Not
	public  boolean enableElement(WebDriver dr,WebElement element)
	{
		boolean elementEnabled= element.isEnabled();
		if(elementEnabled)
		{
			System.out.println("Element is Enabled in Page");
		}
		else
		{
			System.out.println("Element is not Enabled in Page");
		}
		return elementEnabled;
	}

	//Select Value from Dropdown by using SelectByVisibleTest Method
	public boolean selectValueFromDropDownByText(WebElement element, String value)
	{
		try
		{
			Select select = new Select(element);
			select.selectByVisibleText(value);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Select Value from Dropdown by using SelectByIndex Method
	public boolean selectValueFromDropDownByIndex(WebElement element, int value)
	{
		try
		{
			Select select = new Select(element);
			select.selectByIndex(value);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Select Value from Dropdown by using SelectByValue Method
	public boolean selectValueFromDropDownByValue(WebElement element, String value)
	{
		try
		{
			Select select = new Select(element);
			select.selectByValue(value);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Print all the Values from Dropdown and Select a Value from Dropdown
	public boolean selectDropDownValue(String xpathValue, String value,WebDriver driver)
	{
		try
		{
			List<WebElement> dropdownlist = driver.findElements(By.xpath(xpathValue));
			System.out.println(dropdownlist.size());
			for(int i=0; i<dropdownlist.size(); i++)
			{
				//System.out.println(dropdownlist.get(i).getText());
				if(dropdownlist.get(i).getText().equals(value))
				{
					dropdownlist.get(i).click();
					break;
				}
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Function to Accept an Alert Pop-Up
	public boolean acceptAlertPopup(WebDriver driver) throws InterruptedException
	{
		try
		{
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Thread.sleep(2000);
			alert.accept();
			System.out.println("Alert Accepted Successfully");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Something Went Wrong -- Please Check" +e.getMessage());
		}
		return false;
	}


	//Click on Element using Actions Class
	public boolean clickOnElementUsingActions(WebElement element,WebDriver driver)
	{
		try
		{
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().perform();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Method to Match Value with List of Elements and Click on it
	public boolean clickOnMatchingValue(List<WebElement> listOfElements, String valueToBeMatched)
	{
		try
		{
			for(WebElement element : listOfElements) 
			{
				if(element.getText().equalsIgnoreCase(valueToBeMatched)) 
				{
					element.click();
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Select Calendar Date Or Data Picker Using Java Script Executor
	public boolean selectDateByJS(WebDriver driver, WebElement element, String dateValue)
	{
		try
		{
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript("arguments[0].setAttribute('value','"+dateValue+"');", element); 
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Function to Mouse Hover and Click Or Select an Element using Actions Class
	public boolean moveToElement(WebDriver driver, WebElement element)
	{
		try
		{
			Actions actions = new Actions(driver);
			actions.moveToElement(element).build().perform();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Function to Drag and Drop using Actions Class
	public boolean dragAndDrop(WebDriver driver, WebElement elementOne, WebElement elementTwo)
	{
		try
		{
			Actions actions = new Actions(driver);
			actions.dragAndDrop(elementOne, elementTwo).release().build().perform();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Scroll Down the Page Using Java Script
	public boolean scrollPageDown(WebDriver driver)
	{
		try
		{
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean datePicker(WebDriver dr,WebElement ele,String xPathOfYear,String xPathOfMonth,String xPathOfDay,String year,String month,String day)
	{
		try
		{
			ele.click();
			WebElement Year =dr.findElement(By.xpath(xPathOfYear));
			this.click(dr, Year);
			this.selectValueFromDropDownByValue(Year,year);
			WebElement Month = this.findelement(dr, xPathOfMonth);
			this.click(dr, Month);
			int monn = Integer.valueOf(month);
			int mon = monn-1;
			String Mon = Integer.toString(mon);
			this.selectValueFromDropDownByValue(Month, Mon);
			List<WebElement> Date = this.findelements(dr, xPathOfDay);
			for(int i=0;i<Date.size();i++)
			{
				String cal_day =  Date.get(i).getText();
				//System.out.println(cal_day);
				if(cal_day.equals(day))
				{
					Date.get(i).click();
					break;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	public String[] splitStr(String str)
	{
		String[] newStr = str.split("-");
		return newStr;
	}
	//ScreenShot
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
}


