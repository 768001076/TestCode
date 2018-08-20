package baozz.properts;

import account.AccountTest;
import com.alibaba.fastjson.JSONObject;
import com.ccservice.Util.db.DBHelper;
import com.ccservice.Util.db.DBSourceEnum;
import com.ccservice.Util.db.DataRow;
import com.ccservice.Util.db.DataTable;
import com.ccservice.Util.file.ExceptionUtil;
import com.ccservice.Util.file.WriteLog;
import com.ccservice.bean.TrainPropertyBean;
import com.ccservice.control.TrainPropertyMessage;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Description: TimeAndNumberTest
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/7/4
 */
public class TimeAndNumberTest {

    public static Logger logger = null;

    static {
        String properties = "log4j.properties";
        try {
            Properties pro = new Properties();
            pro.load(AccountTest.class.getResourceAsStream("/" + properties));
            PropertyConfigurator.configure(pro);
        }
        catch (IOException e) {
        }
        logger = LoggerFactory.getLogger("TimeAndNumberTest");
    }

    public static void initPorperties() {
        String sql = "getPropertyMessageNew @updateTime = '" + new Timestamp(new Date().getTime()).toString()
                + "',@isSelectAll = 1";
        List<TrainPropertyBean> trainPropertyBeans = getTrainPropertyBeans(sql);
        if (trainPropertyBeans != null && !trainPropertyBeans.isEmpty()) {
            //给内存重新赋值
            TrainPropertyMessage.setPropertyBeans(trainPropertyBeans);
        }
    }

    public static List<TrainPropertyBean> getTrainPropertyBeans(String sql) {
        List<TrainPropertyBean> propertyBeans = new ArrayList<TrainPropertyBean>();
        DataTable table = null;
        try {
            table = DBHelper.GetDataTable(sql, DBSourceEnum.DEFAULT_DB);
            if (table != null && table.GetRow().size() > 0) {
                List<DataRow> rows = table.GetRow();
                for (DataRow row : rows) {
                    if (row.GetColumn("notuse") == null) {
                        String propertyName = row.GetColumnString("PropertyName");
                        String propertyValue = row.GetColumnString("PropertyValue");
                        int agentId = row.GetColumnInt("AgentId");
                        int interfaceType = row.GetColumnInt("InterfaceType");
                        int orderType = row.GetColumnInt("OrderType");
                        String remark = row.GetColumnString("Remark");
                        int propertyType = row.GetColumnInt("PropertyType");
                        int businessType = row.GetColumnInt("BusinessType");
                        int pathwayType = row.GetColumnInt("PathwayType");
                        String propertyUpdateTime = row.GetColumnString("PropertyUpdateTime");
                        String groupName = row.GetColumnString("GroupName");
                        TrainPropertyBean trainPropertyBean = new TrainPropertyBean(propertyName, propertyValue,
                                agentId, interfaceType, orderType, remark, propertyUpdateTime.toString(), propertyType,
                                businessType, pathwayType, groupName);
                        propertyBeans.add(trainPropertyBean);
                    }
                }
            }
            logger.info("GetPropertiesInfo:[SQL=" + sql + "],[Properties=" + propertyBeans + "]");
        }
        catch (Exception e) {
            logger.error("GetPropertiesInfoError:[SQL=" + sql + "],[Properties=" + propertyBeans + "]",
                    e.getSuppressed());
            propertyBeans = new ArrayList<TrainPropertyBean>();
        }
        return propertyBeans;
    }

    public static void main(String[] args) {
        TimeAndNumberTest.initPorperties();
        System.out.println(TrainPropertyMessage.getPropertyBeans());
        System.out.println(TrainPropertyMessage.isNeedGetUndoneOrder(2,1,57,0,1,
                "提交订单失败：您的请求过于频繁，请稍后重试！"));
//        testErrorAgainCount();
    }

    private static void testErrorAgainCount() {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            try {
//                System.out.print("请输入失败原因:");
//                String result = scanner.nextLine();
//                JSONObject errorAgainCount = TrainPropertyMessage.getErrorAgainCount(1, 1, 57, 0, 1, result, 123456789);
//                logger.info(result + "[ErrorAgainCount:" + errorAgainCount + "]");
//            }
//            catch (Exception e) {
//                logger.error("获取循环次数异常:", e.getSuppressed());
//            }
//        }
    }

}
