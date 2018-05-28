package jsontest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONCreateUtil {

    public static void main(String[] args) {
        CreateFailOrderMonitoringJSON();
    }

    private static void CreateFailOrderMonitoringJSON() {
        JSONObject failOrderInfo = new JSONObject();
        JSONArray cycles = new JSONArray();
        failOrderInfo.put("CreateOrderStartTime", "2018-05-14 15:02");//开始时间 yyyy-MM-dd HH:mm
        failOrderInfo.put("CreateOrderFinishTime", "2018-05-14 15:03");//结束时间 yyyy-MM-dd HH:mm
        failOrderInfo.put("CreateOrderDepletionTime", 75326);//订单总耗时 ms
        failOrderInfo.put("OrderID", 132170940);//订单ID
        failOrderInfo.put("AgentID", 57);//采购商ID
        failOrderInfo.put("CreateOrderType", 1);//下单类型 1代购 2云监控 3托管
        failOrderInfo.put("CycleCount", 1);//循环次数
        String demoData = "{\"agentId\":30,\"createOrderChannel\":1,\"createOrderMode\":2,"
                + "\"createOrderModeInfo\":\"125.120.205.191\",\"createOrderTime\":\"18-05-14 16:24~18-05-14 16:24\","
                + "\"createOrderType\":1,\"cycleCount\":1,\"datas\":[{\"round\":1,"
                + "\"steps\":[{\"costKey\":\"parseDataCost\",\"costValue\":1,\"endTimeKey\":\"parseDataEndtime\","
                + "\"endTimeValue\":\"2018-05-14 16:24:22.22\",\"stepName\":\"解析数据\",\"stepNo\":2001},"
                + "{\"costKey\":\"initQueryPageCost\",\"costValue\":400,\"endTimeKey\":\"initQueryPageEndtime\","
                + "\"endTimeValue\":\"2018-05-14 16:24:22.62\",\"stepName\":\"init查询页初始化\",\"stepNo\":2002},"
                + "{\"costKey\":\"getDynamicCost\",\"costValue\":272,\"endTimeKey\":\"getDynamicEndtime\","
                + "\"endTimeValue\":\"2018-05-14 16:24:22.892\",\"stepName\":\"获取页面源码动态密钥\",\"stepNo\":2003},"
                + "{\"costKey\":\"queryLeftTicketsCost\",\"costValue\":6233,"
                + "\"endTimeKey\":\"queryLeftTicketsEndtime\",\"endTimeValue\":\"2018-05-14 16:24:29.125\","
                + "\"stepName\":\"查询余票\",\"stepNo\":2004},{\"costKey\":\"checkOnlineCost\",\"costValue\":332,"
                + "\"endTimeKey\":\"checkOnlineEndtime\",\"endTimeValue\":\"2018-05-14 16:24:29.457\","
                + "\"stepName\":\"查是否在线\",\"stepNo\":2005},{\"costKey\":\"submitBookingCost\",\"costValue\":259,"
                + "\"endTimeKey\":\"submitBookingEndtime\",\"endTimeValue\":\"2018-05-14 16:24:29.716\","
                + "\"stepName\":\"提交预订\",\"stepNo\":2007},{\"costKey\":\"orderPageHtmlInitCost\",\"costValue\":739,"
                + "\"endTimeKey\":\"orderPageHtmlInitEndtime\",\"endTimeValue\":\"2018-05-14 16:24:30.455\","
                + "\"stepName\":\"订单填写页初始化\",\"stepNo\":2008},{\"costKey\":\"passengerInfoJsCost\",\"costValue\":313,"
                + "\"endTimeKey\":\"passengerInfoJsEndtime\",\"endTimeValue\":\"2018-05-14 16:24:30.768\",\"stepName\":\"乘客信息JS\",\"stepNo\":2009},{\"costKey\":\"passengerListCost\",\"costValue\":1,\"endTimeKey\":\"passengerListEndtime\",\"endTimeValue\":\"2018-05-14 16:24:30.769\",\"stepName\":\"乘客列表\",\"stepNo\":2010},{\"costKey\":\"viewLeftTicketsCost\",\"costValue\":0,\"endTimeKey\":\"viewLeftTicketsEndtime\",\"endTimeValue\":\"2018-05-14 16:24:30.769\",\"stepName\":\"查看余票\",\"stepNo\":2011},{\"costKey\":\"addPassengersCost\",\"costValue\":391,\"endTimeKey\":\"addPassengersEndtime\",\"endTimeValue\":\"2018-05-14 16:24:31.16\",\"stepName\":\"添加乘客\",\"stepNo\":2012},{\"costKey\":\"getDynamicCost\",\"costValue\":267,\"endTimeKey\":\"getDynamicEndtime\",\"endTimeValue\":\"2018-05-14 16:24:31.427\",\"stepName\":\"获取页面源码动态密钥\",\"stepNo\":2003},{\"costKey\":\"dwonPictureCost\",\"costValue\":144,\"endTimeKey\":\"dwonPictureEndtime\",\"endTimeValue\":\"2018-05-14 16:24:31.571\",\"stepName\":\"下载图片\",\"stepNo\":2013},{\"costKey\":\"checkOrderInfoCost\",\"costValue\":267,\"endTimeKey\":\"checkOrderInfoEndtime\",\"endTimeValue\":\"2018-05-14 16:24:31.838\",\"stepName\":\"校验订单信息\",\"stepNo\":2018},{\"costKey\":\"viewNumberLineCost\",\"costValue\":271,\"endTimeKey\":\"viewNumberLineEndtime\",\"endTimeValue\":\"2018-05-14 16:24:32.109\",\"stepName\":\"查看排队数\",\"stepNo\":2020},{\"costKey\":\"afterQueueSleepTimeCost\",\"costValue\":704,\"endTimeKey\":\"afterQueueSleepTimeEndtime\",\"endTimeValue\":\"2018-05-14 16:24:32.814\",\"stepName\":\"排队统计后睡眠时间\",\"stepNo\":2021},{\"costKey\":\"submitOrderCost\",\"costValue\":334,\"endTimeKey\":\"submitOrderEndtime\",\"endTimeValue\":\"2018-05-14 16:24:33.148\",\"stepName\":\"提交订单\",\"stepNo\":2022},{\"costKey\":\"suborderTrueSleepTimeCost\",\"costValue\":0,\"endTimeKey\":\"suborderTrueSleepTimeEndtime\",\"endTimeValue\":\"2018-05-14 16:24:33.148\",\"stepName\":\"提交订单成功，扫描提交结果睡眠时间\",\"stepNo\":2023},{\"costKey\":\"queryOrderCost\",\"costValue\":311,\"endTimeKey\":\"queryOrderEndtime\",\"endTimeValue\":\"2018-05-14 16:24:33.459\",\"stepName\":\"查询订单\",\"stepNo\":2024}]},{\"round\":2,\"steps\":[{\"costKey\":\"suborderTrueSleepTimeCost\",\"costValue\":2001,\"endTimeKey\":\"suborderTrueSleepTimeEndtime\",\"endTimeValue\":\"2018-05-14 16:24:35.46\",\"stepName\":\"提交订单成功，扫描提交结果睡眠时间\",\"stepNo\":2023},{\"costKey\":\"queryOrderCost\",\"costValue\":288,\"endTimeKey\":\"queryOrderEndtime\",\"endTimeValue\":\"2018-05-14 16:24:35.748\",\"stepName\":\"查询订单\",\"stepNo\":2024},{\"costKey\":\"queueResultCost\",\"costValue\":368,\"endTimeKey\":\"queueResultEndtime\",\"endTimeValue\":\"2018-05-14 16:24:36.116\",\"stepName\":\"排队结果\",\"stepNo\":2025},{\"costKey\":\"checkUnfinishedOrderCost\",\"costValue\":302,\"endTimeKey\":\"checkUnfinishedOrderEndtime\",\"endTimeValue\":\"2018-05-14 16:24:36.418\",\"stepName\":\"查未完成订单\",\"stepNo\":2026},{\"costKey\":\"deletePassengerCost\",\"costValue\":8,\"endTimeKey\":\"deletePassengerEndtime\",\"endTimeValue\":\"2018-05-14 16:24:36.427\",\"stepName\":\"删除待核验乘客\",\"stepNo\":2027},{\"costKey\":\"callBackSuccesCost\",\"costValue\":13,\"endTimeKey\":\"callBackSuccesEndtime\",\"endTimeValue\":\"2018-05-14 16:24:36.44\",\"stepName\":\"成功回调\",\"stepNo\":2028},{\"costKey\":\"repCreateOrderCost\",\"costValue\":0,\"endTimeKey\":\"repCreateOrderEndtime\",\"endTimeValue\":\"2018-05-14 16:24:36.44\",\"stepName\":\"rep下单结束时间\",\"stepNo\":2030}]}],\"failMsg\":\"提交订单成功，12306订单号为EK00528948。\",\"orderId\":123456,\"repIP\":\"101.236.40.94\",\"totalTimeConsuming\":14396,\"success\":true}";
        JSONObject dataJson = JSONObject.parseObject(demoData);
        JSONArray datas = JSONArray.parseArray(dataJson.getString("datas"));
        JSONObject cycleinfo = new JSONObject();
        cycleinfo.put("createOrderMode", dataJson.getIntValue("createOrderMode"));
        cycleinfo.put("createOrderModeInfo", dataJson.getString("createOrderModeInfo"));
        cycleinfo.put("REPServer",dataJson.getString("repIP"));
        cycleinfo.put("CycleDepletionTime",dataJson.getIntValue("totalTimeConsuming"));
        JSONArray stepsInfo = new JSONArray();
        for (Object data : datas) {
            for (Object steps : JSONArray.parseArray(JSONObject.parseObject(data.toString()).getString("steps"))) {
                JSONObject stepsJson = JSONObject.parseObject(steps.toString());
                stepsJson.remove("costKey");
                stepsJson.remove("endTimeKey");
                stepsJson.remove("stepNo");
                System.out.println(stepsJson.getString("stepName"));
                stepsInfo.add(stepsJson);
            }

        }
        cycleinfo.put("stepsInfo",stepsInfo);
        cycleinfo.put("CreateOrderChannel", 1);//下单渠道 1WEB 2APP
        cycleinfo.put("failMsg",dataJson.getString("failMsg"));
        cycles.add(cycleinfo);
        failOrderInfo.put("CycleStepsInfo",cycles);
        System.out.println(failOrderInfo.toString());

    }

}
