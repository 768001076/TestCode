package jsontest.storage;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import jsontest.storage.bean.CreateOrderDepltionTime;

/**
 * @Description: 存储下单耗时信息
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/5/23
 */
public class CreateOrderDepltionStorageRam {

	 private static Map<Long,CreateOrderDepltionTime> depltionInfo = new HashMap<Long, CreateOrderDepltionTime>();

	    public static void startCreateOrder(long orderId,int orderType){
	        synchronized (CreateOrderDepltionStorageRam.class) {
	            depltionInfo.put(orderId,new CreateOrderDepltionTime(orderId,orderType));
	        }
	    }

	    public static void setAgentId(long orderId,long agentId){
	        synchronized (CreateOrderDepltionStorageRam.class) {
	            depltionInfo.get(orderId).setAgentID(agentId);
	        }
	    }

	    public static void setCycleInfo(long orderId,String cycleInfo){
	        synchronized (CreateOrderDepltionStorageRam.class) {
	            depltionInfo.get(orderId).setCycleStepsInfo(cycleInfo);
	        }
	    }

	    public static String getCreateOrderDepltionInfo(long orderId){
	        synchronized (CreateOrderDepltionStorageRam.class) {
	        	depltionInfo.get(orderId).setCreateOrderFinishTime();
	            return depltionInfo.remove(orderId).toJsonString();
	        }
	    }


	
}
