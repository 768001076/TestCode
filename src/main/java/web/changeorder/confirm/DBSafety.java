package web.changeorder.confirm;

import com.ccservice.Util.PropertyUtil;
import com.ccservice.Util.db.DBHelper;
import com.ccservice.Util.db.DBSourceEnum;
import com.ccservice.Util.db.DataColumn;
import com.ccservice.Util.db.DataRow;
import com.ccservice.Util.db.DataTable;
import com.ccservice.Util.file.ExceptionUtil;
import com.ccservice.Util.file.WriteLog;
import com.ccservice.b2b2c.base.train.TrainStudentInfo;
import com.ccservice.b2b2c.base.train.Trainorder;
import com.ccservice.b2b2c.base.train.Trainorderchange;
import com.ccservice.b2b2c.base.train.Trainticket;
import com.ccservice.b2b2c.base.trainpassenger.Trainpassenger;
import net.tomas.db.safety.uitl.ReflexDBInfoTOObject;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description: DB安全包
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/2/27
 */
public class DBSafety {

    private Set<String> ff = new HashSet<String>();

    private Float pri;

    public Float getPri() {
        return pri;
    }

    private String[] surname = { "赵", "李", "王", "任", "马", "孙", "陈","吴"};
    private String[] boy = { "晨", "轩", "清", "睿", "宝", "涛", "华", "国", "亮", "新", "凯", "志", "明", "伟", "嘉", "东", "洪",
            "月", "士",
            "洋", "欣", "升", "恩", "迅", "科", "富", "函", "业", "胜", "震", "福", "瀚", "瑞", "朔" };
    private  String[] girl = { "婷", "倩", "睿", "瑜", "嘉", "君", "盈", "男", "萱", "雨", "乐", "欣", "悦", "雯", "晨", "珺", "月",
            "茗", "晗",
            "甜", "晴", "亭", "吉", "玉", "晶", "妍", "凤", "蒙", "霖", "希", "宣", "昕", "丽", "心" };

    public static void main(String[] args) {
        //TODO 获取订单方法测试
        //new DBSafety().getTrainorderbyid();
        //TODO 获取改签订单细信息
        //new DBSafety().getTrainOrderChangeById(271);
        //TODO 改签属性类型测试
        //new DBSafety().testChangeORderFieldTypes();
        //TODO Timestamp类型转String
        /*Object timestamp = new Timestamp(new Date().getTime());
        System.out.println("Before:" + timestamp.getClass().getName());
        timestamp = timestamp.toString();
        System.out.println("After:" + timestamp.getClass().getName());*/
        //TODO 并发赋值测试
        //new DBSafety().concurrentTest();
        //TODO 默认属性值测试
        System.out.println(new DBSafety().getPri());
    }

