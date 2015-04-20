package com.tetragon.desto.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
	
	

	public static DateUtil parse(String pSenmotDate) {
		DateUtil senmotDate=new DateUtil();	
		//2007/12/31 23:59:59 999
		//0   4  7  10 13 16 19    20   
		int length=pSenmotDate.length();
		senmotDate.setYear(pSenmotDate.substring(0,4));
		senmotDate.setMonth(pSenmotDate.substring(5,7));
		senmotDate.setDay(pSenmotDate.substring(8,10));
		senmotDate.setHour(pSenmotDate.substring(11,13));
		senmotDate.setMinute(pSenmotDate.substring(14,16));
		senmotDate.setSecond(pSenmotDate.substring(17,19));
		if (length>19)
			senmotDate.setMillisecond(pSenmotDate.substring(20));
		
		return senmotDate;
	}
	public static Long subtract(String firstDate,String secondDate) {

//		SenmotDate senmotFirstDate=new SenmotDate(firstDate);
//		SenmotDate senmotSecondDate=new SenmotDate(secondDate);
		
		long diff = 0 ;
		SimpleDateFormat df = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss SSS");
		try {
			Date fDate=df.parse(firstDate);
			Date sDate=df.parse(secondDate);
			diff = fDate.getTime( ) - sDate.getTime( );
//			System.out.println("diff="+diff);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		int yearDif=SMUtil.stringToInt(senmotFirstDate.getYear())-SMUtil.stringToInt(senmotSecondDate.getYear());
//		int monthDif=SMUtil.stringToInt(senmotFirstDate.getMonth())-SMUtil.stringToInt(senmotSecondDate.getMonth());
//		int dayDif=SMUtil.stringToInt(senmotFirstDate.getDay())-SMUtil.stringToInt(senmotSecondDate.getDay());
//		int hourDif=SMUtil.stringToInt(senmotFirstDate.getHour())-SMUtil.stringToInt(senmotSecondDate.getHour());
//		int minuteDif=SMUtil.stringToInt(senmotFirstDate.getMinute())-SMUtil.stringToInt(senmotSecondDate.getMinute());
//		int secondDif=SMUtil.stringToInt(senmotFirstDate.getSecond())-SMUtil.stringToInt(senmotSecondDate.getSecond());
//		int millisecondDif=SMUtil.stringToInt(senmotFirstDate.getMillisecond())-SMUtil.stringToInt(senmotSecondDate.getMillisecond());
//		
//		long result= millisecondDif + secondDif*1000 + minuteDif*1000*60 +
//			hourDif*1000*60*60 + dayDif*1000*60*60*24 + monthDif*1000*60*60*24*30 + yearDif*1000*60*60*24*365 ; 
		return diff;
	}
	public static Long subtract(Date firstDate,Date secondDate) {
		long diff = 0 ;
		diff = firstDate.getTime( ) - secondDate.getTime( );
		return diff;
	}
	
	public static Long dateToLong(String aDate) {

//		SenmotDate senmotFirstDate=new SenmotDate(firstDate);
//		SenmotDate senmotSecondDate=new SenmotDate(secondDate);
		
		long startAt = 0 ;
		SimpleDateFormat df = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss SSS");
		try {
			Date fDate=df.parse(aDate);
			startAt = fDate.getTime( ) ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return startAt;
	}
	public static Date senmotDateToDate(String aDate) {

		SimpleDateFormat df = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss SSS");
		Date fDate = null;
		try {
			fDate = df.parse(aDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fDate;
	}
	public static Date senmotDateToDateYMDHMS(String aDate) {

		SimpleDateFormat df = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
		Date fDate = null;
		try {
			fDate = df.parse(aDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fDate;
	}

}
