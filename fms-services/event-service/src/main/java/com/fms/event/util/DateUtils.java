/**
 * 
 */
package com.fms.event.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author Kesavalu
 *
 */
@Component
public class DateUtils {
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
	public static String getString(Date date) {
		return dateFormat.format(date);
	}

	public static Date getDate(String s) {
		try {
			return dateFormat.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}    
	
}