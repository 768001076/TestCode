package jsontest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.tomas.proxy.base.bean.ProxyBean;
import net.tomas.proxy.base.bean.RepMessageBean;

public class JSONgetValueTest {

    public static JSONObject jjj = new JSONObject();

    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject("{\"isDropPassenger\":false,\"consumptionVacancySum\":0,\"lishi\":\"02:49\",\"submitOrderIP\":\"61.132.238.115\",\"CycleStepsInfo\":{\"stepsInfo\":\"[{\\\"endTimeValue\\\":\\\"2018-05-22 14:20:55.649\\\",\\\"costValue\\\":218,\\\"stepName\\\":\\\"查询订单\\\"},{\\\"endTimeValue\\\":\\\"2018-05-22 14:20:55.649\\\",\\\"costValue\\\":0,\\\"stepName\\\":\\\"查询结果解析\\\"},{\\\"endTimeValue\\\":\\\"2018-05-22 14:20:55.811\\\",\\\"costValue\\\":162,\\\"stepName\\\":\\\"登录\\\"},{\\\"endTimeValue\\\":\\\"2018-05-22 14:20:55.976\\\",\\\"costValue\\\":165,\\\"stepName\\\":\\\"获取所有乘客,并添加常旅客\\\"},{\\\"endTimeValue\\\":\\\"2018-05-22 14:20:56.230\\\",\\\"costValue\\\":153,\\\"stepName\\\":\\\"购票前检查\\\"},{\\\"endTimeValue\\\":\\\"2018-05-22 14:20:58.230\\\",\\\"costValue\\\":2000,\\\"stepName\\\":\\\"购票前检查-查询乘客睡眠时间\\\"},{\\\"endTimeValue\\\":\\\"2018-05-22 14:21:00.231\\\",\\\"costValue\\\":2000,\\\"stepName\\\":\\\"提交订单休眠时间\\\"},{\\\"endTimeValue\\\":\\\"2018-05-22 14:21:02.412\\\",\\\"costValue\\\":2180,\\\"stepName\\\":\\\"乘客下单\\\"},{\\\"endTimeValue\\\":\\\"2018-05-22 14:21:02.413\\\",\\\"costValue\\\":6436,\\\"stepName\\\":\\\"下单总时间\\\"}]\",\"failMsg\":\"余票不足,请重新查询车票信息！\",\"createOrderMode\":\"3\",\"REPServer\":\"103.37.144.20|172.16.108.255|127.0.0.1\",\"createOrderModeInfo\":\"http://www.12306.cn\"},\"accountPassengerCount\":15,\"success\":false,\"msg\":\"余票不足,请重新查询车票信息！\"}");
        JSONObject datas = JSONObject.parseObject(jsonObject.remove("CycleStepsInfo").toString());
        for (Object data : JSONArray.parseArray(datas.getString("stepsInfo"))) {
            System.out.println(data.toString());

        }

    }

}
