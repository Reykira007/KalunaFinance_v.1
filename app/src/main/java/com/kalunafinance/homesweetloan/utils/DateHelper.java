package com.kalunafinance.homesweetloan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, new Locale("id", "ID"));
        return sdf.format(new Date());
    }

    public static String formatDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, new Locale("id", "ID"));
            Date parsedDate = sdf.parse(date);
            return sdf.format(parsedDate);
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.MONTH_FORMAT, new Locale("id", "ID"));
        return sdf.format(new Date());
    }
}
