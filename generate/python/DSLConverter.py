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

from DSLConverterFunctions import DSLConverterFunctions
from DSLClickRule import DSLClickRule
from DSLTypeRule import DSLTypeRule
from DSLSelectRule import DSLSelectRule

class DSLConverter():
	def __init__(self):
		self=self		
	def generate_code(self,testcase_id,actions_list,file_name,severity=''):
	
		final_java_list=[]
		final_locator_list=[]
		final_variable_list=[]
		
		final_java_list.append('\tpublic void Method_'+str(''.join(testcase_id))+'(WebDriver driver) throws Exception\n')
		final_java_list.append('\t{\n')
			
		for i in range(0,len(actions_list)):

			text=DSLConverterFunctions().replace_amp_strings(actions_list[i])
			text_list=(text.replace("\t"," ")).split(' ')
			
			if text_list[-1] in ['link','button','element','checkbox']:
				java_text,locator,variable_list=DSLClickRule().click_rule(text_list)
			elif text_list[-1] in ['field']:
				java_text,locator,variable_list=DSLTypeRule().type_rule(text_list)
			elif text_list[-1]=='list':
				java_text,locator,variable_list=DSLSelectRule().select_rule(text_list)
			elif text_list[0] in ['AlertMessage','GetMessage']:
				java_text,variable_list=DSLConverterFunctions().alert_message_rule(text_list)
			elif text_list[0]=='Change':
				java_text,locator,variable_list=DSLConverterFunctions().change_window_frame_rule(text_list)
			elif text_list[0]=='Call':
				java_text,locator,variable_list=DSLConverterFunctions().call_function_rule(text_list)	
			elif text_list[0] in ['Check','FCheck']:
				java_text,variable_list=DSLConverterFunctions().check_text_present(text_list)
			elif text_list[0]=='Drag':
				java_text,locator,variable_list=DSLConverterFunctions().drag_and_drop_rule(text_list)	
				
			final_java_list.extend(java_text)
			final_locator_list.extend(locator)
			final_variable_list.extend(variable_list)
		final_java_list.append('\t}\n')
			
		return final_java_list,final_locator_list,final_variable_list