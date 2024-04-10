package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {
	Date date;
	SimpleDateFormat dateFormat;
	
	public static Integer getYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);		
	}
	
	public static Integer getMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH);		
	}

	public static String getYearMonthDay(Integer year, Integer month, Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month); 
		calendar.set(Calendar.DAY_OF_MONTH, day);
		Date date = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
		return dateFormat.format(date);
	}

	public static String getYearMonth(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month); 
		Date date = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMM");
		return dateFormat.format(date);
	}
	
	public static String getYearMonthDayFormat(String date) {
		return date.substring(0, 4) + '-' + date.substring(4, 6) + '-' + date.substring(6, 8);
	}
}