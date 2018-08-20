package exceptiontest;

public class ExceptonTest {

    public static void main(String[] args) {
//        try {
//            throw new NullPointerException("123");
//        }
//        catch (NullPointerException e) {
//            exceptionTypeTest(e);
//        }
//        StringBuffer stringBuffer = null;
//        System.out.println("1" + stringBuffer);
//        try {
//            stringBuffer = new StringBuffer();
//            System.out.println("2" + stringBuffer);
//            throw new NullPointerException("test");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("3" + stringBuffer.toString());


        // TODO 异常信息NULL
        try {
            throw new NumberFormatException();
        }
        catch (Exception e) {
            exceptionTypeTest(e);
        }

    }

    public static void exceptionTypeTest(Exception e){
        System.out.println(e.getMessage());
    }

}
