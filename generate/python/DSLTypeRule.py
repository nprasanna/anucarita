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

class DSLTypeRule():
	def __init__(self):
		self=self
	def type_rule(self,text_list):
		variable_list=[]
		'''
		Type TypeValue in ThisField field
		Type TypeValue in Index 0 ins ThisField field
		Type TypeValue in Index ReplaceValue ThisValue ins ThisField field
		Type TypeValue in Index ReplaceVariable ThisVariable ins ThisField field
		Type TypeValue in ReplaceValue ThisValue ins ThisField field
		Type TypeValue in ReplaceVariable ThisVariable ins ThisField field
		Type Variable VariableThis in ThisField field
		Type Variable VariableThis in Index 0 ins ThisField field
		Type Variable VariableThis in Index ReplaceValue ThisValue ins ThisField field
		Type Variable VariableThis in Index ReplaceVariable ThisVariable ins ThisField field
		Type Variable VariableThis in ReplaceValue ThisValue ins ThisField field
		Type Variable VariableThis in ReplaceVariable ThisVariable ins ThisField field
		
		Check ThisValue in ThisField field
		Check Variable VariableThis in ThisField field
		GetValue AsThisVariable in ThisField field
		
		CheckField ThisField field
		
		Type can be replaced with TypeAndSubmit,TypePresent,CheckField,FCheckField,GetValue,Check
		'''
		type_replace={
		'Type':'seType',#seType(WebDriver driver,String locator,String textToBeTyped,String replace)
		'TypeAndSubmit':'seTypeAndSubmit',#seTypeAndSubmit(WebDriver driver,String locator,String textToBeTyped,String replace)
		'TypePresent':'sePresentElementType',#sePresentElementType(WebDriver driver,String locator,String textToBeTyped,String replace)
		'CheckField':'seIsElementPresent',#seIsElementPresent(WebDriver driver,String locator,String replace)
		'FCheckField':'seIsElementReversePresent',#seIsElementReversePresent(WebDriver driver,String locator,String replace)
		'GetValue':'seGetValue',#seGetValue(WebDriver driver,String locator,String replace)
		'Check':'seCheckValue'#seCheckValue(WebDriver driver,String locator,String replace, String valueToCheck)
		}

		type_action=type_replace[text_list[0]]
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
		
		text_typed=' '.join(text_list[1:in_index])
		text_typed='"'+text_typed+'"'
		if text_list[1]=='Variable':
			text_typed=' '.join(text_list[2:in_index])
			variable_list.append(text_typed)
			
		if 'ReplaceVariable' not in text_list:
			replace_text='"'+replace_text+'"'
		else:
			variable_list.append(replace_text)
			
		java_text='\t\tSeCustomActions.'+type_action+'(driver,"'+locator+'",'+text_typed+','+replace_text+');\n'
		
		if text_list[0]=='GetValue':
			java_text='\t\t'+text_typed+'=SeCustomActions.'+type_action+'(driver,"'+locator+'",'+replace_text+');\n'
			
		if text_list[0]=='CheckField':
			java_text='\t\tSeCustomActions.'+type_action+'(driver,"'+locator+'",'+replace_text+');\n'

		return java_text,locator,variable_list