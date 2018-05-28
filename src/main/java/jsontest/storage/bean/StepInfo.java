package jsontest.storage.bean;

/**
 * @Description: 步骤信息
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/5/23
 */
public class StepInfo {

    private int costValue;//步骤耗时

    private String endTimeValue;//步骤结束时间

    private String stepName;//步骤名称

    public int getCostValue() {
        return costValue;
    }

    public void setCostValue(int costValue) {
        this.costValue = costValue;
    }

    public String getEndTimeValue() {
        return endTimeValue;
    }

    public void setEndTimeValue(String endTimeValue) {
        this.endTimeValue = endTimeValue;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
}
