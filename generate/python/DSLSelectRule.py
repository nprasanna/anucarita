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

class DSLSelectRule():
	def select_rule(self,text_list):
		variable_list=[]
		'''
		Select ThisValue in ThisList list
		Select ThisValue in Index 0 ins ThisList list
		Select ThisValue in Index ReplaceValue ThisReplace ins ThisList list
		Select ThisValue in Index ReplaceVariable ThisReplace ins ThisList list
		Select ThisValue in ReplaceValue ThisReplace ins ThisList list
		Select ThisValue in ReplaceVariable ThisReplace ins ThisList list
		Select ById ThisID in ThisList list
		
		Select Variable ThisVariable in ThisList list
		Select Variable ThisVariable in Index 0 ins ThisList list
		Select Variable ThisVariable in Index ReplaceValue ThisReplace ins ThisList list
		Select Variable ThisVariable in Index ReplaceVariable ThisReplace ins ThisList list
		Select Variable ThisVariable in ReplaceValue ThisReplace ins ThisList list
		Select Variable ThisVariable in ReplaceVariable ThisReplace ins ThisList list
		Select Variable ById ThisID in ThisList list
		Select Variable ByValue ThisValue in ThisList list
		Select Variable ByLabel ThisLabel in ThisList list
		
		Select Index 0 in ThisList list
		
		CheckValue ThisValue in ThisList list
		CheckIsSelected ThisValue1 in ThisList list
		CheckIsNotSelected ThisValue1  in ThisList list
		GetSelected SelectedValue in ThisList list
		GetAllValues AllValue in ThisList list
		
		CheckList ThisList list
		Check ThisValue1,ThisValue2 in ThisList list
		
		Select can be replaced with Select,SelectPresent,CheckList,FCheckList,CheckValue,CheckIsSelected,CheckIsNotSelected,GetSelected,GetAllSelected
		'''
		select_replace={'Select':'seSelect',#seSelect(WebDriver driver,String locator,String selectbyMethod,String Value,String replace)
		'SelectPresent':'sePresentElementSelect',#sePresentElementSelect(WebDriver driver,String locator,String selectbyMethod,String Value,String replace)
		'CheckList':'seIsElementPresent',#seIsElementPresent(WebDriver driver,String locator,String replace)
		'FCheckList':'seIsElementReversePresent',#seIsElementReversePresent(WebDriver driver,String locator,String replace)
		'CheckValue':'seIsValuePresent',#seIsValuePresent(WebDriver driver, String locator, String replace,String valueToCheck)
		'CheckIsSelected':'seIsValueSelected',#seIsValueSelected(WebDriver driver, String locator,String replace,String valueToCheck)
		'CheckIsNotSelected':'seIsValueNotSelected',#seIsValueNotSelected(WebDriver driver, String locator,String replace,String valueToCheck)
		'GetSelected':'seGetValueSelected',#seGetValueSelected(WebDriver driver, String locator,String replace)
		'GetAllSelected':'seGetAllValues'#Need to write
		}
		
		select_action=select_replace[text_list[0]]
	
		select_value_index=1
		select_by_method='default'
		
		if text_list[1] in ['Variable','Index','ById','ByLabel','ByValue']:
			select_value_index=2
			if text_list[2] in ['ById','ByLabel','ByValue']:
				select_value_index=3
			if text_list[1] in ['Index','ById','ByLabel','ByValue']:
				select_by_method=text_list[1]
			if text_list[2] in ['ById','ByLabel','ByValue']:
				select_by_method=text_list[2]
				
		if 'in' in text_list:
			in_index=text_list.index('in')
			locator='.'.join(text_list[in_index+1:])
		else:
			locator='.'.join(text_list[1:])
			
		if 'Index' in text_list and text_list[in_index+1]=='Index':
			index_index=text_list.index('Index',in_index)
		
		replace_text=''	

		if 'ins' in text_list:
			ins_index=text_list.index('ins')
			locator='.'.join(text_list[ins_index+1:])
			if text_list[in_index+1] in ['Index','ReplaceValue','ReplaceVariable']:
				if text_list[in_index+1] in ['ReplaceValue','ReplaceVariable']:
					replace_text=' '.join(text_list[in_index+2:ins_index])
				elif text_list[in_index+2] in ['ReplaceValue','ReplaceVariable']:
					replace_text=' '.join(text_list[index_index+2:ins_index])
				else:
					replace_text=' '.join(text_list[index_index+1:ins_index])

		if 'ReplaceVariable' not in text_list:
			replace_text='"'+replace_text+'"'
		else:
			variable_list.append(replace_text)
			
		select_value=' '.join(text_list[select_value_index:in_index])
		if text_list[1]!='Variable':
			select_value='"'+select_value+'"'
		else:
			variable_list.append(select_value)
		
		java_text='\t\tSeCustomActions.'+select_action+'(driver,"'+locator+'","'+select_by_method+'",'+select_value+','+replace_text+');\n'
		if text_list[0]=='CheckList':
			java_text='\t\tSeCustomActions.'+select_action+'(driver,"'+locator+'",'+replace_text+');\n'
		if text_list[0] in ['CheckValue','CheckIsSelected']:
			java_text='\t\tSeCustomActions.'+select_action+'(driver,"'+locator+'",'+replace_text+','+select_value+');\n'			
		return java_text,locator,variable_list