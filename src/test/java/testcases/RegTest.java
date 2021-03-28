package testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.TestUtil;

public class RegTest extends BaseTest {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void doRegUser(String firstName, String lastName) {
		System.out.println(firstName + " " + lastName);
		
		//writing comment for git1.
		
	}

	
}
