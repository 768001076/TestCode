package thread;

import thread.thr.DateFormatThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(300);
        for (int i = 0; i < 5000; i++) {
            executorService.execute(new DateFormatThread());
        }
        executorService.shutdown();
    }

}
