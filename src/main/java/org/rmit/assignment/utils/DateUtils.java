package org.rmit.assignment.utils;

import java.sql.Date;

public class DateUtils {

    public static Date convertStringToDate(String examDate) {
        //Check if examDate is in correct format yyyy-MM-dd
        if (examDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return Date.valueOf(examDate);
        } else {
            return null;
        }
    }
}
