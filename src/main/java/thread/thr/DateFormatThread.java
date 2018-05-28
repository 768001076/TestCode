package thread.thr;

import uitl.time.TimeUtil;

import java.text.ParseException;
import java.util.Random;

public class DateFormatThread extends Thread {

    @Override public void run() {
        try {
            sleep(new Random().nextInt(3000));
            System.out.println(TimeUtil.yyyy_MM_dd_HH_mm_ss.parse("2018-05-23 16:39:14"));
        }
        catch (Exception e) {
        }
    }
}
