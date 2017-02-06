package com.tobee.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateParser {
	public String getCurrentDate()
	{
		//java.text.DecimalFormat df = new java.text.DecimalFormat("000000");
		//String formatgps = df.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sd1 = new SimpleDateFormat("yyyymmdd");
		System.out.println("Date : " + sd1.format(new Date(c.getTimeInMillis())));
		SimpleDateFormat sd2 = new SimpleDateFormat("HHmmss");
		System.out.println("Time : " + sd2.format(new Date(c.getTimeInMillis())));
		
		SimpleDateFormat sd3 = new SimpleDateFormat("HHmmss");
		System.out.println("Time : " + sd2.format(new Date(c.getTimeInMillis()+1000)));
		
		return sd1.format(new Date(c.getTimeInMillis()));
	}
}
