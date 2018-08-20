package nxw.cloud;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:云端分单规则
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/6/28
 */
public class AllotOrderRuleMthod {

    public static Map<String,String> rules = new HashMap<String, String>();


    public static void main(String[] args) {
        AllotOrderRuleMthod allotOrderRuleMthod = new AllotOrderRuleMthod();
        String rule = allotOrderRuleMthod.getRule(56, 3, 3);
        System.out.println(rule);
    }

    public String getRule(int agentid, int ordertype, int interfacetype) {
        initInfo();
        String key = agentid + "-" + ordertype + "-" + interfacetype;
        if (rules.containsKey(key)){
            return rules.get(key);
        }else {
            return rules.get("0-" + ordertype + (interfacetype == 3 ? "-3" : "-0"));
        }
    }

    private void initInfo() {
        rules.put("57-1-6","1[30],2[70]");//淘宝代购先支付
        rules.put("57-3-6","1[35],2[65]");//淘宝托管先支付
        rules.put("57-1-3","1[70],2[30]");//淘宝代购先占座
        rules.put("57-3-3","1[45],2[55]");//淘宝托管先占座
        rules.put("48-1-9","1[25],2[75]");//途牛代购先支付
        rules.put("48-3-9","1[55],2[45]");//途牛托管先支付
        rules.put("48-1-3","1[20],2[80]");//途牛代购先占座
        rules.put("48-3-3","1[50],2[50]");//途牛托管先占座
        rules.put("0-1-0","1[9],2[91]");//其他代购先支付
        rules.put("0-3-0","1[91],2[7]");//其他托管先支付
        rules.put("0-1-3","1[66],2[34]");//其他代购先占座
        rules.put("0-3-3","1[34],2[66]");//其他托管先占座
    }

}
