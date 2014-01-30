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

class DSLConverterFunctions():
	def __init__(self):
		self=self
	def replace_amp_strings(self,content_string):
		ampreplace={'&quot;':'"','&gt;':'>','&lt;':'<','&rdquo;':'"','&ldquo;':'"','&nbsp;':' ','&amp;':'&','&rsquo;':'\'','&lsquo;':'\''}
		if content_string.find('&')!=-1:
			for key,value in ampreplace.iteritems():
				if content_string.find(key)!=-1:
					content_string=content_string.replace(key,value)
		return content_string
	def alert_message_rule(self,text_list):
		variable_list=[]
		'''
		AlertMessage accept message Are you sure?
		AlertMessage cancel message Are you sure?
		AlertMessage error message Are you sure?
		
		AlertMessage accept variable message ThisVariable
		
		GetAlertMessage variableName accept message Are you sure?
		GetAlertMessage variableName accept variable message Are you sure?
		
		seAlert(WebDriver driver,String alertMessage,String actionNeeded)
		'''
		action='accept'
		if text_list[0]=='AlertMessage' and text_list[1] in ['error','cancel']:
				action='dismiss'
				
		if text_list[0]=='GetAlertMessage' and text_list[2] in ['error','cancel']:
				action='dismiss'
		
		message_index=text_list.index('message')		
		
		alert_text=' '.join(text_list[message_index+1:])
		java_text='\t\tSeCustomActions.seAlert(driver,"'+alert_text+'","'+action+'");\n'
		
		if text_list[message_index-1]=='variable':
			alert_text=' '.join(text_list[message_index+2:])
			java_text='\t\tSeCustomActions.seAlert(driver,'+alert_text+',"'+action+'");\n'
			
		if text_list[0]=='GetMessage':
			java_text='\t\t'+text_list[1]+'=SeCustomActions.seAlert(driver,'+alert_text+',"'+action+'");\n'	
			variable_list.append(text_list[1])

		return java_text,variable_list
	def change_window_frame_rule(self,text_list):
		variable_list=[]
		'''
		Change focus to WindowName
		Change focus to MainWindow
		Change focus to iframe iframeName
		
		seChangeWindow(WebDriver driver,String windowName)
		
		'''
		to_index=text_list.index('to')
		window_text=' '.join(text_list[to_index+1:])
		if text_list[to_index+1]=='iframe':
			window_text=' '.join(text_list[to_index+2:])
		java_text='\t\tSeCustomActions.seChangeWindow('+window_text+');\n'

		return java_text,window_text,variable_list
		
	def check_text_present(self,text_list):
		variable_list=[]
		'''
		Check text sometext
		FCheck text sometext
		Check text variable sometext
		
		seIsTextPresent(WebDriver driver,String TextToCheck)
		'''
		text_index=text_list.index('text')
		text=' '.join(text_list[text_index+1:])
		java_text='\t\tSeCustomActions.seIsTextPresent(driver,"'+text+'");\n'
		if text_list[text_index+1]=='variable':
			java_text='\t\tSeCustomActions.seIsTextPresent(driver,'+text+');\n'

		return java_text,variable_list
	def drag_and_drop_rule(self,text_list):
		variable_list=[]
		locator_list=[]
		'''
		Drag thiselement dropto tothiselement
		seDragAndDrop(WebDriver driver,String LeftElementLocator,String LeftElementReplace,String RightElementLocator,String RightElementReplace)
		'''

		drag_index=text_list.index('drop')
		drag_element='.'.join(text_index[0:drag_index-1])
		drop_element='.'.join(text_index[drag_index+1:])
		
		java_text='\t\tSeCustomActions.seDragAndDrop(driver,"'+drag_element+'","","'+drop_element+'","");\n'
		
		locator_list.append(drag_element)
		locator_list.append(drop_element)
		
		return java_text,locator_list,variable_list
	
	def call_function_rule(self,text_list):
		java_list=[]
		variable_list=[]
		locator_list=[]
		'''
		Call function 
		Call function returnVariable 
		Call function with arguments Arugment1,Arugment2
		'''
		return java_list,locator_list,variable_list