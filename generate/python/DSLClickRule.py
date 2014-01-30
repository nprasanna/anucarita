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
class DSLClickRule():
	def __init__(self):
		self=self
	def click_rule(self,text_list):
		variable_list=[]
		'''
		Click ThisLink link
		Click Index 0 in ThisLink link
		Click Index ReplaceValue ThisValue in ThisLink link
		Click Index ReplaceVariable ThisVariable in ThisLink link
		Click ReplaceValue ThisValue in ThisLink link
		Click ReplaceVariable ThisVariable in ThisLink link
	
		link can be replaced with checkbox,button,element
		Click can be replaced with Click,RightClick,ClickPresent,FireJS,MouseOver,Check,FCheck,ClickChecked,ClickNotChecked
		'''
		click_replace={
		'Click':'seClick',#seClick(WebDriver driver,String locator,String replace)
		'RightClick':'seRightClick',#seRightClick(WebDriver driver,String locator,String replace)
		'ClickPresent':'sePresentElementClick',#sePresentElementClick(WebDriver driver,String locator,String replace)
		'FireJS':'fireJS',#fireJS(WebDriver driver,String locator,String replace)
		'MouseOver':'seMouseOver',#seMouseOver(WebDriver driver,String locator,String replace)
		'Check':'seIsElementPresent',#seIsElementPresent(WebDriver driver,String locator,String replace)
		'FCheck':'seIsElementReversePresent',#seIsElementReversePresent(WebDriver driver,String locator,String replace)
		'ClickChecked':'seClickChecked',#seClickChecked(WebDriver driver,String locator,String replace)
		'ClickNotChecked':'seClickNotChecked'#seClickNotChecked(WebDriver driver,String locator,String replace)
		}
		
		
		click_action=click_replace[text_list[0]] 

		if 'in' in text_list:
			in_index=text_list.index('in')
			locator='.'.join(text_list[in_index+1:])
		else:
			locator='.'.join(text_list[1:])
			
		replace_text=''	
		if text_list[1] in ['Index','ReplaceValue','ReplaceVariable']:
			replace_text=' '.join(text_list[2:in_index])
			if text_list[1]=='Index':
				replace_text=' '.join(text_list[2:in_index])
				if text_list[2] in ['ReplaceValue','ReplaceVariable']:
					replace_text=' '.join(text_list[3:in_index])
		if 'ReplaceVariable' not in text_list:
			replace_text='"'+replace_text+'"'
		else:
			variable_list.append(replace_text)
		
		java_text='\t\tSeCustomActions.'+click_action+'(driver,"'+locator+'",'+replace_text+');\n' 
		
		return java_text,locator,variable_list