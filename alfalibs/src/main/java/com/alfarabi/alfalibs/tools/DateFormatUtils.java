package com.alfarabi.alfalibs.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alfarabi on 10/9/17.
 */

public class DateFormatUtils {

    public static Date toDate(String in, String format){
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(in);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
