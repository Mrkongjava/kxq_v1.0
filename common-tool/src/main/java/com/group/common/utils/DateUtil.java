package com.group.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述：日期工具类
 */
public class DateUtil {
    private static final Log logger = LogFactory.getLog(DateUtil.class);
    
    final static String[] datePatters = new String[]{"yyyy-MM-dd", "yyyyMMdd", "dd/MM/yyyy", "MM/dd/yyyy", "yyyy年MM月dd日"};
    final static String[] datetimePatters = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyyMMdd HHmmss", "dd/MM/yyyy HH:mm:ss", "MM/dd/yyyy HH:mm:ss", "yyyy年MM月dd日 HH时mm分ss秒"};

    public static Date parseDateTryCustomPatters(String date){
    	if (date == null || date.trim().length()<=0) {
            return null;
        }
    	date = date.trim();
    	
    	if(date.indexOf("-")!=-1){
    		if(date.split("-").length!=3){
    			return null;
    		}
    	}else if(date.indexOf("/")!=-1){
    		if(date.split("/").length!=3){
    			return null;
    		}
    	}
    	
    	SimpleDateFormat formatter = new SimpleDateFormat();
        Date d = null;
        for (String patter : datePatters) {
			try {
				formatter.applyPattern(patter);
				d = formatter.parse(date);
				break;
			}catch(ParseException pe){
			}catch (Exception e) {
				System.err.println("=================");
				System.err.println("p:" + patter + ",d:" + date);
				System.err.println("=================");
				e.printStackTrace();
			}
		}
        return d;
    }
    
    public static Date parseDatetimeTryCustomPatters(String datetime){
    	if (datetime == null || datetime.trim().length()<=0) {
            return null;
        }
    	datetime = datetime.trim();
    	
    	if(datetime.indexOf("-")!=-1){
    		if(datetime.split("-").length!=3){
    			return null;
    		}
    	}else if(datetime.indexOf("/")!=-1){
    		if(datetime.split("/").length!=3){
    			return null;
    		}
    	}
    	
    	SimpleDateFormat formatter = new SimpleDateFormat();
        Date d = null;
        for (String patter : datetimePatters) {
			try {
				formatter.applyPattern(patter);
				d = formatter.parse(datetime);
				break;
			}catch(ParseException pe){
			}catch (Exception e) {
				System.err.println("=================");
				System.err.println("p:" + patter + ",d:" + datetime);
				System.err.println("=================");
				e.printStackTrace();
			}
		}
        return d;
    }
    
    /**
     * 对以毫秒为单位的时间间隔进行转换,加上单位.
     * @param millisecond 以毫秒为单位的时间
     * @return 如millisecond值为63000,则返回"1分钟3秒"字符串
     * @author zrh
     */
	public static String time2CHNStr(long millisecond) {
    	
    	if( millisecond <= 0 ) {
    		return "0秒";
    	}
    	long t = millisecond/1000;

    	StringBuilder timeStr = new StringBuilder();
    	long d=0, h=0, m=0, s=0, ms=0;;//天,小时,分钟,秒
    	
    	
    	if( t >= (24*60*60) ) {
    		d = t/(24*60*60);
    	}
    	if( (t - d*24*60*60) >= (60*60) ) {
    		h = (t - d*24*60*60)/(60*60);
    	}
    	if( (t - d*24*60*60 - h*60*60) >= 60 ) {
    		m = (t - d*24*60*60 - h*60*60)/60;
    	}
    	
    	s = t - d*24*60*60 - h*60*60 - m*60;	//秒
    	
    	if( d > 0 ) {
    		timeStr.append(d).append("天");
    	}
    	if( d > 0 || h > 0 ) {
    		timeStr.append(h).append("小时");
    	}
    	if( d > 0 || h > 0 || m > 0 ) {
    		timeStr.append(m).append("分");
    	}
    	if( d > 0 || h > 0 || m > 0 || s > 0) {
    		timeStr.append(s).append("秒");
    	}

    	ms = millisecond%1000;
    	if( ms > 0 ) {
    		timeStr.append(ms).append("毫秒");
    	}
    	return timeStr.toString();
    }