    public void getTrainorderbyid(){
        Trainorder trainorder = new Trainorder();
        try {
            //获取DB信息
            DataTable trainorderData = DBHelper.GetDataTable("confirmChangeFindTrainOrderByID @id=" + 73418923,
                    DBSourceEnum.KONGTIE_DB);
            DataTable trainPassengersData = DBHelper.GetDataTable("confirmChangeFindTrainPassenger @orderId=" +
                    73418923, DBSourceEnum.KONGTIE_DB);
            DataTable trainTicketData = DBHelper.GetDataTable("confirmChangeFindTrainpticket @orderId=" + 73418923, DBSourceEnum.KONGTIE_DB);
            DataTable trainStudentInfoData = DBHelper.GetDataTable("confirmChangeFIndTrainstudentinfo @orderId=" + 73418923, DBSourceEnum.KONGTIE_DB);
            ReflexDBInfoTOObject.setDBDate(trainorder,trainorderData.GetRow().get(0), "trainorderfield.properties");
            List<Trainticket> trainTickets = new ArrayList<Trainticket>();//所有车票信息集合
            List<TrainStudentInfo> trainStudentInfos = new ArrayList<TrainStudentInfo>();//所有学生信息集合
            List<Trainpassenger> trainPassengers = new ArrayList<Trainpassenger>();//所有乘客信息
            if (trainTicketData != null && trainTicketData.GetRow() != null) {//所有车票信息
                for(DataRow dataRow:trainTicketData.GetRow()){
                    Trainticket traintrainticket = new Trainticket();
                    ReflexDBInfoTOObject.setDBDate(traintrainticket, dataRow, "trainticketfield.properties");
                    trainTickets.add(traintrainticket);
                }
            }
            if (trainStudentInfoData != null && trainStudentInfoData.GetRow() != null) {//所有学生信息
                for(DataRow dataRow:trainStudentInfoData.GetRow()){
                    TrainStudentInfo trainstudentinfo = new TrainStudentInfo();
                    ReflexDBInfoTOObject.setDBDate(trainstudentinfo, dataRow, "TrainStudentInfofield.properties");
                    trainStudentInfos.add(trainstudentinfo);
                }
            }
            if (trainPassengersData != null && trainPassengersData.GetRow() != null) {//所有乘客信息
                for(DataRow dataRow:trainPassengersData.GetRow()){
                    Trainpassenger trainpassenger = new Trainpassenger();
                    List<Trainticket> passengerTrainTickets = new ArrayList<Trainticket>();//当前乘客车票信息集合
                    List<TrainStudentInfo> passengertrainStudentInfos = new ArrayList<TrainStudentInfo>();//当前乘客学生信息集合
                    ReflexDBInfoTOObject.setDBDate(trainpassenger, dataRow, "trainorderpassengerfield.properties");
                    long trainPid = trainpassenger.getId();
                    for (Trainticket ticket : trainTickets) {
                        if(ticket.getTrainpid() == trainPid){
                            passengerTrainTickets.add(ticket);
                        }
                    }
                    for (TrainStudentInfo studentInfo : trainStudentInfos) {
                        if(studentInfo.getTrainpid() == trainPid){
                            passengertrainStudentInfos.add(studentInfo);
                        }
                    }
                    trainpassenger.setTraintickets(passengerTrainTickets);
                    trainpassenger.setTrainstudentinfos(passengertrainStudentInfos);
                    trainPassengers.add(trainpassenger);
                }
            }
            trainorder.setPassengers(trainPassengers);
        } catch (Exception e) {
            WriteLog.write("数据库查询赋值异常", ":订单ID:" + 73418923);
            e.printStackTrace();
        }
        System.out.println(trainorder);
    }

    private void setDBDate(Object obj, DataRow dataRow,
                                  String fieldProperitesName) throws Exception {
        Class trainorderchangeClass = obj.getClass();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(obj + "--->" + obj.getClass().getName() + "={");
        for (DataColumn dataColumn : dataRow.GetColumn()) {
            String fieldKey = dataColumn.GetKey();
            Object fieldValue = dataColumn.GetValue();
            String fieldname = PropertyUtil.getValue(fieldKey,
                    fieldProperitesName);
            stringBuffer.append(fieldname + ":" + fieldValue + ",");
            if (fieldname != null && !"".equals(fieldname)) {
                Field declaredField = trainorderchangeClass
                        .getDeclaredField(fieldname);
                if (fieldValue != null){
                    if (fieldValue.getClass().getName().equals("java.math.BigDecimal")){
                        fieldValue = typeFromat(declaredField, fieldValue);
                    }
                    declaredField.setAccessible(true);
                    declaredField.set(obj, fieldValue);
                }
            }
        }
        stringBuffer.substring(0,stringBuffer.length()-1);
        stringBuffer.append("}");
        System.out.println(stringBuffer.toString());
    }

    private Object typeFromat(Field field, Object value){
        Object newresult = new Object();
        if (field.getGenericType().equals(Long.TYPE)){
            newresult = Long.parseLong(value.toString());
        }else if (field.getGenericType().equals(Integer.TYPE)){
            newresult = Integer.parseInt(value.toString());
        }else if (field.getGenericType().equals(Float.TYPE)){
            newresult = Float.parseFloat(value.toString());
        }else if (field.getGenericType().equals(Double.TYPE)){
            newresult = Double.parseDouble(value.toString());
        }else if (field.getGenericType().equals(Long.class)){
            newresult = Long.valueOf(value.toString());
        }else if (field.getGenericType().equals(Integer.class)){
            newresult = Integer.valueOf(value.toString());
        }else if (field.getGenericType().equals(Float.class)){
            newresult = Float.valueOf(value.toString());
        }else if (field.getGenericType().equals(Double.class)){
            newresult = Double.valueOf(value.toString());
        }else {
            System.out.println(field.getGenericType().toString() + "未找到对应类型--Reflect");
        }
        return newresult;
    }

