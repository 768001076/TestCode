package stringtest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringMethodTest {

    public static void main(String[] args) {
        //TODO 字符串开头测试
        //System.out.println("https://mapi.alipay.com/gateway.do".startsWith("https"));
        //TODO URL中文编码
        try {
            String san = URLEncoder.encode("三","UTF-8");
            System.out.println(san);
            System.out.println(URLDecoder.decode(san,"UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
