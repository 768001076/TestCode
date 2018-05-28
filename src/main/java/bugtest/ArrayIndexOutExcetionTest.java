package bugtest;

import java.util.HashMap;
import java.util.Map;

public class ArrayIndexOutExcetionTest {

    public static void main(String[] args) {

        String oldcookie = "current_captcha_type=Z; JSESSIONID=73D1B26A40C70B66BEBECF54FF8C7F26; "
                + "BIGipServerotn=938476042.64545.0000; route=c5c62a339e7744272a54643b3be5bf64";

        String newcookie = "current_captcha_type=Z; JSESSIONID=C196D59087289374149091FA9E57C402; "
                + "BIGipServerotn=2213019914.64545.0000; route=c5c62a339e7744272a54643b3be5bf64";

        System.out.println(compareMap(cookiesMap(oldcookie),cookiesMap(newcookie)));

    }


    private static Map<String, String> cookiesMap(String cookies) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        String[] cookiesStrs = cookies.split("; ");
        for (String cookie : cookiesStrs) {
            String[] cookieStrs = cookie.split("=");
            cookieMap.put(cookieStrs[0], cookieStrs[1]);
        }
        return cookieMap;
    }

    private static boolean compareMap(Map<String, String> mapOne,
            Map<String, String> mapTwo) {
        boolean contain = false;
        for (Object o : mapOne.keySet()) {
            contain = mapTwo.containsKey(o);
            if (contain) {
                contain = mapOne.get(o).equals(mapTwo.get(o));
            }
            if (!contain) {
                return false;
            }
        }
        return true;
    }

}
