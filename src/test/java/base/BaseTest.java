package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import extentlisteners.ExtentListeners;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.DbManager;
import utilities.ExcelReader;
import utilities.MonitoringMail;

public class BaseTest {
	/*
	 * WebDriver,TestNg,Reporting,Screenshot,log4j,properties,ExcelReading,
	 * JavaMail, DataBase,Keywords, we r going 2 see everything in single project.
	 */

	public static WebDriver driver;
	public static Properties OR = new Properties();
	public static Properties Config = new Properties();
	public static FileInputStream fis;
	public static ExcelReader excel = new ExcelReader(
			"C:\\Selenium\\DataDrivenFramework\\src\\test\\resources\\excel\\testdata.xlsx");
	public static Logger log = Logger.getLogger("BaseTest.class");
	public static MonitoringMail mail = new MonitoringMail();
	public static WebDriverWait wait;

	// These r KeyWords.
	public void quit() {
		driver.quit();
		log.info("browser execution completed");
	}

	public void click(String locator) {
		if (locator.endsWith("_xpath")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_id")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_css")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();

		}
		log.info("Clicking on an Element " + locator);
		ExtentListeners.test.info("Clicking on an Element " + locator); // taking from ExtentListeners.
		// If we want 2 see Extent Reports on the browser.Afer Execution of Testng.xml
		// go to Reports folder under u take properties from updating Extent.html
		// path.click and goto c:drive then click that one.
		//
	}

	public void type(String locator, String value) {
		try {
		if (locator.endsWith("_xpath")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_id")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_css")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}
		log.info("Typing in an Element" + locator + " Entered the value as " + value);
		ExtentListeners.test.info("Typing in an Element" + locator + " Entered the value as " + value);
		System.out.println("111111111111111111");
	}
	catch(Throwable t) {
		t.printStackTrace();
		
		//log.error("Error while typing in an Element : "+locator+" error message is : "+t.getMessage());
		//ExtentListeners.test.info("typing in an Element : " + "Error while clicking on an Element : "+locator+" error message is : "+t.getMessage());

		Assert.fail(t.getMessage());
		//captureScreenshot();
	}
	}

//Above click,type,quit() these r called keyword driven approch. we r giving log directly in these methods.

	@BeforeSuite
	public void setUp() {

		if (driver == null) {
			// load the properties 1st.Logs Intiated.properties r loaded.
			PropertyConfigurator.configure(".\\src\\test\\resources\\properties\\log4j.properties");
			log.info("Test Execution Started !!!"); // like S.O.P

			// load the Config properties.
			try {
				fis = new FileInputStream(".\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Config.load(fis);
				log.info("Config Properties loaded !!!");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// load the OR properties...
			try {
				fis = new FileInputStream(".\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.info("OR Properties loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// launching the browsers.
			if (Config.getProperty("browser").equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.info("Chrome browser launched");

			} else if (Config.getProperty("browser").equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.info(" browser launched");

			}

			// giving the URL name.
			driver.get(Config.getProperty("testsiteurl"));
			log.info("Navigated to : " + Config.getProperty("testsiteurl"));

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			try {
				DbManager.setMySqlDbConnection();
				log.info("Database connection Established !!!"); // Like System.out.p
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			wait = new WebDriverWait(driver, Integer.parseInt(Config.getProperty("explicit.wait")));
		}

	}

	//it will add after the entire program executes.
	@AfterSuite
	public void tearDown() {
		quit();
	}
}
