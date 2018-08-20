package thread;

import com.alibaba.fastjson.JSONObject;
import thread.thr.CallThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description: 可回调线程测试
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/7/4
 */
public class CanCallThreadTest {

    public static void main(String[] args) {
        CallThread callThread1 = new CallThread(1000);
        CallThread callThread2 = new CallThread(10000);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<JSONObject>> futures = new ArrayList<Future<JSONObject>>();
        try {
            CompletionService<JSONObject> completionService = new ExecutorCompletionService<JSONObject>(executorService);
            futures.add(completionService.submit(callThread1));
            futures.add(completionService.submit(callThread2));
            int timeout = 60;
            for (int i = 0; i < futures.size(); i++) {
                Future<JSONObject> futrue = completionService.poll(timeout, TimeUnit.SECONDS);
                System.out.println(futrue.get());
                if (null != futrue.get()) {
                    System.out.println("Break!");
                    if (1==1) {
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                cancelFutures(futures);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                executorService.shutdown();
            }
        }
    }

    private static void cancelFutures(List<Future<JSONObject>> futures) {
        try {
            for (Future<JSONObject> future : futures) {

                    future.cancel(false);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