    /**
     * 格式化时间
     * @param date
     * @param dateFormat  返回的时间格式：yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyyMMdd HHmmss", "dd/MM/yyyy HH:mm:ss", "MM/dd/yyyy HH:mm:ss", "yyyy年MM月dd日 HH时mm分ss秒"
     * @return
     */
	public static String format(Date date,String dateFormat){
		if( date == null ) {
            return "";
        }
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

    /**
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new DateFormat();
        formatter.setLenient(false);
        return formatter.parse(date);
    }

    public static Date parseDate(String date, String patter) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.setTimeZone(TimeZone.getDefault());
        formatter.applyPattern(patter);

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatLocal(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DATE);
        int ww = calendar.get(Calendar.DAY_OF_WEEK);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        int se = calendar.get(Calendar.SECOND);
        String s = "";
        s += Integer.toString(yy) + "年";
        s += Integer.toString(mm) + "月";
        s += Integer.toString(dd) + "日";
        s += " ";
        switch (ww) {
            case 1:
                s += "星期日";
                break;
            case 2:
                s += "星期一";
                break;
            case 3:
                s += "星期二";
                break;
            case 4:
                s += "星期三";
                break;
            case 5:
                s += "星期四";
                break;
            case 6:
                s += "星期五";
                break;
            case 7:
                s += "星期六";
                break;
        }
        s += " ";
        s += hh < 10 ? "0" + Integer.toString(hh) : Integer.toString(hh);
        s += ":";
        s += mi < 10 ? "0" + Integer.toString(mi) : Integer.toString(mi);
        s += ":";
        s += se < 10 ? "0" + Integer.toString(se) : Integer.toString(se);
        return s;
    }

    public static String formatLocal() {
        return formatLocal(new Date());
    }

    private static String adjust(String date) {
    	if((date.endsWith(" 00:00:00") || date.endsWith(" 00:00") || date.endsWith(" 00"))){
    		if(date.length()>10){
    			return date.substring(0, 10);
    		}
    	}
        return date;
    }

    public static String formatDate(Date date, String patter) {
    	SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.setTimeZone(TimeZone.getDefault());
        formatter.applyPattern(patter);
        formatter.setLenient(false);
        return adjust(formatter.format(date));
    }

    public static String formatGMTDate(Date date) {
    	SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("yyyy-MM-dd HH:mm:ss");
        formatter.setLenient(false);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return adjust(formatter.format(date));
    }

    public static String formatGMTDate(Date date, String patter) {
    	SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern(patter);
        formatter.setLenient(false);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return adjust(formatter.format(date));
    }

//    public static String formatDate(String date) {
//        Date d = parseDate(date);
//        return d != null ? formatDate(d) : "";
//    }

    public static String formatDate(String date, String patter) {
        Date d = parseDate(date);
        return d != null ? formatDate(d, patter) : "";
    }

    /**
     * 得到当前时间,不带时分秒毫秒
     *
     * @return Date
     */
    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 将当前日期转换成指定格式的字符串
     *
     * @param pattern 日期格式
     * @return 返回格式化的日期字符串
     */
    public static String getNow(String pattern) {
        return formatDate(new Date(), pattern);
    }

    /**
     * 获得当前时间
     *
     * @return Date
     */
    public static Date getNow() {
        return new Date();
    }

    /**
     * 取得年份，格式"yyyy"
     *
     * @return 返回当前年份
     */
    public static int getYear() {
        Date now = new Date();
        return getYear(now);
    }

    /**
     * 取得日期的年份，格式"yyyy"
     *
     * @param date 日期
     * @return 日期的年份
     */
    public static int getYear(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        return cale.get(Calendar.YEAR);
    }

    /**
     * 取得月份
     *
     * @return 返回当前月份
     */
    public static int getMonth() {
        Date now = new Date();
        return getMonth(now);
    }

    /**
     * 取得日期的月份
     *
     * @param date 日期
     * @return 日期的月份
     */
    public static int getMonth(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        return cale.get(Calendar.MONTH) + 1;
    }

    /**
     * 取得日期所在季度
     *
     * @return
     */
    public static int getQuarter(Date date) {
        int month = getMonth(date);
        if (month >= 1 && month <= 3) {
            return 1;
        } else if (month >= 4 && month <= 6) {
            return 2;
        } else if (month >= 7 && month <= 9) {
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * 取得当前日期所在季度
     *
     * @return
     */
    public static int getQuarter() {
        return getQuarter(new Date());
    }

    /**
     * 取得今天的日期数
     *
     * @return 返回今天的日期数
     */
    public static int getDay() {
        Date now = new Date();
        return getDay(now);
    }

    /**
     * 取得日期的天数
     *
     * @param date 日期
     * @return 日期的天数
     */
    public static int getDay(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        return cale.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得与某日期相隔几年的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的年数,可以是负数，表示日期前几年
     * @return 返回的日期
     */
    public static Date addYear(Date date, int addCount) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.YEAR, addCount);
        return cale.getTime();
    }
    
    /**
     * 获得与某日期相隔几月的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的月数,可以是负数，表示日期前几月
     * @return 返回的日期
     */
    public static Date addMonth(Date date, int addCount) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, addCount);
        return cale.getTime();
    }
    
