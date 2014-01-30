/*
* Copyright (c) 2014, Rajasankar All Rights Reserved.
* Contact at twitter.com/rajasankar
*
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.raja.anucarita;

import com.raja.anucarita.SeCustomUtils;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;

public class SeCustomActions
{
	public static void main(String args[])
	{
	}
	public static String locator;
	public static String text;
	public static WebElement element;
	public static List<WebElement> elements;
	public static boolean result;
	public static Select dropDown;
	public static Properties values;
	
	public static void seClick(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		element = SeCustomUtils.elementReturn(driver,locator);
		element.click();
	}
	
	public static void sePresentElementClick(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
	
		element=null;
		if(locator.contains("jquery") || locator.contains("jQuery")) 
		{
			element=SeCustomUtils.elementReturn(driver,locator);
		}
		else
		{
			try
			{
				elements=SeCustomUtils.elementsReturn(driver,locator);

				if(!elements.isEmpty())
				{
					element=elements.get(0);
				}
			}
			catch(Exception e)
			{
			}
		}
		if(element instanceof WebElement)
		{
			element.click();
		}
	}
	
	public static void fireJS(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);

		String actualLocator=locator.split("=",2)[1];
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(actualLocator);
	}
	
	public static void seMouseOver(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		element = SeCustomUtils.elementReturn(driver,locator);
		if (element instanceof WebElement)
		{				
			new Actions(driver).moveToElement(element).perform(); 
		}
	}
	
	public static boolean seIsElementPresent(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		result=false;
		try
		{
			element=SeCustomUtils.elementReturn(driver,locator);
			if(element instanceof WebElement)
			{
				result=true;
			}
		}
		catch(Exception e)
		{
		}
		//testCaseResult(result,"","","",""+values.getProperty("Currentelement")+" element Presence in the UI is "+result.toString()+"");
		
		return result;
	}
	
	public static boolean seIsElementReversePresent(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		result=false;
		element=SeCustomUtils.elementReturn(driver,locator);
		try
		{
			element=SeCustomUtils.elementReturn(driver,locator);
			if(element instanceof WebElement)
			{
				result=true;
			}
		}
		catch(Exception e)
		{
		}
		
		result=!result;
		
		//testCaseResult(result,"","","",""+values.getProperty("Currentelement")+" element Presence in the UI is "+result.toString()+"");
		
		return result;
	}
	
	public static void seRightClick(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=values.getProperty(locator);
		element = SeCustomUtils.elementReturn(driver,locator);
		Actions action= new Actions(driver);
		action.contextClick(element).build().perform();
	}
	
	public static void seDragAndDrop(WebDriver driver,String LeftElementLocator,String LeftElementReplace,String RightElementLocator,String RightElementReplace) throws Exception
	{
		String LeftLocator=SeCustomUtils.returnLocator(LeftElementLocator,LeftElementReplace);
		String RightLocator=SeCustomUtils.returnLocator(RightElementLocator,RightElementReplace);
		
		WebElement LeftElement = SeCustomUtils.elementReturn(driver,LeftLocator);
		WebElement RightElement = SeCustomUtils.elementReturn(driver,RightLocator);
		
		if (LeftElement instanceof WebElement && RightElement instanceof WebElement)
		{
			Actions builder = new Actions(driver);
			Action dragAndDrop = builder.dragAndDrop(LeftElement,RightElement).build();
			dragAndDrop.perform();
		}
	}
	
	public static void seClickChecked(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		
		element = SeCustomUtils.elementReturn(driver,locator);
		if(!element.getAttribute("checked").equalsIgnoreCase("checked"))
		{
			element.click();
		}
	}
	
	public static void seClickNotChecked(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);

		element = SeCustomUtils.elementReturn(driver,locator);
		if(element.getAttribute("checked").equalsIgnoreCase("checked"))
		{
			element.click();
		}
	}
	
	public static void seType(WebDriver driver,String locator,String textToBeTyped,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		element = SeCustomUtils.elementReturn(driver,locator);
		if(element.isDisplayed())
		{
			element.clear();
			element.sendKeys(textToBeTyped);
		}
	}
	
	public static void seTypeAndSubmit(WebDriver driver,String locator,String textToBeTyped,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		element = SeCustomUtils.elementReturn(driver,locator);
		if(element.isDisplayed())
		{
			element.clear();
			element.sendKeys(textToBeTyped);
			element.submit();
		}
	}
	
	public static void sePresentElementType(WebDriver driver,String locator,String textToBeTyped,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		element=null;
		if(locator.contains("jquery") || locator.contains("jQuery")) 
		{
			element=SeCustomUtils.elementReturn(driver,locator);
		}
		else
		{
			try
			{
				elements=SeCustomUtils.elementsReturn(driver,locator);
				if(!elements.isEmpty())
				{
					element=elements.get(0);
				}
			}
			catch(Exception e)
			{
			}
		}

		if(element instanceof WebElement)
		{
			if(element.isDisplayed())
			{
				element.clear();
				element.sendKeys(textToBeTyped);
			}
		}
	}

	public static boolean seCheckValue(WebDriver driver,String locator,String valueToCheck,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		element = SeCustomUtils.elementReturn(driver,locator);
		text=element.getText();
		result=false;
		if(text.equals(valueToCheck))
		{
			result=true;
		}
		//testCaseResult(true,"","","","Text for the field "+values.getProperty("Currentelement")+" is "+valueToCheck+" is present");
		return result;
	}
	
	public static String seGetValue(WebDriver driver,String locator,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		
		element = SeCustomUtils.elementReturn(driver,locator);
		text=element.getText();
		return text;
	}
	
	public static void seSelect(WebDriver driver,String locator,String selectbyMethod,String Value,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		
		result=false;
		element = SeCustomUtils.elementReturn(driver,locator);
		dropDown = new Select(element);
		elements=dropDown.getOptions();
		
		if(selectbyMethod.equalsIgnoreCase("visibleText") || selectbyMethod.equalsIgnoreCase("default"))
		{
			dropDown.selectByVisibleText(Value);
		}
		else if(selectbyMethod.equalsIgnoreCase("Value"))
		{
			dropDown.selectByValue(Value);
		}
		else if(selectbyMethod.equalsIgnoreCase("Index"))
		{
			dropDown.selectByIndex(Integer.parseInt(Value));
		}
		else
		{
			throw new Exception("seSelect select by method is not correct");
		}
	}
	
	public static void sePresentElementSelect(WebDriver driver,String locator,String selectbyMethod,String Value,String replace) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);
		result=false;
		dropDown=null;
		if(locator.contains("jquery") || locator.contains("jQuery")) 
		{
			element=SeCustomUtils.elementReturn(driver,locator);
			if(element instanceof WebElement)
			{
				dropDown = new Select(element);
			}
		}
		else
		{
			elements = SeCustomUtils.elementsReturn(driver,locator);
			if(!elements.isEmpty() && elements.get(0).isDisplayed())
			{
				dropDown = new Select(elements.get(0));
			}
		}
		if (dropDown instanceof Select)
		{
			seSelect(driver,locator,selectbyMethod,Value,replace);
		}
	}
	//Need to write seGetAllValues
	
	public static boolean seIsValuePresent(WebDriver driver, String locator, String replace,String valueToCheck) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);

		element = SeCustomUtils.elementReturn(driver,locator);
		dropDown = new Select(element);
		List<WebElement> getOptions = dropDown.getAllSelectedOptions();
		for (WebElement getOption : getOptions)
		{
			if(getOption.getText().equals(valueToCheck))
			{
				result=true;
				break;
			}
			else
			{
				result=false;
			}
		}
		//testCaseResult(result,"","","",""+values.getProperty("Currentelement")+" element Presence in the UI is "+result.toString()+"");
		return result;
	}
		
	public static boolean seGetValueSelected(WebDriver driver, String locator,String replace,String valueToCheck) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);

		element = SeCustomUtils.elementReturn(driver,locator);
		dropDown = new Select(element);

		text=dropDown.getFirstSelectedOption().getText();
		result=false;
		if(text.equals(valueToCheck))
		{
			result=true;
		}
		return result;
	}
	
	public static boolean seIsValueSelected(WebDriver driver, String locator,String replace,String valueToCheck) throws Exception
	{
		locator=SeCustomUtils.returnLocator(locator,replace);

		element = SeCustomUtils.elementReturn(driver,locator);
		dropDown = new Select(element);

		text=dropDown.getFirstSelectedOption().getText();
		result=false;
		if (text.equals(valueToCheck))
		{
			result=true;
		}
		return result;
	}
	
	public static boolean seIsValueNotSelected(WebDriver driver, String locator,String replace,String valueToCheck) throws Exception
	{
		result=seIsValueSelected(driver,locator,replace,valueToCheck);
		result=!result
		return result
	}
	
	public static String seAlert(WebDriver driver,String alertMessage,String actionNeeded) throws Exception
	{
		try
		{
			SeCustomUtils.seTakeAlertScreenShot(driver);
			
			Alert alert = driver.switchTo().alert();
			String getAlertMessage=alert.getText();
			
			//testCaseResult(true,"","","","Alert for "+alertMessage+" is present");
			
			
			SeCustomUtils.seTakeAlertScreenShot(driver);

			if(actionNeeded.equalsIgnoreCase("accept"))
			{
				alert.accept();
			}
			else if(actionNeeded.equalsIgnoreCase("dismiss") || actionNeeded.equalsIgnoreCase("cancel"))
			{
				alert.dismiss();
			}
			result=false;
			if(getAlertMessage.equalsIgnoreCase(alertMessage))
			{
				result=true;
			}
			//testCaseResult(result,"","","","alertMessage from UI is "+getAlertMessage+" and alert message is "+alertMessage+" ");
			
			SeCustomUtils.seTakeAlertScreenShot(driver);
		}
		catch(Exception e)
		{
			//testCaseResult(false,"","","","Alert for "+alertMessage+" is not present");
			SeCustomUtils.seTakeAlertScreenShot(driver);
		}
		return alertMessage;
	}
	
	public static void seChangeWindow(WebDriver driver,String windowName) throws Exception
	{
		SeCustomUtils.seTakeScreenShot(driver);
		driver.switchTo().window(windowName);
		SeCustomUtils.seTakeScreenShot(driver);
	}

	public static void seChangeIFrame(WebDriver driver,String locator) throws Exception
	{
		SeCustomUtils.seTakeScreenShot(driver);		
		locator=values.getProperty(locator);
		element = SeCustomUtils.elementReturn(driver,locator);
		driver.switchTo().frame(element);		
		SeCustomUtils.seTakeScreenShot(driver);
	}
	
	
	public static void seChangeMainWindow(WebDriver driver) throws Exception
	{
		SeCustomUtils.seTakeScreenShot(driver);
		//for this take the first window handle and change the window
		//for iframe to main window change using defaultcontent option
		//driver.switchTo().defaultContent();
		//driver.switchTo().window(getProperty("mainWinddowHandle"));
		SeCustomUtils.seTakeScreenShot(driver);
	}
	
	public static boolean seIsTextPresent(WebDriver driver,String TextToCheck) throws Exception
	{
		String TextFromSite=driver.getPageSource();
		if(TextFromSite.contains(TextToCheck))
		{
			result=true;
		}
		else
		{
			result=false;
		}
		//testCaseResult(result,"","",""," Text "+TextToCheck+" Presence Check");
		return result;
	}
}