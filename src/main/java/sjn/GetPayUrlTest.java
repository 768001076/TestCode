package sjn;

import HttpMethod.HttpsClientUtils;

import java.util.HashMap;
import java.util.Map;

public class GetPayUrlTest {

    public static void main(String[] args) {
        System.out.println(getPayUrlTest("zhangbik7lgtloj8","asd123456","E464660437","33000010"));
    }

    public static String getPayUrlTest(String username,String password,String code,String pay_code){
        Map<String, String> param = new HashMap<>();
        param.put("username",username);
        param.put("password",password);
        param.put("ticket_code",code);
        param.put("pay_code",pay_code);
        HttpsClientUtils httpsClientUtils = new HttpsClientUtils();
        String result = httpsClientUtils.posthttpclientdata("http://101.236.56.70:8000", param, 90000l);
        return result;
    }


}
