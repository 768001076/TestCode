package jsontest;

import com.alibaba.fastjson.JSONArray;

public class PassengerFastJsonExceptionTest {

    public static void main(String[] args) {
        String passengers = "严敏芳|1|36070219930410062X|1|硬卧|3880";
        JSONArray objects = JSONArray.parseArray(passengers);
        System.out.println(objects);
    }

}
