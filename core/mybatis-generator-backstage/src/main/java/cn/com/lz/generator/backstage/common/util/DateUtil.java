package cn.com.lz.generator.backstage.common.util;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String FORMAT_YMDHM = "yyy-MM-dd HH:mm";

    private static final String FORMAT_YMDHMS = "yyy-MM-dd HH:mm:ss";

    private static final String FORMAT_YMD = "yyy-MM-dd";

    private static final String FORMAT_YMD_APM_HM = "yyyy年MM月dd日 ahh:mm";

    private static final String FORMAT_YMD_APM = "yyyy年MM月dd日";

    public static String nowYMDHMSM(){
        return now("yyyyMMddhhmmssSSS");
    }

    public static String now(String format){
        String now = DateTime.now().toString(format);
        return now;
    }

    public static Date formatYMDHM(String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YMDHM);
        Date date = sdf.parse(format);
        return date;
    }

    public static Date formatYMDAPMHM(String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YMD_APM_HM);
        Date date = sdf.parse(format);
        return date;
    }

    public static Date formatYMDAPM(String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YMD_APM);
        Date date = sdf.parse(format);
        return date;
    }

    public static String parseYMDHM(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YMDHM);
        String dt = sdf.format(date);
        return dt;
    }

    public static String parseYMDAPMHM(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YMD_APM_HM);
        String dt = sdf.format(date);
        return dt;
    }

    public static String parseYMDAPM(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YMD_APM);
        String dt = sdf.format(date);
        return dt;
    }

    public static String DateToStringBeginOrEnd(Date date,Boolean flag) {
        String time = null;
        SimpleDateFormat dateformat1 = new SimpleDateFormat(FORMAT_YMDHMS);
        Calendar calendar1 = Calendar.getInstance();
        //获取某一天的0点0分0秒 或者 23点59分59秒
        if (flag == true) {
            calendar1.setTime(date);
            calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                    0, 0, 0);
            Date beginOfDate = calendar1.getTime();
            time = dateformat1.format(beginOfDate);
            System.out.println(time);
        }else{
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date);
            calendar1.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
                    23, 59, 59);
            Date endOfDate = calendar1.getTime();
            time = dateformat1.format(endOfDate);
            System.out.println(time);
        }
        return time;
    }

    public static Date DateToDateBeginOrEnd(Date date,Boolean flag) throws ParseException {
        Date time = null;
        SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_YMDHMS);
        String timeStr = DateToStringBeginOrEnd(date,flag);
        time = dateformat.parse(timeStr);
        return time;
    }
}
