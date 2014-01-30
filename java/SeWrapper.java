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
import com.raja.anucarita.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.Properties;


import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;
public class SeWrapper
{
	public static void main(String args[])
	{
	}
	
	public static String Browser;
	public static WebDriver driver;
	public static Alert alert;
	public static Properties values;
	
	public static WebDriver getDriver() throws Exception
	{
		if(Browser.equals("Firefox"))
		{
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("capability.policy.default.Window.QueryInterface", "allAccess");         
			profile.setPreference("capability.policy.default.Window.frameElement.get", "allAccess");
			driver = new FirefoxDriver(profile);
		}
		else if(Browser.equals("IE"))
		{
			driver = new InternetExplorerDriver();
		}
		else if(Browser.equals("GoogleChrome"))
		{
			System.setProperty("webdriver.chrome.driver", values.getProperty("GoogleChromeLocation"));
			driver = new ChromeDriver();
		}
		else
		{
			throw new Exception("Browser String is wrong");
		}
		return driver;
	}
	public static void invokeMethods(WebDriver driver,Class<?> c) throws Exception
	{
		Object t = c.newInstance();
		Method m[] = c.getDeclaredMethods();
				
		for(Method name:m)
		{
			String methodname=name.getName();
			try
			{
				name.invoke(t,driver);
				
				SeCustomUtils.seTakeAlertScreenShot(driver);
			}
			catch(Exception e)
			{
				SeCustomUtils.seTakeAlertScreenShot(driver);
			}
			finally
			{
				try
				{
					SeCustomUtils.seTakeAlertScreenShot(driver);
					
					alert = driver.switchTo().alert();
					String getAlertMessage=alert.getText().replaceAll("\\n","");
					if(!(getAlertMessage.equals(null)) || !(getAlertMessage.equals("")))
					{
					Utils.testCaseResult(false,"","","","There is and unexpected alert. Alert message is "+getAlertMessage+"");
					}
					
					SeCustomUtils.seTakeAlertScreenShot(driver);					
				}
				catch(Exception e)
				{
					SeCustomUtils.seTakeAlertScreenShot(driver);
				}
			}
		}
	}
}