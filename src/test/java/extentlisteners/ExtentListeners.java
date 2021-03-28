package extentlisteners;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utilities.TestUtil;

//If we have extentlisteners package no need of TestExtent.java class.Because we r splitting and put them into 2 classes.

public class ExtentListeners implements ITestListener {

	static Date d = new Date();
	static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	private static ExtentReports extent = ExtentManager
			.createInstance(System.getProperty("user.dir") + "\\reports\\" + fileName);

	public static ExtentTest test;

	public void onTestStart(ITestResult result) {

		test = extent
				.createTest(result.getTestClass().getName() + "     @TestCase : " + result.getMethod().getMethodName());

	}

	public void onTestSuccess(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " PASSED" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		test.pass(m);

	}

	public void onTestFailure(ITestResult result) {

		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test.fail(result.getThrowable().getMessage());

		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " FAILED" + "</b>";

		String screenshot = TestUtil.fileName;
		test.fail("<b><font color=red>" + "Screenshot of failure" + "</font></b><br>",
				MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());

		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		test.log(Status.FAIL, m);

	}
	
	

	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		test.skip(m);

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ITestContext context) {

		if (extent != null) {

			extent.flush();
		}

	}

}
