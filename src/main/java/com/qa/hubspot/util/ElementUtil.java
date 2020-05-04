package com.qa.hubspot.util;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.hubspot.base.BasePage;

public class ElementUtil extends BasePage{
	
	WebDriver driver;
	WebDriverWait wait;
	JavaScriptUtil jsUtil;
	Properties prop;
	
	
	public ElementUtil(WebDriver driver) {
		this.driver  = driver;
		wait = new WebDriverWait(driver, AppConstants.DEFAULT_TIMEOUT);
		jsUtil = new JavaScriptUtil(driver);
		
	}
	
	/**
	 * Wait for title
	 * @param title
	 * @return
	 */
	public boolean waitForTitlePresent(String title) {
		wait.until(ExpectedConditions.titleIs(title));
		return true;
	}
	
	/**
	 * presenceOfElementLocated
	 * @param locator
	 * @return
	 */
	public boolean waitForElementPresent(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return true;
	}
	
	/**
	 * visibilityOfElementLocated
	 * @param locator
	 * @return
	 */
	public boolean waitForElementVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return true;
	}
	
	/**
	 * @author bobit
	 * Gettitle method
	 * @return
	 */
	public String doGetPageTitle() {
		
		try {
			return driver.getTitle();
		}
		catch (Exception e) {
			System.out.println("some excaption got occured while getting the title...");
		}
		return null;
	}
	
	/**
	 * Get element
	 * @param locator
	 * @return
	 */
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			//if(waitForElementPresent(locator));
			element = driver.findElement(locator);
			if(highlightElement) {
				jsUtil.flash(element);
			}
		} catch (Exception e) {
			System.out.println("some exception occured while creating the web element...");
		}
		return element;
	}
	
	/**
	 * Click on method
	 * @param locator
	 */
	public void doClick(By locator) {
		try {
		getElement(locator).click();
		
		}
		catch (Exception e) {
			System.out.println("some exception occured while clicking the web element...");
		}
	}
	
	/**
	 * SendKeys Method
	 * @param locator
	 * @param value
	 */
	public void doSendKeys(By locator, String value) {
		
		try {
			WebElement element = getElement(locator);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			System.out.println("some exception occured while entering values in a field.");
		}
	}
	
	/**
	 * isDisplayed
	 * @param locator
	 * @return
	 */
	public boolean doIsDsiplayed(By locator) {
		
		try {
			return getElement(locator).isDisplayed();
		}
		catch (Exception e) {
			System.out.println("some exception occured while isDisplayed");
		}
		return false;
	}
	
	/**
	 * isEnabled
	 * @param locator
	 * @return
	 */
    public boolean doIsEnabled(By locator) {
		
		try {
			return getElement(locator).isEnabled();
		}
		catch (Exception e) {
			System.out.println("some exception occured while isEnabled");
		}
		return false;
	}
    
    /**
     * isSelected
     * @param locator
     * @return
     */
    public boolean doIsSelected(By locator) {
		
		try {
			return getElement(locator).isSelected();
		}
		catch (Exception e) {
			System.out.println("some exception occured while isSelected");
		}
		return false;
	}
    
    public String doGetText(By locator) {
    	try {
    		return getElement(locator).getText();
    	}
    	catch (Exception e) {
    		System.out.println("some exception occured while getting text");
		}
		return null;
    }
	
}
