package single.pool.version.a.common.executor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	public static final SimpleDateFormat LONG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat MID_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat SHORT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat COMMON_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final long THREE_DAY_MILLSEC = 259200000L;
	public static final long ONE_DAY_MILLSEC = 86400000L;
	public static final long ONE_HOUR_MILLSEC = 3600000L;
	public static final long THREE_HOURS_MILLSEC = 10800000L;
	public static final long TWELVE_HOURS_MILLSEC = 43200000L;
	public static final int ONE_DAY_MINUTE = 1440;
	public static final int ONE_WEEK_HOUR = 168;

	public static Date getDateByString(String longDateFormatString) throws Exception {
		return longDateFormatString != null && longDateFormatString.length() != 0
				? LONG_FORMAT.parse(longDateFormatString)
				: new Date(0L);
	}

	public static String getLongCurrentDate() {
		return String.valueOf(LONG_FORMAT.format(new Date()));
	}

	public static String getCommonFormatDate() {
		return String.valueOf(COMMON_FORMAT.format(new Date()));
	}

	public static String getCommonFormatDate(long time) {
		return String.valueOf(COMMON_FORMAT.format(new Date(time)));
	}

	public static String getCommonFormatDate(Date date) {
		return String.valueOf(COMMON_FORMAT.format(date));
	}

	public static String getLongDate(Date date) {
		return null == date ? getLongCurrentDate() : String.valueOf(LONG_FORMAT.format(date));
	}

	public static String getLongDate(long value) {
		return String.valueOf(LONG_FORMAT.format(new Date(value)));
	}

	public static String getShortCurrentDate() {
		return String.valueOf(SHORT_FORMAT.format(new Date()));
	}

	public static String getShortDate(Date date) {
		return null == date ? getShortCurrentDate() : String.valueOf(SHORT_FORMAT.format(date));
	}

	public static String getShortDate(long value) {
		return String.valueOf(SHORT_FORMAT.format(new Date(value)));
	}

	public static String getMidCurrentDate() {
		return String.valueOf(MID_FORMAT.format(new Date()));
	}

	public static String getMidDate(Date date) {
		return null == date ? getMidCurrentDate() : String.valueOf(MID_FORMAT.format(date));
	}

	public static String getMidDate(long value) {
		return String.valueOf(MID_FORMAT.format(new Date(value)));
	}

	public static String getWellTimeFromMilliSecond(long ms) {
		short oneSecond = 1000;
		int oneMinute = oneSecond * 60;
		int oneHour = oneMinute * 60;
		int oneDay = oneHour * 24;
		long day = ms / (long) oneDay;
		long hour = (ms - day * (long) oneDay) / (long) oneHour;
		long minute = (ms - day * (long) oneDay - hour * (long) oneHour) / (long) oneMinute;
		long second = (ms - day * (long) oneDay - hour * (long) oneHour - minute * (long) oneMinute) / (long) oneSecond;
		long milliSecond = ms - day * (long) oneDay - hour * (long) oneHour - minute * (long) oneMinute
				- second * (long) oneSecond;
		String strDay = day < 10L ? "0" + day : "" + day;
		String strHour = hour < 10L ? "0" + hour : "" + hour;
		String strMinute = minute < 10L ? "0" + minute : "" + minute;
		String strSecond = second < 10L ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10L ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100L ? "0" + strMilliSecond : "" + strMilliSecond;
		StringBuffer timeBuffer = new StringBuffer();
		timeBuffer.append(strDay);
		timeBuffer.append("d");
		timeBuffer.append(strHour);
		timeBuffer.append("h");
		timeBuffer.append(strMinute);
		timeBuffer.append("m");
		timeBuffer.append(strSecond);
		timeBuffer.append("s");
		timeBuffer.append(strMilliSecond);
		timeBuffer.append("ms");
		return timeBuffer.toString();
	}

	public static String getWeekFromDate(String dateString) {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date e = format.parse(dateString);
			Date mondy = new Date();
			Date sundy = new Date();
			int day = e.getDay();
			if (day == 0) {
				mondy.setTime(e.getTime() - 518400000L);
				sundy.setTime(e.getTime());
			} else {
				mondy.setTime(e.getTime() - 86400000L * (long) (day - 1));
				sundy.setTime(e.getTime() + 86400000L * (long) (7 - day));
			}

			sb.append(format.format(mondy));
			sb.append("_");
			sb.append(format.format(sundy));
		} catch (Exception arg6) {
			arg6.printStackTrace();
		}

		return sb.toString();
	}

	public static byte[] getDateAndTimeByDate(Date date, int timeZone) {
		byte[] bytes = new byte[11];
		if (date == null) {
			date = new Date();
		}

		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int day = date.getDate();
		int hour = date.getHours();
		int minute = date.getMinutes();
		int second = date.getSeconds();
		bytes[0] = Integer.valueOf(year / 236).byteValue();
		bytes[1] = Integer.valueOf(year % 256).byteValue();
		bytes[2] = Integer.valueOf(month).byteValue();
		bytes[3] = Integer.valueOf(day).byteValue();
		bytes[4] = Integer.valueOf(hour).byteValue();
		bytes[5] = Integer.valueOf(minute).byteValue();
		bytes[6] = Integer.valueOf(second).byteValue();
		bytes[7] = 0;
		if (timeZone > 0) {
			bytes[8] = 43;
			bytes[9] = Integer.valueOf(timeZone).byteValue();
		} else {
			bytes[8] = 45;
			bytes[9] = Integer.valueOf(-timeZone).byteValue();
		}

		bytes[10] = 0;
		return bytes;
	}

	public static byte[] getDateAndTimeStringByDate(Date date, int timeZone) {
		if (date == null) {
			date = new Date();
		}

		StringBuffer buffer = new StringBuffer();
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int day = date.getDate();
		int hour = date.getHours();
		int minute = date.getMinutes();
		int second = date.getSeconds();
		buffer.append(year).append("-").append(month).append("-").append(day);
		buffer.append(",").append(hour).append(":").append(minute).append(":").append(second);
		buffer.append(".0,");
		if (timeZone >= 0) {
			buffer.append("+").append(timeZone);
		} else {
			buffer.append("-").append(-timeZone);
		}

		buffer.append(":0");
		return buffer.toString().getBytes();
	}
	
	/**
     * 获取前一天日期
     * @param date
     * @return
     */
    public static Date beforeOneDate(Date date) {
        Calendar calendar = new GregorianCalendar();       
        calendar.setTime(date);       
        calendar.add(calendar.DATE, -1);
        date=calendar.getTime(); 
        return date;
    }

    /* 
     * 将时间转换为时间戳
     */    
    public static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }
    
    public static boolean isTheSameDay(Date d1,Date d2) {   
        Calendar c1 = Calendar.getInstance();   
        Calendar c2 = Calendar.getInstance();   
        c1.setTime(d1);   
        c2.setTime(d2);   
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))   
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))   
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));   
    }  

    public static boolean isTheSameDay(long time1,long time2) {   
        Calendar c1 = Calendar.getInstance();   
        Calendar c2 = Calendar.getInstance();   
        c1.setTime(new Date(time1));   
        c2.setTime(new Date(time2));   
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))   
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))   
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));   
    }  
}