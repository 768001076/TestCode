package datatest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTest {

    public static SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        //毫秒转日期
        long ms = 1524526402232l;
        String s = msToData(ms);
        System.out.println(s);
    }

    public static String msToData(long ms){
        Date date = new Date(ms);
        String format = YYYY_MM_DD_HH_MM.format(date);
        return format;
    }

}
