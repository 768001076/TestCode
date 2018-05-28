package art;

/**
 * @Description: 异常测试
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/3/1
 */
public class ExceptionTryTest {

    public static void main(String[] args) {
        new ExceptionTryTest().tryExceptionTest();
    }

    public void tryExceptionTest(){
        try {
            throw new RuntimeException("123");
        }
        catch (RuntimeException e) {
            System.out.println(111);
        }catch (Exception e2){
            System.out.println(222);
        }
    }

}
