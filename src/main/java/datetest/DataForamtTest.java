package datetest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 日期格式化测试
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/5/29
 */
public class DataForamtTest {

    public static SimpleDateFormat YYYY_MM_DD_HH_MM_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS");

    public static SimpleDateFormat YYYYMMDDHHMMSSS = new SimpleDateFormat("yyyyMMddHHmmSSS");

    public static void main(String[] args) {
        System.out.println(YYYY_MM_DD_HH_MM_SSS.format(new Date()).toString());
        System.out.println(YYYYMMDDHHMMSSS.format(new Date()).toString());
    }

}
