package com.project.toy.batch.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
public class DateUtil {

    /**
     * 현재 시간을 원하는 형태로 반환
     *
     * @param format
     * @return
     */
    public static String getTime(final String format){
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        return sf.format(calendar.getTime());
    }

}
