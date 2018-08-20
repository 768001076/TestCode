package datasource;

public enum DataSourceTypeEnum {
    LOCAL_TEST(1, "测试", "local_test"), LOCAL_MYTEST(2, "牛旭武测试", "local_mytest");

    private int dataSourceNo;

    private String remark;

    private String prefix;

    DataSourceTypeEnum(int dataSourceNo, String remark, String prefix) {
        this.dataSourceNo = dataSourceNo;
        this.remark = remark;
        this.prefix = prefix;
    }

    public int getDataSourceNo() {
        return dataSourceNo;
    }

    public void setDataSourceNo(int dataSourceNo) {
        this.dataSourceNo = dataSourceNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