    private void testChangeORderFieldTypes(){
        Class trainorderchangeClass = Trainorderchange.class;
        for (Field field : trainorderchangeClass.getDeclaredFields()) {
            if (field.getGenericType() == String.class)
            System.out.println(field.getName() + "\t\t\ttype:" + field.getGenericType());
        }
    }

    private void fieldValueFromatString(Object value){
        value = value.toString();
    }

    private void concurrentTest(){
        int num = 100;
        List<DataRow> data = getData(num);
        List<ConcurrentTestBean> bean = getBean(num);
        for (int i = 0; i < num; i++) {
            new ConcurrentThreadTest(bean.get(i),data.get(i)).start();
        }
        try {
            Thread.sleep(20000);
        }
        catch (InterruptedException e) {
        }
        System.out.println("\n\n\n\n\n\n开始比对");
        for (int i = 0; i < num; i++) {
            StringBuffer sf = new StringBuffer();
            StringBuffer sf2 = new StringBuffer();
            for (DataColumn column : data.get(i).GetColumn()) {
                sf.append(column.GetValue().toString() + "---");
            }
            ConcurrentTestBean concurrentTestBean = bean.get(i);
            sf2.append(concurrentTestBean.getId() + "---");
            sf2.append(concurrentTestBean.getName() + "---");
            sf2.append(concurrentTestBean.getAge() + "---");
            sf2.append(concurrentTestBean.getTime() + "---");
            if (!sf.toString().equals(sf2.toString())){
                System.out.println(sf.toString() + "**********************" + sf2.toString());
            }else {
                System.out.println(sf.toString());
            }
        }
    }

    private List<ConcurrentTestBean> getBean(int num) {
        List<ConcurrentTestBean> concurrentTestBeans = new ArrayList<ConcurrentTestBean>();
        for (int i = 0; i < num; i++) {
            concurrentTestBeans.add(new ConcurrentTestBean());
        }
        return concurrentTestBeans;
    }

    private List<DataRow> getData(int num) {
        List<DataRow> dataRows = new ArrayList<DataRow>();
        for (int i = 0; i < num; i++) {
            List<DataColumn> dataColumns = new ArrayList<>();
            dataColumns.add(new DataColumn("ID",dataRows.size() + 1));
            dataColumns.add(new DataColumn("name",getName()));
            dataColumns.add(new DataColumn("age", new Random().nextInt(30)));
            dataColumns.add(new DataColumn("createTime",new Date().getTime()));
            dataRows.add(new DataRow(dataColumns));
        }
        return dataRows;
    }

    private String getName() {
        int surnameIndex = new Random().nextInt(8);
        int sex = new Random().nextInt(2);
        int nameLength = new Random().nextInt(2) + 1;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(surname[surnameIndex]);
        if (sex == 1){
            for (int i = 0; i < nameLength; i++) {
                stringBuffer.append(boy[new Random().nextInt(34)]);
            }
        }else {
            for (int i = 0; i < nameLength; i++) {
                stringBuffer.append(girl[new Random().nextInt(34)]);
            }
        }
        return stringBuffer.toString();
    }

    public Trainorderchange getTrainOrderChangeById(long changeId) {
        Trainorderchange trainorderchange = new Trainorderchange();
        try {
            DataTable dataTable = DBHelper.GetDataTable("sp_T_TRAINORDERCHANGE_ChangeConfirm_FindTrainOrderChangeByID @id=" + changeId,
                    DBSourceEnum.KONGTIE_DB);
            if (dataTable != null && dataTable.GetRow() != null && dataTable.GetRow().size() == 1) {
                ReflexDBInfoTOObject.setDBDate(trainorderchange, dataTable.GetRow().get(0), "trainorderchangefield" + ".properties");
                return trainorderchange;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.writelogByException("RabbitMQ_TrainConfirmChange_getTrainOrderChangeById_Excetpion",e ,
                    ":改签ID:" + changeId);
        }

        return null;
    }

}
