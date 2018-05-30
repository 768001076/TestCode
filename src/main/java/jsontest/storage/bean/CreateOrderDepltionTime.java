package jsontest.storage.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.xml.crypto.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 下单耗时
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/5/23
 */
public class CreateOrderDepltionTime {

    private long agentID;//采购商ID

    private long createOrderDepletionTime;//下单耗时

    private String createOrderStartTime;//下单开始时间

    private String createOrderFinishTime;//下单结束时间

    private long createOrderStartTimeSel;//下单开始时间查询条件

    private long createOrderFinishTimeSel;//下单结束时间查询条件

    private int createOrderType;//下单类型 1代购 2云监控 3托管

    private int cycleCount = 0;//循环次数
    
    private int createOrderChannel;//下单渠道 1WEB 2APP

    private int createOrderMode;//下单模式 1CDN 2代理 3默认DNS

    private String failMsg;//原因

    private long orderID;//订单ID
    
    private int channel = 2;//代表数据从时佳磊发出来

    private List<CycleInfo> cycleStepsInfo = new ArrayList<CycleInfo>();//循环信息

    public long getAgentID() {
        return agentID;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

    public long getCreateOrderDepletionTime() {
        return createOrderDepletionTime;
    }

    public String getCreateOrderStartTime() {
        return createOrderStartTime;
    }

    public void setCreateOrderStartTime() {
        Date date = new Date();
        createOrderDepletionTime = date.getTime();
        createOrderStartTime = getNowTime(date);
        this.createOrderStartTimeSel = getNowTimeSel(date);
    }

    public String getCreateOrderFinishTime() {
        return createOrderFinishTime;
    }

    public void setCreateOrderFinishTime() {
        Date date = new Date();
        createOrderDepletionTime = date.getTime() - createOrderDepletionTime;
        createOrderFinishTime = getNowTime(date);
        createOrderFinishTimeSel = getNowTimeSel(date);
    }

    public int getCreateOrderType() {
        return createOrderType;
    }

    public void setCreateOrderType(int createOrderType) {
        this.createOrderType = createOrderType;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getCycleStepsInfo() {
        String s = null;
        try {
            s = JSONArray.toJSONString(this.cycleStepsInfo);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void setCycleStepsInfo(String cycleStepsInfoStr) {
        List<CycleInfo> cycleStepsInfoAr = JSONArray.parseArray(cycleStepsInfoStr, CycleInfo.class);
        if (cycleStepsInfoAr != null){
            cycleCount += cycleStepsInfoAr.size();
            this.cycleStepsInfo.addAll(cycleStepsInfoAr);
            CycleInfo cycleInfo = cycleStepsInfoAr.get(cycleStepsInfoAr.size()-1);
            setFailMsg(cycleInfo.getFailMsg());
            setCreateOrderChannel(cycleInfo.getCreateOrderChannel());
            setCreateOrderMode(cycleInfo.getCreateOrderMode());
        }
    }

    private String getNowTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String now = simpleDateFormat.format(date);
        return now;
    }

    private long getNowTimeSel(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmSSS");
        long now = Long.parseLong(simpleDateFormat.format(date));
        return now;
    }

    public String toJsonString(){
        try {
            return JSONObject.toJSONString(this);
        }
        catch (Exception e) {
            return "";
        }
    }

    public CreateOrderDepltionTime(long orderID, int orderType) {
        setCreateOrderStartTime();
        setOrderID(orderID);
        setCreateOrderType(orderType);
    }

	public int getCreateOrderChannel() {
		return createOrderChannel;
	}

	public void setCreateOrderChannel(int createOrderChannel) {
		this.createOrderChannel = createOrderChannel;
	}

	public int getCreateOrderMode() {
		return createOrderMode;
	}

	public void setCreateOrderMode(int createOrderMode) {
		this.createOrderMode = createOrderMode;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

    public long getCreateOrderStartTimeSel() {
        return createOrderStartTimeSel;
    }

    public long getCreateOrderFinishTimeSel() {
        return createOrderFinishTimeSel;
    }

}
