package com.panpan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/29       create this file
 * </pre>
 */
public class Test01 {


    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        System.out.println("first:" + sdf.format(calendar.getTime()));

        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        System.out.println("change:" + sdf.format(calendar.getTime()));


        System.out.println(calendar.getTime().getTime());


        long time = 1735782665670L;
        System.out.println(sdf.format(new Date(time)));

    }

}
