package com.jt.test.calendar;

import java.util.Calendar;

import org.joda.time.Hours;

public class TestCalendar {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		System.out.println("初始时间为：" + calendar.getTime());
		Long ca = calendar.getTimeInMillis();
		System.out.println("当前的毫秒值为：" + ca);
		calendar.add(calendar.HOUR, 1);
		System.out.println("初始时间+1小时" + calendar.getTime());
		calendar.add(calendar.DATE, 30);
		System.out.println("加30天" + calendar.getTime());
	}
}
