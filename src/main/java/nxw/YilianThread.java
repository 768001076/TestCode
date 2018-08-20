package nxw;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONObject;

public class YilianThread {

    public static void main(String[] args) {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(100);
        
        for (int i = 0; i < 400; i++) {
            new aaa(i).start();
    }
    } 
}
 