package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.ContactsPage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hubspot.util.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Epic("Epic - 103 : create Contacts page features")
@Feature("US - 503 : create test for contacts page on HubSpot")
public class ContactsPageTest {
	
	BasePage basePage;
	Properties prop;
	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;
	ContactsPage contactsPage;
	Credentials userCred;
	
	
	@BeforeMethod
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_properties();
		String browser = prop.getProperty("browser");
		driver = basePage.init_driver(browser);
		driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
		homePage = loginPage.doLogin(userCred);
		contactsPage = homePage.goToContactsPage();
		
	}
	
	@Test(priority = 1, description = "verify contacts page title")
	@Description("verify Contacts Page title")
	@Severity(SeverityLevel.NORMAL)
	public void verifyContactsPageTitle() {
		String title = contactsPage.getConctactsPageTitle();
		System.out.println("contacts page title is: "+ title);
		Assert.assertEquals(title, AppConstants.CONTACTS_PAGE_TITLE);
	}
	
	@DataProvider
	public Object[][] getContactsTestData(){
		Object[][] data = ExcelUtil.getTestData(AppConstants.CONTACTS_SHEET_NAME);
		return data;
	}
	
	@Test(priority = 2, dataProvider = "getContactsTestData", description = "create new contacts")
	@Description("create new contacts")
	@Severity(SeverityLevel.BLOCKER)
	public void createContactsTest(String email, String firstName, String lastName, String jobTitle) {
		contactsPage.createNewContact(email, firstName, lastName, jobTitle);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	
	
	

}
