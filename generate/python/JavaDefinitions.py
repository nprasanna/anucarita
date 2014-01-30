'''
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
'''

class JavaDefinitions:
	def __init__(self):
		self=self
	def import_list(self,java_file_name):
		import_list=[]
		import_list.append('package com.raja.anucarita;\n\n')
		import_list.append('import org.openqa.selenium.WebDriver;\n')
		import_list.append('import java.io.File;\n')
		import_list.append('import com.raja.anucarita.SeWrapper;\n')
		import_list.append('import com.raja.anucarita.SeCustomUtils;\n')
		import_list.append('import com.raja.anucarita.Utils;\n')
		import_list.append('import com.raja.anucarita.SeCustomActions;\n')
		import_list.append('import java.util.concurrent.TimeUnit;\n')
		import_list.append('import java.util.Properties;\n')
		import_list.append('public class '+java_file_name+'\n')
		import_list.append('{\n')
		return import_list
	def variable_list(self):
		variable_list=[]
		variable_list.append('\tpublic static String mainWinddowHandle=null;\n')
		variable_list.append('\tpublic static WebDriver driver;\n')
		variable_list.append('\tpublic static Properties values;\n')
		variable_list.append('\tpublic static Properties locatorfile;\n')
		return variable_list
	def main_function(self,java_file_name):
		main_function=[]
		main_function.append('\tpublic static void main(String args[]) throws Exception\n')
		main_function.append('\t{\n')
		main_function.append('\t\tvalues=Utils.loadLocatorInfo(System.getProperty("confFolder")+File.separator+"config.properties");\n')
		main_function.append('\t\tlocatorfile=Utils.loadLocatorInfo(System.getProperty("confFolder")+File.separator+"'+java_file_name+'.properties");\n')
		main_function.append('\t\tdriver=SeWrapper.getDriver();\n')
		main_function.append('\t\tdriver.manage().timeouts().implicitlyWait(Integer.parseInt(values.getProperty("Timeout")), TimeUnit.SECONDS);\n')
		main_function.append('\t\tdriver.manage().window().maximize();\n')
		main_function.append('\t\tmainWinddowHandle=driver.getWindowHandle();\n')
		main_function.append('\t\tClass<?> c = Class.forName("com.raja.anucarita.'+java_file_name+'");\n')
		main_function.append('\t\tSeWrapper.invokeMethods(driver,c);\n')
		main_function.append('\t}\n')
		return main_function
	def closing_list(self):
		closing_list=[]
		closing_list.append('\tpublic void Method_Close(WebDriver driver) throws Exception\n')
		closing_list.append('\t{\n')
		closing_list.append('\t\tdriver.quit();\n')
		closing_list.append('\t}\n')		
		closing_list.append('}\n')
		return closing_list