package com.tobee.tests;


public class CharaterParser {
	
	
	public String skipSpecialHtmlChars(String sentence)
	{
		int size = sentence.getBytes().length;
		char [] arryvl = new char[size];
		char [] tarryvl = new char[size];
		sentence.getChars(0, sentence.length(), arryvl, 0);
		
		boolean start = false;
		boolean end = false;
		int idx = 0;
		
		for( int i =0;i < arryvl.length;i++)
		{
			if(arryvl[i] == '&' ) 
			{
				start = true;
				end = false;
			}
			else if(arryvl[i] == ';')
			{
				end = true;
				start = false;
				continue;
			}
			
			if(!start)
				tarryvl[idx++] = arryvl[i];
			else
			if(end)
			{
				tarryvl[idx++] = arryvl[i];
			}
		}
		
		
		return new String(tarryvl).trim();
	}
	
	/*
	public static void main(String[] args)
	{
		String targetS = "09&nbsp;호&nbsp;8697";
		
		String afterS = skipSpecialHtmlChars(targetS);
		System.out.println(afterS);
	}
	*/
}
