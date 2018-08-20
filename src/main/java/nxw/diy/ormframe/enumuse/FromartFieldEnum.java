package nxw.diy.ormframe.enumuse;

import java.lang.reflect.Field;

/**
 * @Description: 属性转换类型枚举
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/3/1
 */
public enum FromartFieldEnum {

    LONG_TYPE(1,"long"),INTEGER_TYPE(2,"int"),FLOAT_TYPE(3,"float"),DOUBLE_TYPE(4,"double"),LONG_CLASS(5,"class java.lang"
            + ".Long"),INTEGER_CLASS(6,"class java.lang.Integer"),FLOAT_CLASS(7,"class java.lang.Float"),DOUBLE_CLASS(5,"class java.lang"
            + ".Double");

    /*
     * 编号
     */
    private int no;
    /*
     * 内容
     */
    private String str;

    private FromartFieldEnum(int no, String str) {
        this.no = no;
        this.str = str;
    }

    public int getNo() {
        return this.no;
    }

    public String getStr() {
        return this.str;
    }

    /**
     * @Description: 根据编号获取枚举
     *
     * @Param no --编号
     * @Return FromartFieldEnum
     * @Throws NullPointerException
     */
    public static FromartFieldEnum getFromartFieldEnumByNO(int no) throws NullPointerException {
        for (FromartFieldEnum fromartFieldEnum : values()) {
            if (fromartFieldEnum.getNo() == no) {
                return fromartFieldEnum;
            }
        }
        throw new NullPointerException("FromartFieldEnum is not have this No!!!!");
    }

    /**
     * @Description: 根据内容获取枚举
     *
     * @Param str --内容
     * @Return FromartFieldEnum
     * @Throws NullPointerException
     */
    public static FromartFieldEnum getFromartFieldEnumByStr(String str) throws NullPointerException {
        for (FromartFieldEnum fromartFieldEnum : values()) {
            if (fromartFieldEnum.getStr().equals(str)) {
                return fromartFieldEnum;
            }
        }
        throw new NullPointerException("FromartFieldEnum is not have this str!!!!");
    }

    /**
     * @Description: 类型转换
     *
     * @Param field--属性
     * @Param value--值
     * @Return Object 如果未找到对应类型 返回null
     */
    public static Object fromartMethod(Field field, Object value){
        String s = field.getGenericType().toString();
        switch (getFromartFieldEnumByStr(s)) {
        case LONG_TYPE:;return Long.parseLong(value.toString());
        case INTEGER_TYPE:return Integer.parseInt(value.toString());
        case FLOAT_TYPE:return Float.parseFloat(value.toString());
        case DOUBLE_TYPE:return Double.parseDouble(value.toString());
        case LONG_CLASS:return Long.valueOf(value.toString());
        case INTEGER_CLASS:return Integer.valueOf(value.toString());
        case FLOAT_CLASS:return Float.valueOf(value.toString());
        case DOUBLE_CLASS:return Double.valueOf(value.toString());
        default:return null;
        }
    }

}
