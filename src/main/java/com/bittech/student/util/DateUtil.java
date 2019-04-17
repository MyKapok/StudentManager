package com.bittech.student.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
本类用来进行时间的格式化
*/
public class DateUtil {

//传入日期，在传入指定格式
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	
//传入字符串，再传入指定的格式
	public static Date formatString(String str,String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
}
