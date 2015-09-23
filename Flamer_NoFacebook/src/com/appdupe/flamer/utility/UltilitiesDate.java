/**
 * ============================================================================
 *
 * Copyright (C) 2011 Android Museum Systems.  All rights reserved. The content 
 * presented herein may not, under any circumstances, be reproduced, in 
 * whole or in any part or form, without written permission from 
 * Museum Systems.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are NOT permitted. Neither the name of Museum Systems,
 * nor the names of contributors may be used to endorse or promote products 
 * derived from this software without specific prior written permission.
 *
 * ============================================================================
 *
 * Author: Admin
 *  
 *
 * Revision History
 * ----------------------------------------------------------------------------
 * Date                Author              Comment, bug number, fix description
 *
 * Mar 11, 2012      tuan@edge-works.net           version 1.0
 *
 * ----------------------------------------------------------------------------
 */

package com.appdupe.flamer.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Period;

public class UltilitiesDate {

	private static final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

	static int entyer;
	static int endmonth;
	static int endday;
	/** The days. */
	static int days;

	/** The hours. */
	static int hours;

	/** The minutes. */
	static int minutes;

	/** The seconds. */
	static int seconds;

	/** The start. */
	static DateTime start;

	/** The end. */
	static DateTime end;

	/** The p. */
	static Period p = null;

	/**
	 * Date string.
	 * 
	 * @return the string
	 */
	public static String dateString() {
		start = new DateTime(2012, 12, 17, 10, 44, 55);
		end = new DateTime(2012, 12, 25, 10, 47, 53);
		System.out.println("start  date :" + start);
		System.out.println("end  date :" + end);
		p = new Period(start, end);
		days = p.getDays();
		hours = p.getHours();
		minutes = p.getMinutes();
		seconds = p.getSeconds();
		System.out.println(days + "    " + hours + "    " + minutes + "    "
				+ seconds);
		String arrDate = days + "," + hours + "," + minutes + "," + seconds;
		return arrDate;
	}

	/**
	 * Dates string.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */

	public static String datesString(String date) {

		// String date="2012-03-13 08:00:00 PM";
		System.out.println("date  " + date);
		String[] temp = date.split("-");
		int y = Integer.parseInt(temp[0]);
		int m = Integer.parseInt(temp[1]);
		System.out.println("yeae  :" + y);
		System.out.println("month  " + m);
		String[] dayss = temp[2].toString().split(":");
		String[] day = dayss[0].split(" ");
		int d = Integer.parseInt(day[0]);
		int h = Integer.parseInt(day[1]);
		System.out.println("day  :" + d);
		System.out.println("hour  " + h);
		int min = Integer.parseInt(dayss[1]);
		int sec = Integer.parseInt(dayss[2]);
		System.out.println("minute  " + min);
		System.out.println("second  " + sec);
		start = new DateTime();
		System.out.println("start :" + start);
		end = new DateTime(y, m, d, h, min, sec);
		System.out.println("end :" + end);
		// p = new Period(start,end);
		p = new Period(end, start);

		days = p.getDays();
		System.out.println("days  " + days);
		hours = p.getHours();
		System.out.println("hours  " + hours);
		minutes = p.getMinutes();
		System.out.println("minute  " + minutes);
		seconds = p.getSeconds();
		System.out.println("seconds" + seconds);
		String da = "";
		String hr = "";
		String mi = "";
		String s = "";
		if (days < 10)
			da = "00";
		if (days >= 10 && days < 100) {
			da = "0";
		}
		if (hours < 10)
			hr = "0";
		if (minutes < 10)
			mi = "0";
		if (seconds < 10)
			s = "0";

		System.out.println(days + "    " + hours + "    " + minutes + "    "
				+ seconds);
		String arrDate = da + days + ":" + hr + hours + ":" + mi + minutes
				+ ":" + s + seconds;
		return arrDate;
	}

	/**
	 * Gets the days.
	 * 
	 * @return the days
	 */
	public int getDays() {

		return days;
	}

	/**
	 * Gets the hours.
	 * 
	 * @return the hours
	 */
	public int getHours() {

		return hours;
	}

	/**
	 * Gets the minutes.
	 * 
	 * @return the minutes
	 */
	public int getMinutes() {

		return minutes;
	}

	/**
	 * Gets the seconds.
	 * 
	 * @return the seconds
	 */
	public int getSeconds() {

		return seconds;
	}

	// private static String getLocalTime( int hr, int min )
	public static String getLocalTime(String date) {
		String[] date1 = date.split(" ");
		String localTime = "";

		String[] ldate = date1[1].split(":");
		int hr = Integer.parseInt(ldate[0]);
		int min = Integer.parseInt(ldate[1]);
		int sec = Integer.parseInt(ldate[2]);
		// System.out.println("hour and min     " + hr + "    " + min);

		Calendar gmt = Calendar.getInstance();

		gmt.set(Calendar.HOUR, hr);
		gmt.set(Calendar.MINUTE, min);
		gmt.set(Calendar.SECOND, sec);
		gmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		Calendar local = Calendar.getInstance();
		local.setTimeZone(TimeZone.getDefault());
		local.setTime(gmt.getTime());

		int hour = local.get(Calendar.HOUR);
		int minutes = local.get(Calendar.MINUTE);
		int seconds = local.get(Calendar.SECOND);
		boolean am = local.get(Calendar.AM_PM) == Calendar.AM;
		hour = hour + 12;
		String str_hr = "";
		String str_min = "";
		String str_sec = "";
		String am_pm = "";

		if (hour < 10)
			str_hr = "0";
		if (minutes < 10)
			str_min = "0";
		if (seconds < 10)
			str_sec = "0";

		/*
		 * if( am ) am_pm = "AM"; else { am_pm = "PM"; //str_hr = str_hr + 12; }
		 */

		localTime = date1[0] + " " + str_hr + hour + ":" + str_min + minutes
				+ ":" + str_sec + seconds;
		// System.out.println("local time     " + localTime);
		// SimpleDateFormat parsedFormat=new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		return localTime;
	}

	public static String GetDateString(String inputDate) {

		Date date = null;

		SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			date = inFormat.parse(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		String[] days = new String[] { "SUN", "MON", "TUE", "WED", "THUR",
				"FRI", "SAT" };

		String day = days[calendar.get(Calendar.DAY_OF_WEEK)];

		final String[] payload1 = inputDate.split("-");

		String[] months = new String[] { "JAN", "FEB", "MAR", "APR", "MAY",
				"JUNE", "JULY", "AUG", "SEPT", "OCT", "NOV", "DEC" };
		// System.out.println(months[Integer.parseInt(payload1[1])-1]);

		return day + ", " + payload1[2] + " "
				+ months[Integer.parseInt(payload1[1]) - 1] + " " + payload1[0];
	}
}
