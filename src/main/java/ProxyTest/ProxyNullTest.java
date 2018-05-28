package ProxyTest;

import com.alibaba.fastjson.JSONObject;
import net.tomas.proxy.base.bean.ProxyBean;
import net.tomas.proxy.base.bean.RepMessageBean;

import java.io.*;

public class ProxyNullTest {

    public static void main(String[] args) {
//        RepMessageBean repMessageBean = new RepMessageBean();
//        repMessageBean.setOutNetIp("");
//        repMessageBean.setIntraneIp("127.0.0.1");
//        repMessageBean.setProxyBean(null);
//        System.out.println(repMessageBean.toJsonString());
//        ProxyBean info = new ProxyBean();
//        info.setProxyIp("192.168.0.152");
//        try {
//            info = getInfo();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(info.toJsonString());
        File file = new File("C:\\Users\\Administrator\\Desktop\\获取代理信息.log");
        InputStreamReader inputStreamReader = null;
        FileInputStream inputStream = null;
        BufferedReader br= null;
        try {
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream,"GBK");
            br = new BufferedReader(inputStreamReader);
            String i;
            while ((i = br.readLine()) != null){
                if (i.contains("intraneIp") && i.contains("proxyIp")){
                    JSONObject jsonObject = JSONObject.parseObject(i.split("proxyBean>>")[1]);
                    Boolean canUse = jsonObject.getBooleanValue("canUse");
                    String proxyIp = jsonObject.getString("proxyIp");
                    Integer proxyPort = jsonObject.getIntValue("proxyPort");
                    ProxyBean proxyBean2 = new ProxyBean(proxyIp, proxyPort, canUse);
                    ProxyBean proxy = ProxyNullTest.getProxy(proxyBean2);
                    String proxyJson = proxy.toJsonString();
                    System.out.println(proxyJson);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null){
                    br.close();
                }
                if (inputStreamReader != null){
                    inputStreamReader.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ProxyBean getProxy(ProxyBean proxyBean) {
        Boolean canUse = proxyBean.isCanUse();
        String proxyIp = proxyBean.getProxyIp();
        Integer proxyPort = proxyBean.getProxyPort();
        if (proxyIp == null || "".equals(proxyIp) || proxyPort == 0) {
            // DB查询数据
            System.out.println("DB");
            return proxyBean;
        } else {
            if (!canUse) {
                System.out.println("代理池");
                return proxyBean;
            }
        }
        return proxyBean;
    }

    public static ProxyBean getInfo(){
        ProxyBean proxyBean = new ProxyBean();
        proxyBean.setProxyPort(3138);
//        proxyBean.setProxyIp("127.0.0.1");
        proxyBean.setUseProxy(false);
        proxyBean.setCanUse(false);
        return proxyBean;
    }

}
