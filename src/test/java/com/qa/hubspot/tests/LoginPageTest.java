package com.qa.hubspot.tests;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;


import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import schemasMicrosoftComOfficePowerpoint.IscommentDocument;

@Epic("Epic - 101 : create login features")
@Feature("US - 501 : create test for login on HubSpot")
public class LoginPageTest {
	
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	Credentials userCred;
	
	Logger log = Logger.getLogger(LoginPageTest.class);
	
	@BeforeMethod(alwaysRun = true)
	@Parameters(value= {"browser"})
	public void setup(String browser) {
		String browserName = null;
		basePage = new BasePage();
		prop = basePage.init_properties();
		
		if(browser.equals(null)) {
			browserName = prop.getProperty("browser");
		}else {
			browserName = browser;
		}
		
		driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		log.info("url is launched: "+ prop.getProperty("url")); 
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Test(priority = 1, groups = "sanity", description = "verify login page title", enabled = true)
	@Description("verify Login Page title")
	@Severity(SeverityLevel.NORMAL)
	public void verifyLoginPageTitleTest() {
		log.info("starting -------------------------->>>> verifyLoginPageTest");
		String title = loginPage.getPageTitle();
		System.out.println("login page title is: "+ title);
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
		log.info("ending -------------------------->>>> verifyLoginPageTest");
		log.warn("some warning");
		log.error("some error");
		log.fatal("fatal error");
	}
	
	@Test(priority = 2, description = "verify sign up link", enabled = true)
	@Description("verify sign up link")
	@Severity(SeverityLevel.NORMAL)
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.checkSignUpLink());
	}
	
	@Test(priority = 3, description = "login test features", enabled = true)
	@Description("Login page test feature")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() {
		HomePage homePage = loginPage.doLogin(userCred);
		String accountName = homePage.getLoggedInUserAccountName();
		System.out.println("looged in account name: "+ accountName);
		Assert.assertEquals(accountName, prop.getProperty("accountname"));
	}
	
	
	@DataProvider
	public Object[][] getLoginInvalidData() {
		
		Object data[][] = {{"bill@gmail.com", "test1234"}, 
				           {"tily@gmail.com", " "},
				           {" ", "test4567"},
				           {"yummy", "yummy"},
				           {" ", " "}};
		return data;
	}
	
	@Test(priority = 4, dataProvider = "getLoginInvalidData", description = "Invalid test case using invlaid credentials", enabled = false)
	public void login_InvalidTestCase(String username, String pwd) {
		
		userCred.setAppUsername(username);
		userCred.setAppPassword(pwd);
		loginPage.doLogin(userCred);
		Assert.assertTrue(loginPage.checkLoginErrorMessage());
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

}
