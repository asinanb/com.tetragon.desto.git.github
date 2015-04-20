package com.tetragon.desto.util;

import java.sql.Timestamp;

public class SMUtil {
	
	public static int stringToInt (String strValue){
		int intValue;
		if (strValue==null)strValue="0";
		intValue= Integer.parseInt(strValue.trim());
		return intValue;
	}
	
	public static long stringToLong (String strValue){
		if (strValue==null)strValue="0";
		long longValue;
		longValue= Long.parseLong(strValue.trim());
		return longValue;
	}
	
	public static String intToString (int intValue){
		String strValue;
		strValue= Integer.toString(intValue);
		return strValue;
	}
	
	public static String longToString (long longValue){
		String strValue;
		strValue= Long.toString(longValue);
		return strValue;
	}
	public static double stringToDouble (String strValue){
		double dblValue;
		dblValue= Double.valueOf(strValue.trim()).doubleValue();
		return dblValue;
	}

	public static float stringToFloat(String strValue) {
		float fltValue = 0;
		try {
			fltValue= Float.valueOf(strValue.trim()).floatValue();
		} catch (NumberFormatException e) {
		}
		return fltValue;
	}
//
//	public static Date stringToDate(String strValue) {
//		Date dateValue=Date.valueOf(strValue);
//		return dateValue;
//	}
	

	public static float randVal (float min,float max){
		float value;
		value=MathUtil.randNum(min,max);
		return value;
	}

	public static int randVal (int min,int max){
		int value;
		value=MathUtil.randNum(min,max);
		return value;
	}
	public static int randVal (int max){
		int value;
		value=MathUtil.randNum(max);
		return value;
	}
	public static String floatToString (float f){
		return Float.toString(f);
	}
	public static String doubleToString (double d){
		return Double.toString(d);
	}
// String to boolean	
	public static boolean stringToBoolean (String strValue){
		boolean boolValue;
		boolValue= Boolean.valueOf(strValue.trim()).booleanValue();
		return boolValue;
	}


	public static Timestamp jDateToSqlDate (java.util.Date javaDate){
//		 BUG: loses time of day
//		java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
		java.sql.Timestamp sqlDate =(new java.sql.Timestamp(javaDate.getTime()));
		return sqlDate;
	}
	
	public static Object nullControll(Object obj) {
		if (obj==null){
			
			Object nullObject= new Object();
			return nullObject;
		}
		return obj;
	}
}
