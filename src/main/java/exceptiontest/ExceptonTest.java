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
    }

    public static void exceptionTypeTest(Exception e){
        System.out.println(e.getClass().getSimpleName());
    }

}
