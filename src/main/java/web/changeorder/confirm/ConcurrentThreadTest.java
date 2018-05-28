package web.changeorder.confirm;

import com.ccservice.Util.db.DataRow;
import net.tomas.db.safety.uitl.ReflexDBInfoTOObject;

/**
 * @Description: 线程
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/3/1
 */
public class ConcurrentThreadTest extends Thread {

    private ConcurrentTestBean bean = null;

    private DataRow data = null;

    public ConcurrentThreadTest(ConcurrentTestBean bean, DataRow data) {
        this.bean = bean;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            sleep(10000);
            System.out.println(currentThread().getName() + "--start");
            ReflexDBInfoTOObject.setDBDate(bean,data,"test.properties");
            System.out.println(currentThread().getName() + "--end");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
