package thread.lock;

import java.util.Random;

/**
 * @Description: 线程锁测试
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/7
 */
public class ThreadLockTastTest {

    public static void main(String[] args) {
        final String msg = "线程锁测试";
        for (int i = 0; i < 25; i++) {
            new Thread(new Runnable() {
                @Override public void run() {
                    int r = new Random().nextInt(100000);
                    int id = new Random().nextInt(2);
                    ThreadLockTask.startWork(id,msg,r);
                }
            }).start();
        }
    }

}
