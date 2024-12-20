package com.pp.time;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2021/9/3       create this file
 * </pre>
 */
public class TimeTest {

    /**
     * 不符合格式要求的，可以使用两个''包裹起来
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日' EEE HH'时'mm'分'ss'秒' 'CST'",
            Locale.CHINA);

    public static void main(String[] args) {

        Long timeLong = 1725206399000L;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeLong);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(calendar.getTime()));

        //

    }

    @Test
    public void comparePastDate() throws ParseException {
        String oldDate = "2021-06-21";
        String nowDate = "2021-09-30";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date old;
        int day = 0;

        old = sdf.parse(oldDate);
        calendar.setTime(old);
        Long oTime = calendar.getTimeInMillis();

        Date now = sdf.parse(nowDate);
        calendar.setTime(now);
        Long nTime = calendar.getTimeInMillis();

        day = (int) ((nTime - oTime) / (3600F * 1000 * 24));

        System.out.println("相差：" + day);
    }


    @Test
    public void testPrintTimeFromLong() {
        long timeLong = 1732069822690L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date time = new Date(timeLong);
        System.out.println(sdf.format(time));
    }


}
