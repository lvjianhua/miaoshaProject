package com.lv_miaoshaProject.common.utils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class PBMeth {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String newStr(String str) {
        String newStr = null;
        String[] temp = str.split(",");
        for (String string : temp) {
            if (string == null || string.length() == 0) {
                continue;
            }
            if (newStr == null) {
                newStr = "'" + string + "'";
            } else {
                newStr += ",'" + string + "'";
            }
        }
        return newStr;
    }

    public static boolean isCheckUUID(String UID) {
        try {
            UUID.fromString(UID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //java去除字符串中的空格、回车、换行符、制表符
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    public static String getChinaNum(int weekday) {
        if (weekday == 1) {
            return "一";
        } else if (weekday == 2) {
            return "二";
        } else if (weekday == 3) {
            return "三";
        } else if (weekday == 4) {
            return "四";
        } else if (weekday == 5) {
            return "五";
        } else if (weekday == 6) {
            return "六";
        } else {
            return "日";
        }
    }


    public static String getChinaStr(String weekday) {
        if (weekday == null || weekday.length() == 0) {
            return "";
        }
        if (weekday.equals("一")) {
            return "1";
        } else if (weekday.equals("二")) {
            return "2";
        } else if (weekday.equals("三")) {
            return "3";
        } else if (weekday.equals("四")) {
            return "4";
        } else if (weekday.equals("五")) {
            return "5";
        } else if (weekday.equals("六")) {
            return "6";
        } else {
            return "7";
        }
    }


    /**
     * 生成min到max之间的随机整数
     * <p>
     * return int
     */
    public static int CreateRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * 字符串转换成日期
     *
     * @param str    字符串
     * @param layout 格式
     * @return date
     */
    public static Date StrToDate(String str, String layout) {

        SimpleDateFormat format = new SimpleDateFormat(layout);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {

            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }

            outBuff.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {

            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }

        return 1;
    }

    public static boolean checkTimeDifference(String firstTime, String secondTime, long time) {
        try {

            Date beginTime = timeFormat.parse(firstTime);
            Date endTime = timeFormat.parse(secondTime);

            if (endTime.getTime() - beginTime.getTime() > time) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long getQuot(String time1, String time2) {
        long quot = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = date1.getTime() - date2.getTime();
            quot = quot / 1000 / 60 / 60 / 24;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quot;
    }


    public static boolean isDate(String strDate) {
        try {
            if (strDate.length() != 10) {
                return false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(strDate);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static double DiffTime(String BeginTime, String EndTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        // boolean Compare=false;
        double Compare = 0;
        try {
            Date beginDate = sdf.parse(BeginTime);
            Date endDate = sdf.parse(EndTime);
            begin.setTime(beginDate);
            end.setTime(endDate);
            Compare = ((double) end.getTimeInMillis() - (double) begin.getTimeInMillis()) / 60 / 60 / 1000;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Compare;


    }

    public static boolean DiffTime(String BeginTime, String EndTime, String inputTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar input = Calendar.getInstance();
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        boolean Compare = false;
        try {
            Date inputDate = sdf.parse(inputTime);
            Date beginDate = sdf.parse(BeginTime);
            Date endDate = sdf.parse(EndTime);
            /*System.out.print(DateDiff(beginDate,inputDate));*/
            input.setTime(inputDate);
            begin.setTime(beginDate);
            end.setTime(endDate);
            if (input.getTimeInMillis() > begin.getTimeInMillis() && input.getTimeInMillis() < end.getTimeInMillis()) {
                return true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Compare;


    }

    /**
     * 把double类型的变量弄成2位
     *
     * @param value
     * @param scale
     * @return
     */
    public static double round(double value, int scale) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    public static String getMinTime(String curYear, String curMonth) {
        return curYear + "-" + curMonth + "-01 00:00:00";
    }

    public static String getMaxTime(String curYear, String curMonth) {
        String maxTime = "";
        int iYear = Integer.parseInt(curYear);
        int iMonth = Integer.parseInt(curMonth);
        iMonth++;
        if (iMonth > 12) {
            iYear++;
            maxTime = String.valueOf(iYear) + "-01-01 00:00:00";
        } else {
            maxTime = curYear + "-" + String.valueOf(iMonth) + "-01 00:00:00";
        }
        return maxTime;
    }


    /**
     * 把用户输入的不合法的日期转换成合法的时间比如11-10 10:30变成2013-11-10 10:30:00,11-10变成2013-11-10 00:00:00
     *
     * @param strTime
     * @return
     */
    public static final String str2Date(String strTime) {
        String resultTime = null;
        String[] tempTimes = strTime.split(" ");
        String strDay = tempTimes[0];

        if (tempTimes.length == 1) {
            resultTime = getDayTime(strDay) + " 00:00:00";
        } else if (tempTimes.length == 2) {
            strDay = getDayTime(strDay);
            String strHour = tempTimes[1];
            String[] strHours = strHour.split(":");
            if (strHours.length == 3) {
                resultTime = strDay + " " + strHour;
            } else if (strHours.length == 2) {
                resultTime = strDay + " " + strHour + ":00";
            } else if (strHours.length == 1) {
                resultTime = strDay + " " + strHour + ":00:00";
            } else {
                resultTime = strDay;
            }
            if (resultTime.length() == 0) {
                resultTime = null;
            }
        }

        return resultTime;
    }

    /**
     * 返回时间格式的字符中的日期
     *
     * @param strDay 时间格式的字符串
     * @return
     */
    private static String getDayTime(String strDay) {
        String resultTime = "";
        String[] strDays = strDay.split("-");
        if (strDays.length == 2) {
            resultTime = PBMeth.GetNowYear() + "-" + strDay;
        } else if (strDays.length == 3) {
            resultTime = strDay;
        }

        return resultTime;
    }

    public static final String getFormateTime(String strTime) {
        String strResult = "";
        if (strTime.trim().length() > 0) {
            int ipos = strTime.indexOf(".");
            if (ipos < 0) {
                return "";
            }

            String hours = strTime.substring(0, ipos);
            String minutes = "0";
            if (ipos + 1 < strTime.length()) {
                minutes = strTime.substring(ipos + 1, strTime.length());
                int iminutes = Integer.parseInt(minutes) * 60 / 100;
                if (iminutes < 10) {
                    minutes = "0" + String.valueOf(iminutes);
                } else {
                    minutes = String.valueOf(iminutes);
                }
            }

            strResult = hours + ":" + minutes;

        }
        return strResult;
    }

    public static final String string2FormateTime(String dateString)
            throws ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        dateFormat.setLenient(false);

        Date timeDate = dateFormat.parse(dateString);
        java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());
        return String.valueOf(dateTime);
    }

    public static final java.sql.Timestamp string2Time(String dateString)
            throws ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dateFormat.setLenient(false);
        Date timeDate = dateFormat.parse(dateString);
        java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());
        return dateTime;
    }

    public static final String GetYYMMDD() {
        String strDate = GetNowDate();
        String strNewDate = strDate.split(" ")[0];
        String strYear = strNewDate.split("-")[0];
        strYear = strYear.substring(2, 4);
        String strMonth = strNewDate.split("-")[1];
        String strDay = strNewDate.split("-")[2];

        return strYear + strMonth + strDay;
    }

    public static final String GetYYMM() {
        String strDate = GetNowDate();
        String strNewDate = strDate.split(" ")[0];
        String strYear = strNewDate.split("-")[0];
        strYear = strYear.substring(2, 4);
        String strMonth = strNewDate.split("-")[1];

        return strYear + strMonth;
    }

    public static final String GetNowDate(String dateFormate) {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        temp_str = sdf.format(dt);
        return temp_str;
    }

    /**
     * 返回yyyy-MM-dd HH:mm:ss格式的日期
     *
     * @return
     */
    public static final String GetNowDate() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        temp_str = sdf.format(dt);
        return temp_str;
    }

    /**
     * 返回yyyy-MM-dd格式的日期
     *
     * @return
     */
    public static final String GetNowYear() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        temp_str = sdf.format(dt);
        String[] temp_strs = temp_str.split("-");
        temp_str = temp_strs[0];
        return temp_str;
    }

    public static final String GetNowHour() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        temp_str = sdf.format(dt);
        String[] temp_strs = temp_str.split(" ");
        temp_str = temp_strs[1];
        return temp_str;
    }

    public static final String GetNowMinutes() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        temp_str = sdf.format(dt);
        String[] temp_strs = temp_str.split(":");
        temp_str = temp_strs[1];
        return temp_str;
    }

    public static final String GetDiffYear(int difNum) {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        temp_str = sdf.format(dt);
        String[] temp_strs = temp_str.split("-");
        temp_str = temp_strs[0];
        int curYear = Integer.valueOf(temp_str);
        int lastYear = curYear + difNum;
        return String.valueOf(lastYear);
    }


    public static final String GetLastMonth(String oYear, String oMonth) {
        String lastStr = "";
        int curMonth = Integer.valueOf(oMonth);
        int curYear = Integer.valueOf(oYear);
        curMonth -= 1;
        if (curMonth == 0) {
            curMonth = 12;
            curYear -= 1;
        }

        lastStr = String.valueOf(curYear) + "," + String.valueOf(curMonth);

        return lastStr;
    }

    //��ȡָ���·ݵ��¸��µ��·�
    public static final String GetNextMonth(String oYear, String oMonth) {
        String lastStr = "";
        int curMonth = Integer.valueOf(oMonth);
        int curYear = Integer.valueOf(oYear);
        curMonth += 1;
        if (curMonth == 13) {
            curMonth = 1;
            curYear += 1;
        }

        lastStr = String.valueOf(curYear) + "," + String.valueOf(curMonth);

        return lastStr;
    }

    //��ȡ�ϸ��µ���ݺ��·�
    public static final String GetLastMonth() {
        String lastStr = "";
        int curMonth = Integer.valueOf(GetNowMonth());
        int curYear = Integer.valueOf(GetNowYear());
        curMonth -= 1;
        if (curMonth == 0) {
            curMonth = 12;
            curYear -= 1;
        }

        lastStr = String.valueOf(curYear) + "," + String.valueOf(curMonth);

        return lastStr;
    }

    public static final String GetCurrentQuarter() {
        String curMonth = GetNowMonth();
        if (curMonth.startsWith("0")) {
            curMonth = curMonth.substring(1, 2);
        }
        int icurMonth = Integer.parseInt(curMonth);
        if (icurMonth < 4) {
            return "1";
        } else if (icurMonth < 7) {
            return "2";
        } else if (icurMonth < 10) {
            return "3";
        } else if (icurMonth < 12) {
            return "4";
        }

        return "1";
    }


    public static final String GetPreQuarter() {
        String curMonth = GetNowMonth();
        if (curMonth.startsWith("0")) {
            curMonth = curMonth.substring(1, 2);
        }
        int icurMonth = Integer.parseInt(curMonth);
        if (icurMonth < 4) {
            return "4";
        } else if (icurMonth < 7) {
            return "1";
        } else if (icurMonth < 10) {
            return "2";
        } else if (icurMonth < 12) {
            return "3";
        }

        return "1";
    }

    public static final String GetNowMonth() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        temp_str = sdf.format(dt);
        String[] temp_strs = temp_str.split("-");
        temp_str = temp_strs[1];
        return temp_str;
    }

    public static final String GetNowDay() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        temp_str = sdf.format(dt);
        String[] temp_strs = temp_str.split("-");
        temp_str = temp_strs[2];
        return temp_str;
    }

    /**
     * 获取时间撮
     *
     * @return
     */
    public static final String getTimeString() {
        String time;
        Calendar cal = new GregorianCalendar();
        time = "" + cal.get(Calendar.HOUR) +
                cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND) + cal.get(Calendar.MILLISECOND);
        return time;
    }

    public static String getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateformat1.format(now.getTime());
    }

    public static String getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateformat1.format(now.getTime());
    }

    public static String getDateHourAfter(Date d, int hour) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateformat1.format(now.getTime());
    }

    public static Date getShortDateFormate(String strDate) throws Exception {
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
        return dateformat1.parse(strDate);
    }

    public static Date getFullDateFormate(String strDate) throws Exception {
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateformat1.parse(strDate);
    }

    /**
     * 计算两个时间的相差天数
     *
     * @param toTime
     * @param fromTime
     * @return
     */
    public static long DefferDate(String toTime, String fromTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            long to = df.parse(toTime).getTime();
            long from = df.parse(fromTime).getTime();
            return (to - from) / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
            return 10;
        }
    }

    /**
     * 比较两个时间的大小，如果DATE1>DATE2那么就返回1，相等返回0，否则返回-1
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return 0;
    }


    /**
     * 计算两个时间的相差分钟
     *
     * @param toTime
     * @param fromTime
     * @return
     */
    public static long DefferMinth(String toTime, String fromTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            long to = df.parse(toTime).getTime();
            long from = df.parse(fromTime).getTime();
            return (to - from);
        } catch (Exception e) {
            e.printStackTrace();
            return 10;
        }
    }

    /**
     * 检查客户输入数据的有效性
     *
     * @param s
     * @param checkType
     * @return
     */
    public static final boolean checkInput(String s, int checkType) {
        if (s == null) return false;

        s = s.toLowerCase();

        //默认空格是不合法字符
        String strsql = " #and#or#exec#insert#select#drop#delete#-- #' #update#count#*#%#chr#mid#master#truncate #char#declare#script#alert#frame#<#>";

        if (checkType == 1) //空格是合法字符
        {
            strsql = "and #or #exec#insert #select #drop #delete #-- #' #update #count#*#%#chr#mid#master#truncate #char#declare #script#alert#frame#<#>";
        }

        String[] strs = strsql.split("#");


        for (int i = 0; i < strs.length; i++) {
            String strTemp = strs[i].toString();
            if (s.indexOf(strTemp) >= 0) {
                return false;
            }
        }

        return true;
    }

    public static String javaScriptEscape(String input) {
        if (input == null) {
            return input;
        }
        StringBuffer filtered = new StringBuffer(input.length());
        char prevChar = '\u0000';
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (c == '"') {
                filtered.append("\\\"");
            } else if (c == '\'') {
                filtered.append("\\'");
            } else if (c == '\\') {
                filtered.append("\\\\");
            } else if (c == '\t') {
                filtered.append("\\t");
            } else if (c == '\n') {
                if (prevChar != '\r') {
                    filtered.append("\\n");
                }
            } else if (c == '\r') {
                filtered.append("\\n");
            } else if (c == '\f') {
                filtered.append("\\f");
            } else if (c == '/') {
                filtered.append("\\/");
            } else if (c == '<') {
                filtered.append("&lt;");
            } else if (c == '>') {
                filtered.append("&gt");
            } else if (c == ' ') {
                filtered.append("&nbsp;");
            } else {
                filtered.append(c);
            }
            prevChar = c;
        }
        return filtered.toString();
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            Character gg = str.charAt(i);
            if (!Character.isDigit(str.charAt(i))) {
                if (!gg.equals('-')) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isMoney(String str) {
        str = str.replace(".", "");
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String MoneyNumbersForDisplay(String s, char mSeparator) {
        String formattedString = "";
        String mIntegerPart = "";
        String mDecimalPart = "";
        //If the number is Zero then just return 0.00
        if (s.equals("0")) {
            return "0.00";
        }//end if

        //Check if there are decimals
        if (s.indexOf(".") == -1) {
            mIntegerPart = s;
            mDecimalPart = ".00";
        } else {
            mIntegerPart = s.substring(0, ((int) s.indexOf(".")));
            mDecimalPart = s.substring(((int) s.indexOf(".")), (int) s.length());
        }//end if

        //Get the integer form of the String
        int num = Integer.parseInt(mIntegerPart);
        //Create new DecimalFormat object
        DecimalFormat dfNum = new DecimalFormat();
        //Create new DecimalFormatSynbols object
        DecimalFormatSymbols dfsNum = dfNum.getDecimalFormatSymbols();
        //Apply the symbol
        dfsNum.setGroupingSeparator(mSeparator);
        dfNum.setDecimalFormatSymbols(dfsNum);
        //Apply the thousands separator method of DecimalFormat
        formattedString = dfNum.format(num) + mDecimalPart;
        //return the formatted string
        return formattedString;
    }

    public static String IntegerNumbersForDisplay(String s, char mSeparator) {
        String formattedString = "";
        String mIntegerPart = "";
        //If the number is Zero then just return 0.00
        if (s.equals("0")) {
            return "0";
        }//end if

        //Get the integer form of the String
        int num = Integer.parseInt(s);
        //Create new DecimalFormat object
        DecimalFormat dfNum = new DecimalFormat();
        //Create new DecimalFormatSynbols object
        DecimalFormatSymbols dfsNum = dfNum.getDecimalFormatSymbols();
        //Apply the symbol
        dfsNum.setGroupingSeparator(mSeparator);
        dfNum.setDecimalFormatSymbols(dfsNum);
        //Apply the thousands separator method of DecimalFormat
        formattedString = dfNum.format(num);
        //return the formatted string
        return formattedString;
    }

    public static String getHanZiNumber(String totalMoney) {
        String lastMoney = "";
        int ipos = totalMoney.indexOf(".");
        if (ipos > 0) {
            String preMoney = totalMoney.substring(0, ipos);
            String endMoney = totalMoney.substring(ipos + 1, totalMoney.length());

            String pointFrontNum = toHanArr(preMoney);
            if (pointFrontNum.length() > 0 && !"零".equals(pointFrontNum)) {
                preMoney = pointFrontNum + "元";
            } else {
                preMoney = "";
            }

            if (Integer.parseInt(endMoney) > 0) {
                if (endMoney.length() == 1) {
                    lastMoney = preMoney + toHanArr(endMoney) + "角";
                } else {
                    String fEndMoney = endMoney.substring(0, 1);
                    if (fEndMoney.equals("0")) {
                        fEndMoney = "零";
                    } else {
                        fEndMoney = toHanArr(fEndMoney) + "角";
                    }

                    String sEndMoney = endMoney.substring(1, 2);
                    if (sEndMoney.equals("0")) {
                        sEndMoney = "";
                    } else {
                        sEndMoney = toHanArr(sEndMoney) + "分";
                    }

                    lastMoney = preMoney + fEndMoney + sEndMoney;
                }
            } else {
                lastMoney = preMoney + "整";
            }
        } else {
            lastMoney = toHanArr(totalMoney) + "元整";
        }

        lastMoney = removeZero(lastMoney);

        return lastMoney;
    }
    
    public static void main(String[] args) {
        try{
        	String x = "Mon Mar 02 13:57:49 CST 2015";
            SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            try
            {
            	Date date=sdf1.parse(x);
                SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:mm");
                String sDate=sdf.format(date);
                System.out.println(sDate);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception e){
        	System.out.println(e.getMessage());
        }
    }

    /**
     * 数字转换成人民币大写
     *
     * @param numStr
     * @return
     */
    public static String toHanArr(String numStr) {
        String[] hanArr = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[] unitArr = {"拾", "佰", "仟"};
        String[] unitBigArr = {"万", "亿"};
        String[] pointArr = {"角", "分"};
        String result = "";
        int numberLen = numStr.length();
        //如果是负数则要把第一个负号（-）去掉并在数字前面加上"负"
        if (numberLen >= 2 && "-".equals(numStr.substring(0, 1))) {
            numberLen = numberLen - 1;
            numStr = numStr.substring(1);
            result = "负";
        }

        int numberBit = 0;

        if (numberLen > 14) {
            return "不能转换大于万亿（14位）以上的数字";
        }
        // 4个数字为一节，先计算一共有几节
        int numPart = numberLen % 4 == 0 ? numberLen / 4 : numberLen / 4 + 1;
        // 第一节有几位
        // 54，8545，8454 则第一节只有两位
        int firstNumpart = numberLen - (numPart - 1) * 4;
        // 构造一个数组，存储每一节的数据
        String[] numPartArr = new String[numPart];
        int index = -1;
        for (int j = 0; j < numPartArr.length; j++) {
            numPartArr[j] = "";
            if (j == 0) {
                // 第一组
                numPartArr[j] += numStr.substring(0, firstNumpart);
            } else {
                // 不是第一组数字，应该是4位
                numPartArr[j] += numStr.substring(firstNumpart + (j - 1) * 4,
                        firstNumpart + j * 4);
            }
            //转换为中文
            for (int i = 0; i < numPartArr[j].length(); i++) {
                int num = numPartArr[j].charAt(i) - 48;
                int numPartLength = numPartArr[j].length();
                if (i != numPartLength - 1 && num != 0) {
                    result += hanArr[num] + unitArr[numPartLength - 2 - i];
                } else {
                    result += hanArr[num];
                }
            }

            if (numPartArr.length == 2 && j == 0) {
                result += unitBigArr[0];
            }
            if (numPartArr.length == 3 && j == 0) {
                result += unitBigArr[1];
            }
            if (numPartArr.length == 3 && j == 1) {
                result += unitBigArr[0];
            }
        }

        return result;
    }

    public static String removeZero(String lastMoney) {
        while (lastMoney.contains("零零")) {
            lastMoney = lastMoney.replace("零零", "零");
        }

        if (lastMoney.contains("零元")) {
            lastMoney = lastMoney.replace("零元", "元");
        }
        return lastMoney;
    }

    //日期转换为UNIX时间戳
    public static String dateToUnix(String dateStr) throws ParseException {
        Date date = StrToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
        return String.valueOf((timeFormat.parse(timeFormat.format(date))).getTime() / 1000);
    }

    //UNIX时间戳转换为日期
    public static String unixToDate(String unixTime) throws ParseException {
        Long timestamp = Long.parseLong(unixTime) * 1000;
        return timeFormat.format(new Date(timestamp));
    }

    /**
     * @param 要转换的毫秒数
     * @return 该毫秒数转换为 h:m:s 后的格式
     * @author jiapj
     */
    public static String formatDuring(long mss) {
        long hours = mss / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return hours + "h" + minutes + "m"
                + seconds + "s";
    }


    /**
     * 方法用途：判断传入的时间过了 mouth 个 月之后，是否小于当前日期。例如传入 2016-08-10，2  得到true.
     *
     * @param date  传入的时间
     * @param mouth 相差月份
     * @return
     * @throws Exception 2016年12月27日
     * @author:dingzefeng
     */
    public static boolean getQuotMonth(String date, int mouth) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        boolean result = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());

        Calendar c2 = Calendar.getInstance();
        c2.setTime(sdf.parse(date));
        c2.add(Calendar.MONTH, mouth);

        if (c1.getTimeInMillis() > c2.getTimeInMillis()) {
            result = true;
        }
        return result;
    }


}