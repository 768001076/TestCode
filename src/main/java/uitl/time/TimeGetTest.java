package uitl.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeGetTest {

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        String format = simpleDateFormat.format(new Date(1534297705857L));
        System.out.println(format);
    }

}
