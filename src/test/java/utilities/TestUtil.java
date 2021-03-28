package utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import base.BaseTest;

public class TestUtil extends BaseTest {
	
	public static String fileName;
	public static void captureScreenshot() throws IOException {

		Date d = new Date();
		fileName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFile, new File("./reports/" + fileName));

		// FileUtils.copyFile(screenshotFile, new File("./screenshot/error.jpg"));

	}

	@DataProvider(name = "dp")
	// public static Object[][] getData(String sheetName) {

	public static Object[][] getData(Method m) { // Method it's going 2 give current method sheetname.

		String sheetName = m.getName();

		int rows = excel.getRowCount(sheetName);
		int colms = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][colms];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < colms; colNum++) {

				// data[0][0]=excel.getCellData(sheetName, 0, 2); same step we r putting in loop
				// if we have 100 users in excel.

				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}

		}
		return data;
	}
}
