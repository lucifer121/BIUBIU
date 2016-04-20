package com.android.biubiu.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CaculateDateUtils {
	private static final List<String> YEARS = new ArrayList<String>();
	private static final int FROM = 1975, TO = 2100;

	static {
		for (int i = 1975; i <= 2100; i++) YEARS.add(String.valueOf(i)+"年");
	}

	private List<String> years = YEARS;
	private int from = FROM, to = TO;
	private static final List<String> MONTHS = new ArrayList<String>();

	static {
		for (int i = 1; i <= 12; i++) MONTHS.add(String.valueOf(i)+"月");
	}

	private List<String> months = MONTHS;

	private static final HashMap<Integer, List<String>> DAYS = new HashMap<Integer, List<String>>();

	private static final Calendar C = Calendar.getInstance();

	private List<String> days = new ArrayList<String>();

	private int day, month,year;
	private int maxDay;
    
	public static CaculateDateUtils caculateDate;
	public static CaculateDateUtils getInstance(){
		if(caculateDate == null){
			caculateDate = new CaculateDateUtils();
		}
		return caculateDate;
	}
	public void setYearRange(int yearFrom, int yearTo) {
		from = yearFrom;
		to = yearTo;
		years.clear();
		for (int i = yearFrom; i <= yearTo; i++) years.add(String.valueOf(i)+"年");
	}
	private void setData() {
		int maxDay = C.getActualMaximum(Calendar.DATE);
		if (maxDay == this.maxDay) return;
		this.maxDay = maxDay;
		List<String> days;
		if (DAYS.containsKey(maxDay)) {
			days = DAYS.get(maxDay);
		} else {
			days = new ArrayList<String>();
			for (int i = 1; i <= maxDay; i++) days.add(String.valueOf(i)+"日");
			DAYS.put(maxDay, days);
		}
		this.days = days;
	}
	public void setCurrentDay(int day) {
		day = Math.max(day, 1);
		day = Math.min(day, maxDay);
	}
	public void setCurrentMonth(int month) {
		setMonth(month);
		setData();
	}

	private void setMonth(int month) {
		month = Math.max(month, 1);
		month = Math.min(month, 12);
		this.month = month;
		C.set(Calendar.MONTH, month - 1);
	}

	public void setCurrentYear(int year) {
		setYear(year);
		setData();
	}

	private void setYear(int year) {
		year = Math.max(year, 1);
		year = Math.min(year, Integer.MAX_VALUE - 1);
		this.year = year;
		C.set(Calendar.YEAR, year);
	}

	public void setCurrentYearAndMonth(int year, int month) {
		setYear(year);
		setMonth(month);
		setData();
	}
	public List<String> getDays(int year, int month) {
		setCurrentYearAndMonth(year, month);
		return days;
	}
	public List<String> getMonths() {
		return months;
	}
	public List<String> getYears(int yearFrom, int yearTo) {
		setYearRange(yearFrom, yearTo);
		return years;
	}
	public String[] getDaySs(int year, int month) {
		setCurrentYearAndMonth(year, month);
		String[] daySs = new String[days.size()];
		for(int i=0;i<days.size();i++){
			daySs[i] = days.get(i);
		}
		return daySs;
	}
	public String[] getMonthSs() {
		String[] monthSs = new String[months.size()];
		for(int i=0;i<months.size();i++){
			monthSs[i] = months.get(i);
		}
		return monthSs;
	}
	public String[] getYearSs(int yearFrom, int yearTo) {
		setYearRange(yearFrom, yearTo);
		String[] yearSs = new String[years.size()];
		for(int i=0;i<years.size();i++){
			yearSs[i] = years.get(i);
		}
		return yearSs;
	}
}
