package com.tetragon.desto.util;

public class DateUtil {
	
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	private String millisecond;
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMillisecond() {
		return millisecond;
	}
	public void setMillisecond(String millisecond) {
		this.millisecond = millisecond;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public DateUtil(String date) {
		DateUtil smd=DateParser.parse(date);
		this.day = smd.getDay();
		this.hour = smd.getHour();
		this.millisecond = smd.getMillisecond();
		this.minute = smd.getMinute();
		this.month = smd.getMonth();
		this.second = smd.getSecond();
		this.year = smd.getYear();
	}
	public DateUtil() {
		this.day = null;
		this.hour =null;
		this.millisecond = null;
		this.minute = null;
		this.month = null;
		this.second = null;
		this.year = null;
	}
}