    /**
     * 获得与某日期相隔几天的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的天数,可以是负数，表示日期前几天
     * @return 返回的日期
     */
    public static Date addDay(Date date, int addCount) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.DAY_OF_MONTH, addCount);

        return cale.getTime();
    }
    
    public static Date addHour(Date date, int addCount) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.HOUR_OF_DAY, addCount);
        return cale.getTime();
    }
    
    public static Date addMinutes(Date date, int minutes) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MINUTE, minutes);
        return cale.getTime();
    }

    /**
     * 得到某天是周几
     *
     * @param strDay 2010-01-02
     * @return 周几
     */
    public static int getWeekDay(String strDay) {

        Date day = parseDate(strDay);
        Calendar cale = Calendar.getInstance();
        cale.setTime(day);
        return cale.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到某天是周几
     *
     * @param date 日期类型
     * @return 周几
     */
    public static int getWeekDay(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        return cale.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 取得两个日期段的日期间隔
     *
     * @param t1 时间1
     * @param t2 时间2
     * @return t2 与t1的间隔天数
     */
    public static int getBetweenDays(String t1, String t2) {
        Date d1 = parseDate(t1);
        Date d2 = parseDate(t2);
        return getBetweenDays(d1, d2);
    }

    /**
     * 取得两个日期段的日期间隔
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return t2 与t1的间隔天数
     */
    public static int getBetweenDays(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return -1;
        }
        int betweenDays;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            c2.setTime(d1);
            c1.setTime(d2);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears
                ;
             i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }
    
    /**
     * 计算两个时间之间相差的小时数
     * @param d1
     * @param d2
     * @return
     */
    public static Long getBetweenHours(Date d1, Date d2){
        long hours = 0;  
        long time1 = d1.getTime();  
		long time2 = d2.getTime();  
		long diff ;  
		if(time1<time2) {  
		    diff = time2 - time1;  
		} else {  
		    diff = time1 - time2;  
		}  
		hours = diff / (1000 * 60 * 60);  
        return hours;  
    }
    

    /**
     * 转换字符串为日期，格式"yyyy-MM-dd"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date strToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        return formatter.parse(date);
    }

    /**
     * 转换字符串为日期，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date strToTime(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setLenient(false);
        return formatter.parse(date);
    }

    /**
     * 转换日期为字符串，格式"yyyy-MM-dd"
     *
     * @param date 日期
     * @return 返回格式化的日期字符串
     */
    public static String dateToStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 返回 yyyy年MM月dd日
     *
     * @param date 日期对象
     * @return 返回格式化的日期字符串
     */
    public static String dateToStrCN(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M月d日");
        return formatter.format(date);
    }

    /**
     * 转换日期为字符串，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param date 日期
     * @return 返回格式化的日期字符串
     */
    public static String timeToStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
    
    /**
     * 转换日期为字符串，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param millisecond 日期毫秒
     * @return 返回格式化的日期字符串
     */
    public static String timeToStr(long millisecond) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d =  new Date(millisecond);
        return formatter.format(d);
    }

    public static Date getWeekDate(Date indate, int weeknum) {
        Calendar c = Calendar.getInstance();
        c.setTime(indate);
        c.set(Calendar.DAY_OF_WEEK, weeknum);
        return c.getTime();
    }

    /**
     * 获得指定日期所在月份的第一天
     *
     * @param date 日期对象
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date getFirstDate(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-01");
        String firstdate = formatter.format(date);
        return strToDate(firstdate);
    }

    /**
     * 获得今天所在月份的第一天
     *
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date getFirstDate() throws ParseException {
        Date now = new Date();
        return getFirstDate(now);
    }

    /**
     * 获得指定日期所在月份的最后一天
     *
     * @param date 日期对象
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date getLastDate(Date date) throws ParseException {
        Date nextMonthfirstDate = addMonth(getFirstDate(date), 1);
        return addDay(nextMonthfirstDate, -1);
    }

    /**
     * 得到每年第几周的开始日期,默认星期日是每周的第一天
     *
     * @param theyear
     * @param weekIndex
     * @param addDays
     * @return
     */
    public static String getFirstYearOfWeek(int theyear, int weekIndex, int addDays) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, theyear);
        c.set(Calendar.WEEK_OF_YEAR, weekIndex);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + addDays); //设置星期几为每周第一天
        String strdate = sdf.format(c.getTime());
        return strdate;
    }


    /**
     * 得到每年第几周的结束日期
     *
     * @param theyear
     * @param weekIndex
     * @param addDays
     * @return
     */
    public static String getLastYearOfWeek(int theyear, int weekIndex, int addDays) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, theyear);
        c.set(Calendar.WEEK_OF_YEAR, weekIndex);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + addDays);
        String strdate = sdf.format(c.getTime());
        return strdate;
    }

    /**
     * 得到当前日期是多少周
     *
     * @return int
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static int getCurrentWeeks() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 得到某一年周的总数
     *
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(c.getTime());
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获得今天所在月份的最后一天
     *
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date getLastDate() throws ParseException {
        Date now = new Date();
        return getLastDate(now);
    }

    /**
     * 通过两个日期，获得间隔月份
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonthBetweenDate(String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sdf.parse(date1));
        aft.setTime(sdf.parse(date2));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 得到当前时间,不带时分秒毫秒
     *
     * @return String
     */
    public static String getDateString() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        String s = dataFormat.format(calendar.getTime());
        return s;
    }

    /**
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     */
    public static String formatDuring(long mss) {
        if (mss <= 0) {
            return "小于1毫秒";
        }
        if (mss < 1000) {
            return mss + "毫秒";
        }
        String result = "";
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        if (days > 0) {
            result += days + "天";
        }
        if (hours > 0) {
            result += hours + "小时";
        }
        if (minutes > 0) {
            result += minutes + "分";
        }
        if (seconds >= 0) {
            result += seconds + "秒";
        }
        return result;
    }

    /**
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     */
    public static String formatDuring(Date begin, Date end) {
        if (begin != null && end != null) {
            return formatDuring(end.getTime() - begin.getTime());
        } else {
            return "";
        }
    }


    public static Date getRandomDate(Date start, Date end) {
        Random random = new Random();
        long s = start.getTime();
        long e = end.getTime();
        long rtn = s + (long) (random.nextDouble() * (e - s));
        return new Date(rtn);
    }

    public static Date getRandomDate(String start, String end) {
        return getRandomDate(parseDate(start), parseDate(end));
    }

    /**
     * 将日期转换为corn表达式
     * @param date
     * @return
     */
    public static String getCron(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        return sdf.format(date);
    }

    /**
     * 判断两个时间是否有重叠
     * @param bt
     * @param ot
     * @param st
     * @param ed
     * @return
     */
    public static boolean getAlphalDate(Date bt,Date ot,Date st,Date ed) {
        long btlong = Math.min(bt.getTime(), ot.getTime());// 开始时间
        long otlong = Math.max(bt.getTime(), ot.getTime());// 结束时间
        long stlong = Math.min(st.getTime(), ed.getTime());// 开始时间
        long edlong = Math.max(st.getTime(), ed.getTime());// 结束时间

        // 具体算法如下
        // 首先看是否有包含关系
        if ((stlong >= btlong && stlong <= otlong) || (edlong >= btlong && edlong <= otlong)) {
            // 一定有重叠部分
            return true;
        }else {
            return false;
        }
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear1(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }

    // 获取某年的第几周的开始日期
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    // 获取某年的第几周的结束日期
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    // 获取当前时间所在周的开始日期
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }

    // 获取当前时间所在周的结束日期
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);

        return c.getTime();
    }

    //获取当前月的第一天
    public static Date getFirstDayOfMonth(Date date){
        Calendar cale = Calendar.getInstance();
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return cale.getTime();
    }

    //获取当前月的最后一天
    public static Date getLastDayOfMonth(Date date){
        Calendar cale = Calendar.getInstance();
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return cale.getTime();
    }

    /**
     * 比较两个时间是否为同一天
     * @param date1
     * @param date2
     * @return  true，表示同一天；false表示不同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);

        //分别比较两个时间的年月日，若都一样，则为同一天
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 比较两个时间是否为同一个月
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameMONTH(Date date1, Date date2) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);

        //分别比较两个时间的年月，若都一样，则为同一个月
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH);
    }



    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String format(Calendar calendar) {
        Date d = calendar.getTime();
        return sdf.format(d);
    }

    /**
     * 把标准格式字符串(yyyy-MM-dd HH:mm:ss)转换为日期对象（java.util.Date）.
     *
     * @param datestr - 日期字符串  ,标准日期格式为 yyyy-MM-dd HH:mm:ss
     */
    public static Date parseStandardDate(String datestr) {
        Date date = null;
        try {
            if (StringUtils.isNotEmpty(datestr)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(datestr);
            }
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return date;
    }

    /**
     * 把日期对象（java.util.Date）格式化为标准日期格式 yyyy-MM-dd HH:mm:ss.
     *
     * @param date - 日期对象(java.util.Date)
     */
    public static String formatStandardDate(Date date) {
        String datestr = null;
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            datestr = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datestr;
    }


    /**
     * 把字符串转换为日期对象（java.util.Date）
     *
     * @param datestr - 日期字符串
     * @param dateFormat - 跟字符串对应的日期格式，例如： yyyy-MM-dd HH:mm:ss
     */
    public static Date parseString2Date(String datestr, String dateFormat) {

        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            date = sdf.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 把日期对象（java.util.Date）格式化为字符串
     *
     * @param date - 日期对象(java.util.Date)
     * @param dateFormat - 格式字符串，例如： yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate2String(Date date, String dateFormat) {
        String datestr = null;
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            datestr = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datestr;
    }

    /**
     * 比较两个日期之间的大小
     *
     * @return 前者大于后者返回true 反之false
     */
    public static boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得昨天的结束时刻,YYYY-MM-DD 23:59:59
     */
    public static String getPreLastTime() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = now.getTime();
        String before = sdf.format(date);
        return before + " 23:59:59";
    }

    /**
     * 获得日期的结束时刻,YYYY-MM-DD 23:59:59
     */
    public static Date getLastTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String before = sdf.format(date);
        return parseString2Date(before + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获得当天的开始时刻,YYYY-MM-DD 00:00:00
     */
    public static String getCurrentFirstTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()) + " 00:00:00";
    }

    /**
     * 获得当天的结束时刻,YYYY-MM-DD 23:59:59
     */
    public static String getCurrentLastTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()) + " 23:59:59";
    }

    /**
     * 转化为java.sql.Timestamp
     */
    public static java.sql.Timestamp toSqlTimeStamp(Date date) {

        if (null == date) {
            return null;
        }

        java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
        return time;
    }

    /**
     * 转化为java.sql.Date
     */
    public static java.sql.Date toSqlDate(Date date) {

        if (null == date) {
            return null;
        }

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }

    /**
     * 获取指定日期所在星期的日期列表(周一到周日)
     */
    public static List<String> makeWeekdays(Date date) {

        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 定义星期一为一周的第一天
        final int FIRST_DAY = Calendar.MONDAY;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 循环找到星期一那天的日期
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }

        List<String> weekdays = new ArrayList<String>(7);
        for (int i = 0; i < 7; i++) {
            String day = sdf.format(calendar.getTime());
            weekdays.add(day);
            // 计算下一天
            calendar.add(Calendar.DATE, 1);
        }

        return weekdays;
    }

    /**
     * 获取指定日期所在星期的日期列表(周一到周日)
     *
     * @param datestr 为 yyyy-MM-dd 格式日期字符串 例：2015-01-01
     */
    public static List<String> makeWeekdays(String datestr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return makeWeekdays(date);
    }

    /**
     * 比较两个日期是否在同一个星期（周一到周日为一个星期）
     *
     * @param date1 为 yyyy-MM-dd 格式日期字符串 例：2015-01-01
     * @param date2 为 yyyy-MM-dd 格式日期字符串 例：2015-01-02
     */
    public static boolean isSameWeek(String date1, String date2) {

        boolean flag = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 非空判断
        if (StringUtils.isNotBlank(date1) && StringUtils.isNotBlank(date2)) {
            //检验格式
            try {
                sdf.parse(date1);
                sdf.parse(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return flag;
            }
        } else {
            return flag;
        }

        List<String> weekdays = makeWeekdays(date1);

        for (String day : weekdays) {
            if (day.equals(date2)) {
                flag = true;
                return flag;
            }
        }

        return flag;
    }

    /**
     * 比较两个日期是否同一个月
     *
     * @param date1 为 yyyy-MM-dd 格式日期字符串 例：2015-01-01
     * @param date2 为 yyyy-MM-dd 格式日期字符串 例：2015-01-02
     */
    public static boolean isSameMonth(String date1, String date2) {

        boolean flag = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 非空判断
        if (StringUtils.isNotBlank(date1) && StringUtils.isNotBlank(date2)) {
            //检验格式
            try {
                sdf.parse(date1);
                sdf.parse(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return flag;
            }
        } else {
            return flag;
        }

        if (date1.substring(0, 7).equals(date2.substring(0, 7))) {
            flag = true;
        }

        return flag;
    }

    /**
     * 比较两个日期是否同一个月
     */
    public static boolean isSameMonth(Date date1, Date date2) {

        boolean flag = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        String date1str = sdf.format(date1);
        String date2str = sdf.format(date2);

        if (date1str.equals(date2str)) {
            flag = true;
        }

        return flag;
    }

    /**
     * 获得指定时间的 yyyy-MM-dd 格式
     * @param date
     * @return
     */
    public static String getDate(Date date) {
        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 获得指定时间的 yyyy-MM-dd HH:mm:ss 格式
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 时间相减，返回相差的秒数 result = frontDate - backDate
     * @param frontDate
     * @param backDate
     * @return
     */
    public static long subtractDate(Date frontDate, Date backDate) {

        long result = (long) 0.0;
        try {
            result = (frontDate.getTime() - backDate.getTime()) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得指定格式的年月日的前后的年月日
     * @param dateStr   日期字符串 如：2014-03-20，2014-03-20 10:00:00
     * @param format    日期格式 如：yyyy-MM-dd，yyyy-MM-dd HH:mm:ss
     * @param year      年  如获得当前时间前一年为负数，后一年为正数，若不变则填 null；下面的月和日也一样
     * @param month     月
     * @param date      日
     * @return
     */
    public static String subMonth(String dateStr,String format,Integer year,Integer month,Integer date) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date dt = sdf.parse(dateStr);

            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);

            rightNow.add(Calendar.YEAR,year == null ? 0:year);
            rightNow.add(Calendar.MONTH, month == null ? 0:month);
            rightNow.add(Calendar.DATE,date == null ? 0:date);
            Date dt1 = rightNow.getTime();
            String reStr = sdf.format(dt1);
            return reStr;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(subMonth("2014-03-20","yyyy-mm-dd",null,null,null));

    }
}
