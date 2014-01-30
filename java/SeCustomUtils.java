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

import java.io.File;
import java.util.Properties;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.interactions.Actions;

public class SeCustomUtils
{
	public static void main(String args[])
	{
	
	}
	public static String byMethod;
	public static String actualLocator;
	public static boolean Result;
	public static Properties values;
	public static Properties locatorfile;
	public static WebElement element;
	public static List<WebElement> elements;
	public static WebDriverWait wait;
	
	public static WebElement elementReturn(WebDriver driver, String locator) throws Exception
	{
		byMethod=locator.split("=",2)[0];
		actualLocator=locator.split("=",2)[1];
	
		if(byMethod.equalsIgnoreCase("css"))
		{
			element = driver.findElement(By.cssSelector(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("jQuery"))
		{
			final String LocatorTwo=actualLocator;
			String Timeout=values.getProperty("timeout");
			try
			{
				wait = new WebDriverWait(driver, Integer.parseInt(Timeout));
				wait.until(new ExpectedCondition<Boolean>()
				{
					public Boolean apply(WebDriver driver)
					{
						Result=false;
						try
						{
							element = findElementByjQuery(driver,LocatorTwo);
							if(element instanceof WebElement)
							{
								Result=true;
							}
							else
							{
								Result=false;
							}
						}
						catch(Exception e)
						{
						}
						return Result;
					}
				});
			}
			catch(Exception e)
			{
			}
			element = findElementByjQuery(driver,actualLocator);
		}
		else if(byMethod.equalsIgnoreCase("linkText"))
		{
			element = driver.findElement(By.linkText(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("id"))
		{
			element = driver.findElement(By.id(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("name"))
		{
			element = driver.findElement(By.name(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("ByIDorName"))
		{
			driver.findElement(new ByIdOrName(actualLocator));			
		}
		else if(byMethod.equalsIgnoreCase("partialLinkText"))
		{
			element = driver.findElement(By.partialLinkText(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("xpath"))
		{
			element = driver.findElement(By.xpath(actualLocator));
		}
		else
		{
		}
		if (element instanceof WebElement)
		{
			return element;
		}
		else
		{
			element=null;
		}
		return null;
	}
	
	public static String returnLocator(String locator,String replace) throws Exception
	{
		locator=locatorfile.getProperty(locator);
		if (!(replace.equals("")))
		{
			locator=locator.replace("INDEX",replace);
		}
		return locator;
	}
	
	public static void seWaitForElement(WebDriver driver,String locator, String replace, String waitTimeOut) throws Exception
	{
		locator=returnLocator(locator,replace);
		
		final String LocatorOne=locator;
		try
		{
			wait = new WebDriverWait(driver, Integer.parseInt(waitTimeOut));
			Result=false;		
			wait.until(new ExpectedCondition<Boolean>()
			{
				public Boolean apply(WebDriver driver)
				{
					Result=false;
					try
					{
						element = elementReturn(driver,LocatorOne);
						if(element instanceof WebElement && element.isDisplayed())
						{
							Result=true;
						}
						else
						{
							Result=false;
						}
					}
					catch(Exception e)
					{
					}
					return Result;
				}
			});
		}
		catch(Exception e)
		{
		}	
	}
	
	public static WebElement findElementByjQuery(WebDriver driver,String locator) throws Exception
	{
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			locator = "return jQuery("+locator+")[0]";

			Result = checkAndLoadJS(driver);
			if(Result)
			{
				element=(WebElement)js.executeScript(locator);
				if (element instanceof WebElement)
				{
					return element;
				}
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	public static List<WebElement> elementsReturn(WebDriver driver, String locator) throws Exception
	{
		byMethod=locator.split("=",2)[0];
		actualLocator=locator.split("=",2)[1];
		
		if(byMethod.equalsIgnoreCase("css"))
		{
			elements = driver.findElements(By.cssSelector(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("jQuery"))
		{
			/*
			Need to write code for iterating multiple elements with jQuery
			*/
			String Timeout=values.getProperty("timeout");
			
			final String LocatorTwo=actualLocator;
			try
			{
				wait = new WebDriverWait(driver, Integer.parseInt(Timeout));
				wait.until(new ExpectedCondition<Boolean>()
				{
					public Boolean apply(WebDriver driver)
					{
						Result=false;
						try
						{
							element = findElementByjQuery(driver,LocatorTwo);
							if(element instanceof WebElement)
							{
								Result=true;
							}
							else
							{
								Result=false;
							}
						}
						catch(Exception e)
						{
						}
						return Result;
					}
				});
			}
			catch(Exception e)
			{
			}
			
			element = findElementByjQuery(driver,actualLocator);
			elements.add(element);
			/**/
		}
		else if(byMethod.equalsIgnoreCase("linkText"))
		{
			elements = driver.findElements(By.linkText(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("id"))
		{
			elements = driver.findElements(By.id(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("name"))
		{
			elements = driver.findElements(By.name(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("ByIDorName"))
		{
			driver.findElements(new ByIdOrName(actualLocator));			
		}
		else if(byMethod.equalsIgnoreCase("partialLinkText"))
		{
			elements = driver.findElements(By.partialLinkText(actualLocator));
		}
		else if(byMethod.equalsIgnoreCase("xpath"))
		{
			elements = driver.findElements(By.xpath(actualLocator));
		}
		else
		{
		}
		return elements;
	}
	
	public static Boolean checkAndLoadJS(WebDriver driver) throws Exception
	{
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			
			Result=false;
			Result = (Boolean) js.executeScript("return typeof jQuery != 'undefined'");
			if(!Result)
			{
				Result=injectjQuery(driver);
				if(!Result)
				{
					return Result;
				}
			}
			else
			{
				return Result;
			}
		}
		catch(Exception e)
		{
		}
		return Result;
	}
	
	
	public static Boolean injectjQuery(WebDriver driver) throws Exception
	{		
		Result=false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		String Host=values.getProperty("host");

		js.executeScript(
		"var hId = document.getElementsByTagName(\"head\")[0];"+
		"var nSciprt = document.createElement('script');"+
		"nSciprt.type='text/javascript';"+
		"nSciprt.src='https://ajax.googleapis.com/ajax/libs/jquery/1.6.3/jquery.min.js';"+
		"hId.appendChild(nSciprt);"+
		"hId.appendChild(nSciprt);"+
		"return typeof jQuery!='undefined';"
		);
		
		if (values.getProperty("Browser").equals("IE"))
		{
			int count = 0;
			while(!((Boolean)js.executeScript("return typeof jQuery!='undefined';")))
			{
				Thread.sleep(100);
				count = count+1;
				if(count==100)
				{
					break;
				}
			}
		}
		
		Result = (Boolean) js.executeScript(
		"if(typeof jQuery!='undefined')"+
		"{"+
			"var Result=true;"+
		"}"+
		"else"+
		"{"+
			"var Result=false;"+
		"}"+
		"return Result;"
		);
		return Result;
	}
		
	public static void seTakeScreenShot(WebDriver driver) throws Exception
	{
		try
		{
			File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String name=null;	
			FileUtils.copyFile(screenShot, new File(name));
			
		}
		catch(Exception e)
		{
			seTakeAlertScreenShot(driver);
		}
		
	}
	
	public static void seTakeAlertScreenShot(WebDriver driver) throws Exception
	{
		//to do 
	}
}