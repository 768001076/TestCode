package thread.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 线程执行任务
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/7
 */
public class ThreadLockTask {

    // 任务锁
    private static Map<Long,Object> taskLock = new HashMap<Long, Object>();


    public static boolean startWork(long id, String msg, int r) {
        Object lock = getLock(id);
        synchronized(lock) {
            try {
                System.out.println(r + ":开始执行任务:" + id);
                Thread.sleep(5000);
//                System.out.println(r + ":" + id + ":" + msg + ":" + (r%2 ==0));
                return r%2 ==0;
            }
            catch (InterruptedException e) {
                System.out.println("睡不着了:" + r);
                return false;
            }
        }
    }

    private static Object getLock(long id) {
        if (taskLock.containsKey(id)) {
            return taskLock.get(id);
        } else {
            Object lock = new Object();
            taskLock.put(id,lock);
            return lock;
        }
    }

}
