package db;

import com.ccservice.Util.db.*;

/**
 * @Description: SQL测试
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/3/16
 */
public class SqlTest {

    public static void main(String[] args) {
        SqlTest sqlTest = new SqlTest();
        DataTable data = sqlTest.getData("exec getPropertyMessageByGroupNew @updateTime='',@isSelectAll=1,@GroupName=4,@OutherName='PASSENGER_JS_TIMEOUT'");
        sqlTest.SystemResult(data);
    }

    public void SystemResult(DataTable dataTable){
        for (DataRow row : dataTable.GetRow()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (DataColumn column : row.GetColumn()) {
                stringBuffer.append(column.GetKey() + ":" +  column.GetValue() + ",");
            }
            System.out.println(stringBuffer.toString());
        }

    }

    public DataTable getData(String sql){
        DataTable dataTable = null;
        try {
            dataTable = DBHelper.GetDataTable(sql, DBSourceEnum.KONGTIE_DB);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dataTable;
    }

}
