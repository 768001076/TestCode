package jsontest.storage.bean;

import java.util.List;

/**
 * @Description: 循环信息
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/5/23
 */
public class CycleInfo {

    private int createOrderChannel;//下单渠道 1WEB 2APP

    private int cycleDepletionTime;//本次循环耗时

    private String repServer;//服务器

    private int createOrderMode;//下单模式 1CDN 2代理 3默认DNS

    private String createOrderModeInfo;//下单模式信息

    private String failMsg;//原因

    private List<StepInfo> stepsInfo;//步骤信息

    public int getCreateOrderChannel() {
        return createOrderChannel;
    }

    public void setCreateOrderChannel(int createOrderChannel) {
        this.createOrderChannel = createOrderChannel;
    }

    public int getCycleDepletionTime() {
        return cycleDepletionTime;
    }

    public void setCycleDepletionTime(int cycleDepletionTime) {
        this.cycleDepletionTime = cycleDepletionTime;
    }

    public String getRepServer() {
        return repServer;
    }

    public void setRepServer(String repServer) {
        this.repServer = repServer;
    }

    public int getCreateOrderMode() {
        return createOrderMode;
    }

    public void setCreateOrderMode(int createOrderMode) {
        this.createOrderMode = createOrderMode;
    }

    public String getCreateOrderModeInfo() {
        return createOrderModeInfo;
    }

    public void setCreateOrderModeInfo(String createOrderModeInfo) {
        this.createOrderModeInfo = createOrderModeInfo;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public List<StepInfo> getStepsInfo() {
        return stepsInfo;
    }

    public void setStepsInfo(List<StepInfo> stepsInfo) {
        this.stepsInfo = stepsInfo;
    }
}
