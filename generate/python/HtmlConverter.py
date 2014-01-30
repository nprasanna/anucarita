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

from BeautifulSoup import BeautifulSoup
import urllib2
import sys
from DSLConverter import DSLConverter
from JavaDefinitions import JavaDefinitions
from ModuleSpecificFunctions import ModuleSpecificFunctions

"""
This file takes the Html file/files as input and parses the text in the columns. Based the settings given the columns are parsed for Serial number, case description, action, tags, etc
"""

class HtmlConverter:
	def __init__(self):
		self=self
	def convert_html(self,file_path,file_name):

		line_text_list=[]
		level_text_list=[]
		td_dict={}
		tr_dict={}
		
		data=urllib2.urlopen(file_path)
		soup = BeautifulSoup(data)
		tables =soup.findAll("table")
		
		"""
		This part takes on the html colums get the converted to text with linked to row and column ids. Get the needed column that can be processed in the DSL converter class. 
		"""
		for table in tables:
			trcount=0
			for tr in table.findAll('tr'):
				tdcount=0
				for td in tr.findAll('td'):
					for line in td.findAll('p'):
						line_text=line(text=True)
						if line_text!=None and line_text!='' and len(line_text)>0:
							for i in range(0,len(line_text)):
								if i is not None:
									line_text[i]=line_text[i].replace('\n','').replace('\t','').replace('\r','')
							line_text=filter(None,line_text)
						if line_text is not None and line_text!=' ' and line_text!='' and len(line_text)>0:
								line_text_list.append(line_text[0])
					td_dict['col_'+str(tdcount)]=line_text_list
					line_text_list=[]
					tdcount=tdcount+1
				tr_dict['row_'+str(trcount)]=td_dict
				td_dict={}
				trcount=trcount+1

		"""
		define the columns naming conventions otherwise default relationship will be taken 
		Serial Number = col_0
		Description = col_1
		Actions = col_2
		Severity = col_3
		Remarks = col_4
		"""
		del tr_dict['row_0']
		
		final_java_list=[]
		final_locator_list=[]
		final_variable_list=[]
		
		for row_number,content in tr_dict.iteritems():
			java_list,locator_list,variable_list=DSLConverter().generate_code(content['col_0'],content['col_2'],file_name,content['col_3'])
			final_java_list.extend(java_list)
			final_locator_list.extend(locator_list)
			final_variable_list.extend(variable_list)

		return final_java_list,final_locator_list,final_variable_list
		
	def generate_files(self,file_path,file_name):
		final_java_list=[]
		
		java_list,locator_list,variable_list=HtmlConverter().convert_html(file_path,file_name)
		
		import_list=JavaDefinitions().import_list(file_name)
		final_java_list.extend(import_list)
		
		variable_file_list=JavaDefinitions().variable_list()
		final_java_list.extend(variable_file_list)

		for variable in variable_list:
			final_java_list.extend('\tpublic static String '+variable+';\n')

		main_function=JavaDefinitions().main_function(file_name)
		final_java_list.extend(main_function)
		
		final_java_list.extend(java_list)
		
		module_function=ModuleSpecificFunctions().module_function(file_name)
		final_java_list.extend(module_function)
			
		closing_list=JavaDefinitions().closing_list()
		final_java_list.extend(closing_list)
		
		javafile=open(file_name+'.java','w')
		javafile.write(''.join(final_java_list))
		javafile.close()
		
		locator_file=open(file_name+'.properties','w')
		locator_file.write(''.join(final_java_list))
		locator_file.close()
#
if len(sys.argv)>1:
	HtmlConverter().generate_files('file:///'+sys.argv[1]+'',sys.argv[2])
else:
	print " Usage:"
	print " HtmlConverter.py full_file_path java_file_name_you_want"