package nxw;

import java.util.Random;

import com.alibaba.fastjson.JSONObject;

public class aaa extends Thread {

    private String[] surname = { "赵", "李", "王", "任", "马", "孙", "陈", "吴" };

    private String[] boy = { "晨", "轩", "清", "睿", "宝", "涛", "华", "国", "亮", "新", "凯", "志", "明", "伟", "嘉", "东", "洪", "月",
            "士", "洋", "欣", "升", "恩", "迅", "科", "富", "函", "业", "胜", "震", "福", "瀚", "瑞", "朔" };

    private String[] girl = { "婷", "倩", "睿", "瑜", "嘉", "君", "盈", "男", "萱", "雨", "乐", "欣", "悦", "雯", "晨", "珺", "月", "茗",
            "晗", "甜", "晴", "亭", "吉", "玉", "晶", "妍", "凤", "蒙", "霖", "希", "宣", "昕", "丽", "心" };

    private int i;

    public aaa(int i) {
        super();
        this.i = i;
    }

    @Override public void run() {
        String s = "{\"idType\":\"1\",\"bankNo\":\"NQ+Rleb0iZk=\",\"bankMobileNum\":\"NQ+Rleb0iZk=\",\"price\":5.5,"
                + "\"systemType\":2,\"mobileNum\":\"JofbVc6D/Lsfacb8Gongtg==\",\"changeFlag\":0,"
                + "\"passengerName\":\"%E7%BD%97%E8%8B%B1\",\"orderId\":133232322,\"idNumber\":\"650102196208021620\"}";
        JSONObject parseObject = JSONObject.parseObject(s);
        Long long1 = parseObject.getLong("orderId");
        String string = parseObject.getString("idNumber");
        String substring = string.substring(0, 14);
        parseObject.put("idNumber",
                substring + new Random().nextInt(10) + new Random().nextInt(10) + new Random().nextInt(10)
                        + new Random().nextInt(10));
        parseObject.put("orderId", long1 + i);
        try {
            String retStr = SendPostAndGetNanJing.sendHttpMsg("http://paycard.hangtian123.net/PayCard/obtain",
                    "jsonStr=" + parseObject.toJSONString());
            System.out.println(retStr);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    String getNamesss () {
        int surnameIndex = new Random().nextInt(8);
        int sex = new Random().nextInt(2);
        int nameLength = new Random().nextInt(2) + 1;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(surname[surnameIndex]);
        if (sex == 1) {
            for (int i = 0; i < nameLength; i++) {
                stringBuffer.append(boy[new Random().nextInt(34)]);
            }
        }
        else {
            for (int i = 0; i < nameLength; i++) {
                stringBuffer.append(girl[new Random().nextInt(34)]);
            }
        }
        return stringBuffer.toString();
    }
}
