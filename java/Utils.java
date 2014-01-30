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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.io.FileInputStream;

public class Utils
{
	public static void main(String args[])
	{
	}
	
	public static Properties values;
	public static Properties locatorfile;
	
	public static void writeToFile(String fileName,boolean append,String value)
	{
		BufferedWriter bw = null;
        try 
        {       if(append)
				{
					bw = new BufferedWriter(new FileWriter(fileName, true));                                                   
				}
				else
				{
					File f = new File(fileName);
					f.delete();
					bw = new BufferedWriter(new FileWriter(new File(fileName), true));
				}
                StringBuffer sb=new StringBuffer();
				sb.append(value);                                                                
				bw.write(sb.toString().trim());
				bw.newLine();		
                bw.flush();
                bw.close();
        } 
        catch (Exception ex) 
        {
            System.err.println("Exception occurred :"+ex.getMessage());
            ex.printStackTrace();            
        }                 
	}
	
	public static String readFromFile(String filename)
	{
		String s = null ;
		StringBuilder content = new StringBuilder();
		try {
			BufferedReader input =  new BufferedReader(new FileReader(filename));
			try {
					while ((s=input.readLine()) != null)
					{
						content.append(s);
						content.append(System.getProperty("line.separator"));
					}	
				}
			finally {
					input.close();
					}
			}		
		catch (IOException ex){
			ex.printStackTrace();
			}	
		return content.toString();
	}
	
	public static String createFolder(String foldername)
	{	
		File folder = new File(foldername);
		String s = null;
		if (!folder.exists())
		{
			try 
			{
				if (folder.mkdirs())
					s = "created";
				else
					s = "not created";
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}	
		}
		return s;	
	}
	
	public static String generateTimeValue() throws Exception
	{
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		long milli = now.getTimeInMillis();
		return ""+year+"_"+month+"_"+day+"_"+hour+"_"+min+"_"+sec+"_"+milli+"";
	}
	
	public static Properties loadLocatorInfo(String fileName) throws Exception
	{
		FileInputStream filecontent = new FileInputStream( new File(fileName) );
		Properties locatorfile=new Properties();
		locatorfile.load(filecontent);
		return locatorfile;
	}
	
	public static void testCaseResult(boolean R,String ReportName,String TestCaseName,String Testcaseid,String Remarks) throws Exception
	{	
		String ReportFileLocation=values.getProperty("Reports")+File.separator+"report_"+values.getProperty("DateName")+".csv";
		String Description=values.getProperty("Description");
		String Severity=values.getProperty("Severity");
		String Status=values.getProperty("Status");
		
		if(Status == null)
		{
			Status="NONE";
		}
		if(Status.equalsIgnoreCase("Exception"))
		{
			Status="Exception";
		}
		else
		{
			Status="Failed";
			if(R)
			{
				Status="Passed";
			}
		}
		writeToFile(ReportFileLocation,true,""+TestCaseName+"; "+Testcaseid+"; "+Severity+"; "+Status+"; "+Description+"; "+Remarks+"");
	}
}