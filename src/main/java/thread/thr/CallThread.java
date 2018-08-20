package thread.thr;

import com.alibaba.fastjson.JSONObject;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @Description: 可回调线程
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/7/4
 */
public class CallThread implements Callable<JSONObject> {

    private int sle = 0;

    public CallThread(int sle) {
        this.sle = sle;
    }

    @Override public JSONObject call() {
        return getResult();
    }

    private JSONObject getResult() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sleepTime",sle);
        jsonObject.put("result","结束");
        if (sle > 3000) {
            for (int i=0 ; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println(1);
                }
                catch (Exception e) {
                }
            }
        }
        System.out.println(Thread.currentThread().getName() + ":" + jsonObject);
        return jsonObject;
    }
}
