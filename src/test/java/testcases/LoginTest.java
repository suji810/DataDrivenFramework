package testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.ExcelReader;
import utilities.TestUtil;

public class LoginTest extends BaseTest {

//	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")

	@Test(dataProviderClass = TestUtil.class, dataProvider="dp")
	public void doLogin(String username,String password) {    
		// first we r navigating url. username,pwd,submitbutton. give the locator and
		// value. taking username and password from Excel using DP.

		type("email_id", username); // taking type method from base class for username.
		type("password_id", password);
		click("loginBtn_id"); // for button
	}
	
	
	// If we have Method m in TestUtil class we no need 2 write this one.we can optimize our code more easily.

/*	@DataProvider(name = "dp")  // copy this method from TestParameterization from SeleniumMvnProject
	public Object[][] getData()

	{
		
		 * If we want to get data from excel we should create d object for ExcelReader
		 * .and we need to provide the path.we should give that path in
		 * src/test/resources-->package-excel.. copy d excel folder property location
		 * and saveAs the excel file give name like testdata2. go 2 c drive paste the
		 * path and save d file name there.It stored as .xlxs.then refresh d resources
		 * packg.
		 

		String sheetName ="doLogin"; // give Excel sheet name. taking "doLogin " from above method.
		int rows = excel.getRowCount(sheetName); // sheetName taking from ExcelReader class.
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][cols]; // from excel :3 rows and 2 columns

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < cols; colNum++) {
				// data[0][0]=excel.getCellData(sheetName, 0, 2); same step we r putting in loop
				// if we have 100 users in excel.
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}    //If we have 100 testcases we can't write this loop for every case so, we put this one in seperate class
		//in Utilities.we can call from there where evere we need.

		return TestUtil.getData("doLogin");
	} */

}
