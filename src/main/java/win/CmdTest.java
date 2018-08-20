package win;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * @Description: CMD测试
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/7/10
 */
public class CmdTest {

    public static void main(String[] args) throws IOException {
//        sshLogin();
        test("sfds");
    }

    public static void sshLogin() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("C:\\Users\\Administrator\\Desktop\\bat\\aliy1.bat");
    }

    public static void test(String name ,JSONObject... jsonObjects) {
        JSONObject jsonObject = new JSONObject();
        for (JSONObject s : jsonObjects) {
            jsonObject.putAll(s);
        }
        System.out.println(jsonObject);
    }

}
