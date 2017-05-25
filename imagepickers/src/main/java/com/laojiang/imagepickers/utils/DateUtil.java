package com.laojiang.imagepickers.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 */
public class DateUtil {
    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return
     */
    public static String getStandardDate(String timeStr) {
        if (timeStr == null) return "错误的时间戳";
        StringBuffer sb = new StringBuffer();
        long t;
        try {
            t = Long.parseLong(timeStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "错误的时间戳";
        }
        System.out.println("*******" + t);

        long time = System.currentTimeMillis() - (t * 1000);
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前
        if (day - 28 > 0) {
            sb.append(getTime(t));
        } else if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚") && !(day - 28 > 0)) {
            sb.append("前");
        }
        return sb.toString();
    }

    /**
     * 将时间戳转为代表 “距现在多久之前” 的字符串
     *
     * @param t 时间戳
     * @return
     */
    public static String getStandardDate(long t) {
        StringBuffer sb = new StringBuffer();

        long time = System.currentTimeMillis() - (t);
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前
        if (day - 28 > 0) {
            sb.append(getTime(t));
        } else if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚") && !(day - 28 > 0)) {
            sb.append("前");
        }
        return sb.toString();
    }

    /**
     * 获取英文月份
     *
     * @param date 时间戳
     * @return 英文月份
     */
    public static String getEnglishMonth(long date) {
        String monthStr = getMonth(date);
        String englishMonth = monthStr;
        int month = Integer.parseInt(monthStr);
        switch (month) {
            case 1:
                englishMonth = "Jan.";
                break;
            case 2:
                englishMonth = "Feb.";
                break;
            case 3:
                englishMonth = "Mar.";
                break;
            case 4:
                englishMonth = "Apr.";
                break;
            case 5:
                englishMonth = "May.";
                break;
            case 6:
                englishMonth = "Jun.";
                break;
            case 7:
                englishMonth = "Jul.";
                break;
            case 8:
                englishMonth = "Aug.";
                break;
            case 9:
                englishMonth = "Sep.";
                break;
            case 10:
                englishMonth = "Oct.";
                break;
            case 11:
                englishMonth = "Nov.";
                break;
            case 12:
                englishMonth = "Dec.";
                break;
        }

        return englishMonth;
    }

    //获取星期
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();

        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取月份
     *
     * @param date 时间戳
     * @return 月份
     */
    public static String getMonth(long date) {
        String month = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        month = sdf.format(date);
        return month;
    }
    /**
     * 获取月份
     *
     * @param date 时间戳
     * @return 月份
     */
    public static int getSs(long date) {
        Date date1 = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("ss");
        String ss = sdf.format(date1);

        return Integer.parseInt(ss);
    }

    /**
     * 获取年份
     *
     * @param date 时间戳
     * @return 年份
     */
    public static String getYear(long date) {
        String month = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        month = sdf.format(date);
        return month;
    }

    /**
     * 获取天（几号）
     *
     * @param date 时间戳
     * @return
     */
    public static String getDay(long date) {
        String day = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        day = sdf.format(date);
        return day;
    }

    public static String getTimeForCivilization(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM.dd , yyyy");

        return formatter.format(date * 1000);
    }

    public static String getTimeFoExamDetail(long date1, long date2) {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date1) + "~" + formatter.format(date2);
    }

    public static String getTimeFoExamDay(long date1) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        return formatter.format(date1);
    }

    public static String getWeekForExam(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        String weekStr = formatter.format(date);
        if (weekStr.equals("Monday")) {
            weekStr = "周一";
        } else if (weekStr.equals("Tuesday")) {
            weekStr = "周二";
        } else if (weekStr.equals("Wednesday")) {
            weekStr = "周三";
        } else if (weekStr.equals("Thursday")) {
            weekStr = "周四";
        } else if (weekStr.equals("Friday")) {
            weekStr = "周五";
        } else if (weekStr.equals("Saturday")) {
            weekStr = "周六";
        } else if (weekStr.equals("Sunday")) {
            weekStr = "周日";
        }
        return weekStr;
    }

    public static String getTime(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(date * 1000);
    }
    public static String getHHmm(long date) {
        Date dates = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(dates);
    }

    public static String getDate(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date / 1000);
    }

    public static String getDate2(long date) {
        Date date2 = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date2);
    }
    public static String getDate6(long date) {
        Date date2 = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(date2);
    }
    public static String getDate4(long date) {
        Date dates = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm" );
        return formatter.format(dates);
    }

    public static String getDate5(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static long getNowDate2() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return curDate.getTime();
    }

    public static String getNextDate() {
        Date date = new Date(System.currentTimeMillis());
        long time = (date.getTime() / 1000) + 60 * 60 * 24;//秒
        date.setTime(time * 1000);//毫秒
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nextDate_2 = sf.format(date).toString();
        return nextDate_2;
    }

    public static String getNext2Date() {
        Date date = new Date(System.currentTimeMillis());
        long time = (date.getTime() / 1000) + 60 * 60 * 24 * 2;//秒
        date.setTime(time * 1000);//毫秒
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nextDate_2 = sf.format(date).toString();
        return nextDate_2;
    }

    public static String getNext3Date() {
        Date date = new Date(System.currentTimeMillis());
        long time = (date.getTime() / 1000) + 60 * 60 * 24 * 3;//秒
        date.setTime(time * 1000);//毫秒
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nextDate_2 = sf.format(date).toString();
        return nextDate_2;
    }
    /**
     * "yyyy-MM-dd"
     *
     * @param mill
     * @return
     */
    public static String getDateHHmmss(long mill) {
        //mill为你龙类型的时间戳
        Date date = new Date(mill);

        String strs = "";
        try {
//yyyy表示年MM表示月dd表示日
//yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//进行格式化
            strs = sdf.format(date);

            System.out.println(strs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 将秒转换成时间
     *
     * @param time
     * @return
     */
    public static String timeStr(long time) {
        StringBuffer timeBuffer = new StringBuffer();
        long hour = time / (60 * 60);
        if (hour >= 1) {
            time -= 60 * 60;
        }
        long miu = time / (60);
        if (miu >= 1) {
            time -= miu * 60;
        }
        long se = time;
        timeBuffer.append(hour < 10 ? ("0" + hour) : hour);
        timeBuffer.append(":");
        timeBuffer.append(miu < 10 ? ("0" + miu) : miu);
        timeBuffer.append(":");
        timeBuffer.append(se < 10 ? ("0" + se) : se);
        return timeBuffer.toString();
    }

    /**
     * 将毫秒转换成时间
     *
     * @param time
     * @return
     */
    public static String timeStr2(long time) {
        //L.i("time == " + time);
        StringBuffer timeBuffer = new StringBuffer();
        long hour = time / (60 * 60 * 1000);
        if (hour >= 1) {
            time -= 60 * 60 * 1000;
        }
        long miu = time / (60 * 1000);
        if (miu >= 1) {
            time -= miu * 60 * 1000;
        }
        long se = time / 1000;
        if (se >= 1) {
            time -= se * 1000;
        }
        long mil = time;
        //L.i("hour == " + hour +"  miu == " + miu +" se == " + se);
        /*timeBuffer.append(hour < 10 ? ("0" + hour) : hour);
        timeBuffer.append(":");*/
        timeBuffer.append(miu < 10 ? ("0" + miu) : miu);
        timeBuffer.append(":");
        timeBuffer.append(se < 10 ? ("0" + se) : se);
        timeBuffer.append(":");
        timeBuffer.append(mil < 10 ? ("0" + mil) : mil);
        return timeBuffer.toString();
    }

    /**
     * 将毫秒转换成时间
     *
     * @param time
     * @return
     */
    public static String timeStr3(long time) {
        //L.i("time == " + time);
        StringBuffer timeBuffer = new StringBuffer();
        long hour = time / (60 * 60 * 1000);
        if (hour >= 1) {
            time -= 60 * 60 * 1000;
        }
        long miu = time / (60 * 1000);
        if (miu >= 1) {
            time -= miu * 60 * 1000;
        }
        long se = time / 1000;
        if (se >= 1) {
            time -= se * 1000;
        }
        long mil = time;
        //L.i("hour == " + hour +"  miu == " + miu +" se == " + se);
        /*timeBuffer.append(hour < 10 ? ("0" + hour) : hour);
        timeBuffer.append(":");*/
        timeBuffer.append(miu < 10 ? ("0" + miu) : miu);
        timeBuffer.append(":");
        timeBuffer.append(se < 10 ? ("0" + se) : se);
        /*timeBuffer.append(":");
        timeBuffer.append(mil < 10 ? ("0" + mil) : mil);*/
        return timeBuffer.toString();
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getNowTime2() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当前日期加时间
     *
     * @return
     */
    public static String getNowDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当前时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getDate3(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 格式化时间戳
     *
     * @param date
     * @param format
     * @return
     */
    public static String getDate(long date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String str = formatter.format(date);
        return str;
    }
    //2018年9月3日 14:44

    /**
     * 获取当前时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getNowDateAndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当前时间yyyy-MM-dd HHmmss
     *
     * @return
     */
    public static String getNowDateAndTimeNoWord() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取系统当前时间戳
     *
     * @return
     */
    @SuppressWarnings("static-access")
    public static long getSystemTimeOfLong() {
        Calendar c = Calendar.getInstance();
        long shijian = c.getInstance().getTimeInMillis();
        return shijian;
    }

    /**
     * 考试格式化日期时间
     *
     * @param longTime
     * @return
     */
    public static String getShortDate(String longTime) {
        try {
            String[] strs = longTime.split(" ");
            return strs[0];
        } catch (Exception e) {
            return "输入的格式错误，示例格式：2016-11-11 11:11:11";
        }
    }

    /**
     * 考试格式化日期时间
     *
     * @param longTime
     * @return
     */
    public static String getShortDate2AndTime2(String longTime) {
        String str = getShortDate(longTime);
        String str2 = getShortTime(longTime);
        try {
            String[] strs = str.split("-");
            String[] strs2 = str2.split(":");
            return strs[1] + "-" + strs[2] + " " + strs2[0] + ":" + strs2[1];
        } catch (Exception e) {
            return "输入的格式错误，示例格式：2016-11-11 11:11:11";
        }
    }

    /**
     * 考试格式化日期时间
     *
     * @param longTime
     * @return
     */
    public static String getShortTime(String longTime) {
        try {
            String[] strs = longTime.split(" ");
            return strs[1];
        } catch (Exception e) {
            return "输入的格式错误，示例格式：2016-11-11 11:11:11";
        }
    }

    public static String getWeek(long date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(date);
        String[] week = time.split("-");
        /*
        * 以2049年10月1日（100周年国庆）为例，用蔡勒（Zeller）公式进行计算，过程如下：
        * 蔡勒（Zeller）公式：w=y+[y/4]+[c/4]-2c+[26(m+1)/10]+d-1
        * =49+[49/4]+[20/4]-2×20+[26× (10+1)/10]+1-1
        * =49+[12.25]+5-40+[28.6] =49+12+5-40+28 =54 (除以7余5)
        * 即2049年10月1日（100周年国庆）是星期5。
        */
        int c = Integer.valueOf(week[0].substring(0, 1));
        int y = Integer.valueOf(week[0].substring(2, 3));
        int month = Integer.valueOf(week[1]);
        // int weekIndex=y+(y/4)+(c/4)-2*c+(26*(month+1)/10)+day-1;
        if (month > 3 && month <= 14) {
        } else {
            y = y - 1;
            month = month + 12;
        }
        int day = Integer.valueOf(week[2]);
        int weekIndex = y + (y / 4) + (c / 4) - 2 * c + (26 * (month + 1) / 10) + day - 1;
        if (weekIndex < 0) {
            weekIndex = weekIndex * (-1);
        } else {
            weekIndex = weekIndex % 7;
        }
        String weekDate = null;
        switch (Integer.valueOf(weekIndex)) {
            case 1:
                weekDate = "星期一";
                break;
            case 2:
                weekDate = "星期二";
                break;
            case 3:
                weekDate = "星期三";
                break;
            case 4:
                weekDate = "星期四";
                break;
            case 5:
                weekDate = "星期五";
                break;
            case 6:
                weekDate = "星期六";
                break;
            case 7:
                weekDate = "星期日";
                break;
            default:
                weekDate = "星期一";
                break;
        }
        return weekDate;
    }

    /**
     * 获取当期是星期几
     *
     * @return
     */
    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int week = cal.get(Calendar.DAY_OF_WEEK);
        String weekStr = "";
        switch (week) {
            case Calendar.SUNDAY:
                weekStr = "周日";
                break;
            case Calendar.MONDAY:
                weekStr = "周一";
                break;
            case Calendar.TUESDAY:
                weekStr = "周二";
                break;
            case Calendar.WEDNESDAY:
                weekStr = "周三";
                break;
            case Calendar.THURSDAY:
                weekStr = "周四";
                break;
            case Calendar.FRIDAY:
                weekStr = "周五";
                break;
            case Calendar.SATURDAY:
                weekStr = "周六";
                break;
        }
        return weekStr;
    }
    public static  String numToString(int num){
       if (num==1){
            return "一";
        }else  if (num==2){
            return "二";
        }else  if (num==3){
            return "三";
        }else  if (num==4){
            return "四";
        }else  if (num==5){
            return "五";
        }else  if (num==6){
           return "六";
       }else  if (num==7){
           return "七";
       }else  if (num==8){
           return "八";
       }
        return num+"";
    }
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 根据年月获取最大天数
     * @param year
     * @param month
     * @return
     */
    public static int getLastDay(int year, int month) {
        int day = 1;
        Calendar cal = Calendar.getInstance();
        cal.set(year,month - 1,day);
        int last = cal.getActualMaximum(Calendar.DATE);
        System.out.println(last);
        return last;
    }
}
