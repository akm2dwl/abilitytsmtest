package com.ability.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Util {
    
    private final static Pattern LTRIM = Pattern.compile("^\\s+");
    private final static Pattern RTRIM = Pattern.compile("\\s+$");
        
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
    
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
            .getExternalContext().getRequest();
    }

    public static void showUIMessage(Severity severity, String message) {
        FacesMessage facesMessage = new FacesMessage(severity, message, "");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    public static java.sql.Date sqlDate(java.util.Date calendarDate){
        if(calendarDate != null){
            return new java.sql.Date(calendarDate.getTime());
        }
        else {
            return null;
        }
    }
    
    public static java.sql.Timestamp sqlTime(java.util.Date calendarDate){
        if(calendarDate != null){
            return new java.sql.Timestamp(calendarDate.getTime());
        }
        else {
            return null;
        }
    }
    
    public static java.util.Date utilDate(java.sql.Date calendarDate){
        if(calendarDate != null){
            return new java.util.Date(calendarDate.getTime());
        }
        else {
            return null;
        }
    }

    public static Double decimalFormat(double value, String format) {
		
        //DecimalFormat df = new DecimalFormat("#,##");
        DecimalFormat df = new DecimalFormat(format);

        String d = df.format(value);

        return Double.valueOf(d);
    }

    public static Date dateAdd(Date date, int increment) {

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.DAY_OF_MONTH, increment);
        // c1.add(Calendar.MONTH, 1);
        Date date1 = c1.getTime();

        return date1;
    }

    public static Date dateFormat(Date date, int increment) {

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.DAY_OF_MONTH, increment);
        // c1.add(Calendar.MONTH, 1);
        Date date1 = c1.getTime();

        return date1;
    }

    public static String dateToString(Date date, String format) {

        SimpleDateFormat sm = new SimpleDateFormat(format);
        String strDate = sm.format(date);

        return strDate;
    }

    public static Date stringToDate(String date, String format) {

        Date convertedDate = null;

        DateFormat formatter = new SimpleDateFormat(format);
        try {
                convertedDate = (Date) formatter.parse(date);
        } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return convertedDate;

    }

    public static String todayDate() {
            Date myDate = new Date();
            System.out.println(myDate);
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sm.format(myDate);
            String todayDate = strDate;

            return todayDate;
    }

    public static String Format(Date date, String format) throws ParseException {

            SimpleDateFormat sm = new SimpleDateFormat(format);
            String strDate = sm.format(date);

            return strDate;

    }

    public static String DateFormat(String dateInString, String format) {

        String r = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {

                Date date = formatter.parse(dateInString);
                int result;
                if (date != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        result = cal.get(Calendar.DAY_OF_MONTH);

                        r = String.valueOf(result);
                }

        } catch (ParseException e) {
                r = e.getMessage();
        }
        return r;
    }

    public static int getYearFromDate(Date date) {
        int result = -1;
        if (date != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                result = cal.get(Calendar.YEAR);

                System.out.println(result);
        }
        return result;
    }

    public static void DateDiff(String dateStart, String dateStop) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Date d1 = null;
        Date d2 = null;

        try {

            d1 = format.parse(dateStart);

            d2 = format.parse(dateStop);

            long diff = d2.getTime() - d1.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Calendar startCalendar = new GregorianCalendar();
            startCalendar.setTime(d1);
            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(d2);

            int diffYear = endCalendar.get(Calendar.YEAR)
                            - startCalendar.get(Calendar.YEAR);
            int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH)
                            - startCalendar.get(Calendar.MONTH);

            Calendar startCal = Calendar.getInstance();
            startCal.setTime(d1);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(d2);

            int diffDayOfWeek = endCal.get(Calendar.DAY_OF_WEEK)
                            - startCal.get(Calendar.DAY_OF_WEEK);

            System.out.print(diffDays + " days, ");
            System.out.print(diffDayOfWeek + " weeks, ");
            System.out.print(diffMonth + " months, ");
            System.out.print(diffYear + " years.");

        } catch (ParseException e) {
        }

    }

    public static String LCase1(String GenerateDate) {

        String Left = GenerateDate.substring(0, 5);

        return Left;

    }

    public static String calcDate(String dateInString) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        dateInString = "7-Jun-2013";

        try {

                Date date = formatter.parse(dateInString);
                System.out.println(date);
                System.out.println(formatter.format(date));

        } catch (ParseException e) {
        }
        return dateInString;

    }

    // Date = 07/06/2013
    public static void calcDate1(String dateInString) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateInString = "07/06/2013";

        try {

                Date date = formatter.parse(dateInString);
                System.out.println(date);
                System.out.println(formatter.format(date));

        } catch (ParseException e) {
        }

    }

    public static String Year(String dateInString) {
        String r = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {

                Date date = formatter.parse(dateInString);
                int result = -1;
                if (date != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        result = cal.get(Calendar.YEAR);
                        r = String.valueOf(result);
                }

        } catch (ParseException e) {
                r = e.getMessage();
        }
        return r;
    }

    public static int Month(Date date) {
        //Date date = formatter.parse(dateInString);
        int result;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            result = cal.get(Calendar.MONTH);
            
            return result + 1;
        }
        return 0;
    }

    public static String Day(String dateInString) {

        String r = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {

                Date date = formatter.parse(dateInString);
                int result;
                if (date != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        result = cal.get(Calendar.DAY_OF_MONTH);

                        r = String.valueOf(result);
                }

        } catch (ParseException e) {
                r = e.getMessage();
        }
        return r;
    }

    public static String Hour(String dateInString) {

        String r = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {

                Date date = formatter.parse(dateInString);
                int result;
                if (date != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        result = cal.get(Calendar.HOUR_OF_DAY);

                        r = String.valueOf(result);
                }

        } catch (ParseException e) {
                r = e.getMessage();
        }
        return r;
    }

    public static String Minute(String dateInString) {
            String r = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {

                    Date date = formatter.parse(dateInString);
                    int result;
                    if (date != null) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            result = cal.get(Calendar.MINUTE);

                            r = String.valueOf(result);
                    }

            } catch (ParseException e) {
                    r = e.getMessage();
            }
            return r;
    }

    public static String Second(String dateInString) {
            String r = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {

                    Date date = formatter.parse(dateInString);
                    int result;
                    if (date != null) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            result = cal.get(Calendar.SECOND);

                            r = String.valueOf(result);
                    }

            } catch (ParseException e) {
                    r = e.getMessage();
            }
            return r;
    }

    public static Date DateValue(String dateInString) {
        Date s = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {

                s = (Date) formatter.parse(dateInString);
                System.out.println(formatter.format(s));

        } catch (ParseException e) {
                s = null;
        }
        return s;
    }

    public static String TimeValue(String dateInString) {
        String f = null;
        String day2;
        String day;
        Date s = null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<String> openitem2 = new ArrayList<String>();

        try {

            s = (Date) formatter.parse(dateInString);
            System.out.println(formatter.format(s));

            List<String> openitem = new ArrayList<String>();
            openitem.add(formatter.format(s));
            String t = openitem.get(0);
            String[] tokens = t.split("\\s");
            day = tokens[1];
            System.out.println(day);
            openitem2.add(day);
            String t2 = openitem2.get(0);
            String[] tokens2 = t2.split("\\:");
            day2 = tokens2[0];
            String d3 = tokens2[1];
            String d4 = tokens2[2];

            System.out.println(day2);

            int d = Integer.valueOf(day2);
            if (d >= 12) {

                    f = String.valueOf(d - 12 + ":" + d3 + ":" + d4 + "PM");

                    System.out.println(f);

            } else {

                    f = String.valueOf(d + ":" + d3 + ":" + d4 + ":" + "AM");
                    System.out.println(f);
            }
        } catch (ParseException e) {
                f = null;
        }
        return f;

    }

    public static void calcDate3(String dateInString) {

        SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");
        dateInString = "Fri, June 7 2013";

        try {

                Date date = formatter.parse(dateInString);
                System.out.println(date);
                System.out.println(formatter.format(date));

        } catch (ParseException e) {
                e.printStackTrace();
        }

    }

    public static void calcDate4(String dateInString) {

        SimpleDateFormat formatter = new SimpleDateFormat(
                        "EEEE, MMM dd, yyyy HH:mm:ss a");
        dateInString = "Friday, Jun 7, 2013 12:10:56 PM";

        try {

                Date date = formatter.parse(dateInString);
                System.out.println(date);
                System.out.println(formatter.format(date));

        } catch (ParseException e) {
        }

    }

    public static int Asc(final char character) {
        System.out.println((int) character);
        return (int) character;
    }

    public static int Asc(final int ascii) {
        System.out.println((char) ascii);
        return (char) ascii;
    }

    // Return True / False Where Space is Exit Or Not
    public static boolean whiteSpace(String text) {

        boolean containsWhitespace = false;
        for (int i = 0; i < text.length() && !containsWhitespace; i++) {
                if (Character.isWhitespace(text.charAt(i))) {
                        containsWhitespace = true;

                }
                System.out.println(containsWhitespace);
        }
        return containsWhitespace;

    }

    public static String Left(String left, int length) {

        String Left = left.substring(0, length);

        return Left;
    }

    public static String Right(String right, int length) {

        String Right = right.substring(right.length() - length);

        return Right;
    }

    public static void Mid1(String mid, String start, int length) {
        String s = (mid.replaceAll(" ", ""));
        int indexOfP = s.indexOf(start);
        String result = s.substring(indexOfP, indexOfP + length);
        System.out.println(result); // prints 'position'
    }

    public static void Mid(String mid, int start, int length) {

        char a_char = mid.charAt(start - 1);
        int indexOfP = mid.indexOf(a_char);
        String result = mid.substring(indexOfP, indexOfP + length);
        System.out.println(result);

    }

    public static String Mid11(String mid1, int start, int length) {

        String mid = mid1.substring(start - 1, start - 1 + length);

        return mid;

    }

    public static String ltrim(String s) {

        return LTRIM.matcher(s).replaceAll("");
    }

    public static String rtrim(String s) {
        return RTRIM.matcher(s).replaceAll("");

    }

    public static int Instr(String data, String compare, int pos) {
        while ((pos = data.indexOf(compare, pos)) != -1) {
                pos++;
        }

        int pos1 = data.indexOf(compare, pos);
        return pos1;

    }

    public static String Replace(String data, String find, String replacewith) {

        String newString = data.replaceAll(find, replacewith);

        return newString;

    }

    public static String MonthName(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        num = num - 1;
        if (num >= 0 && num <= 12) {
            month = months[num];

            if (num == 12) {

                    month = "wrong";
            }
        }

        return month;
    }

    public static String trimAll(String s) {
        if (s != null) {

                return s.replaceAll(" ", "");
        }

        return s;
    }

    public static String Space2(int space) {

        StringBuilder sbf = new StringBuilder();
        int s1 = 0;
        int s2;
        char[] sB;
        s1 = (Integer) space;
        s2 = s1 + (s1 + 2) / 3;
        sB = new char[s2];

        System.out.println("Space : " + "S" + sbf.append(sB) + "T");
        return null;
    }

    public static String Format(String data) {

        int i = Integer.parseInt(data);
        String formatted = String.format("%08d", i);
        System.out.println(formatted);

        return formatted;

    }

    public static int Weekday(String dayOfWeek) {
        try {
                DateFormat formatter;
                Date date;
                formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                date = (Date) formatter.parse(dayOfWeek);
                GregorianCalendar g = new GregorianCalendar();
                g.setTime(date);

                System.out.println(g.get(Calendar.DAY_OF_WEEK));

                return g.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
                System.out.println("Exception :" + e);
        }
        return 0;
    }

    public static String WeekdayName(String dayOfWeek) {
        String day = null;
        try {
            DateFormat formatter;
            Date date;
            formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            date = (Date) formatter.parse(dayOfWeek);
            GregorianCalendar g = new GregorianCalendar();
            g.setTime(date);

            String s = g.getTime().toString();
            System.out.println(s);

            List<String> openitem = new ArrayList<String>();
            openitem.add(s);
            String t = openitem.get(0);
            String[] tokens = t.split("\\s");
            day = tokens[0];

            System.out.println(day);

        } catch (ParseException e) {
                System.out.println("Exception :" + e);
        }
        return day;
    }

    public static String DayFormat(Date date, String format) {
        String day = null;

        String d1 = dateToString(date, format);
        Date d2 = stringToDate(d1, format);
        GregorianCalendar g = new GregorianCalendar();
        g.setTime(d2);
        String s = g.getTime().toString();
        List<String> openitem = new ArrayList<String>();
        openitem.add(s);
        String t = openitem.get(0);
        String[] tokens = t.split("\\s");
        day = tokens[0];

        System.out.println(day);

        return day;
    }

        
}
