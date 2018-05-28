package HttpMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;

import static com.ccservice.Util.PropertyUtil.StringIsNull;

public class HttpBeanMethod {

    private String url;

    private Map<String,String> header = new HashMap<String, String>();

    private String httpType = "get";

    private int timeout = 30000;

    private String param = "";

    private int statusCode;

    private String location;

    private String result;

    private String realTrain12306Url = "https://kyfw.12306.cn/otn/";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getHttpType() {
        return httpType;
    }

    public void setHttpType(String httpType) {
        this.httpType = httpType;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRealTrain12306Url() {
        return realTrain12306Url;
    }

    public void setRealTrain12306Url(String realTrain12306Url) {
        this.realTrain12306Url = realTrain12306Url;
    }

    public HttpBeanMethod(String url, Map<String, String> header, String httpType, int timeout, String param) {
        this.url = url;
        this.header = header;
        this.httpType = httpType;

        this.timeout = timeout;
        this.param = param;
    }

    public void send(String ip,String prot) throws NullPointerException {
        url = get12306UrlPath(url, header);
        //记录setcookie的log
        String writeCookie = "";
        //12306
        boolean is12306 = true;
        //记日志>>12306
        boolean writeLog = url.contains(".12306.cn");
        //取Cookie
        boolean getResponseCookie = true;
        //日志
        String logName = "RequestUtil_Submit_Info";
        //时间
        long startTime = System.currentTimeMillis();
        //标示
        String remark = startTime + ">>" + (new Random().nextInt(9000000) + 1000000);
        //日志
        if (writeLog) {
            System.out.println(remark + ">>" + url + ">>" + httpType + ">>ip12306" + ">>param>>"
                    + param + ">>header>>" + header);
        }
        //编码
        url = url.trim();

        //HTTPS
        if (url.startsWith("https")) {
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL"); //或SSL
                X509TrustManager[] xtmArray = new X509TrustManager[] { new myX509TrustManager() };
                sslContext.init(null, xtmArray, new java.security.SecureRandom());
            }
            catch (GeneralSecurityException e) {
            }
            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            }
            HttpsURLConnection.setDefaultHostnameVerifier(new myHostnameVerifier());
        }
        InputStream in = null;
        URLConnection con = null;
        DataOutputStream out = null;
        BufferedReader reader = null;
        JSONArray cookies = new JSONArray();
        StringBuffer res = new StringBuffer();
        try {
            if (ip!=null && prot !=null) {//使用代理
                System.out.println(remark + ">>url>>"+url+">>>代理信息=" + ip + ":" + prot);
                con = (new URL(url)).openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, Integer
                        .parseInt(prot))));
            } else {
                con = (new URL(url)).openConnection();
            }
            con.setDoOutput(true);
            con.setUseCaches(false);
            if (timeout > 0) {
                con.setReadTimeout(timeout);
                con.setConnectTimeout(timeout);
            }
            try {
                for (String key : header.keySet()) {
                    con.setRequestProperty(key, header.get(key));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ("get".equalsIgnoreCase(httpType)) {
                con.connect();
            } else {
                out = new DataOutputStream(con.getOutputStream());
                out.write(param.getBytes("utf-8"));
            }
            in = con.getInputStream();
            //状态码及Location
            try {
                HttpURLConnection conn = (HttpURLConnection) con;
                this.statusCode = conn.getResponseCode();
                if (301 == statusCode || 302 == statusCode) {
                    this.location = conn.getHeaderField("Location");
                }
                //此次不知道设么，额状态是代理不可用暂时放一下,以下代码为测试 TODO
            } catch (Exception e) {
                if (writeLog) {
                    System.out.println(e.getMessage() + remark + "_Exception");
                }
            }
            //判断是否压缩
            if ("gzip".equalsIgnoreCase(con.getHeaderField("Content-Encoding"))) {
                reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(in), "utf-8"));
            }
            else {
                reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            }
            String lineTxt = null;
            while ((lineTxt = reader.readLine()) != null) {
                res.append(lineTxt);
            }
            List<String> SetCookies = con.getHeaderFields().get("Set-Cookie");
            //取Response的Cookie
            if (getResponseCookie) {
                //存在Cookie
                if (SetCookies != null && SetCookies.size() > 0) {
                    for (String SetCookie : SetCookies) {
                        cookies.add(SetCookie);
                    }
                }
            }
            if (writeLog) {
                //存在Cookie
                if (SetCookies != null && SetCookies.size() > 0) {
                    for (String SetCookie : SetCookies) {
                        writeCookie += SetCookie;
                    }
                }
            }
            if (SetCookies != null && SetCookies.size() > 0) {
                for (String setCookie : SetCookies) {
                    try {
                        System.out.println("cookie:" + cookies);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage() + remark + "_Exception");
                    }
                }
            }
        }
        catch (ConnectException coone){
            System.out.println(coone.getMessage());
            if (coone.getMessage().contains("Connection refused")){
                System.out.println(coone.getMessage());
            }
        }
        catch (Exception e) {
            //重置
            res = new StringBuffer();
            System.out.println(e.getMessage());
            //错误日志
            if (writeLog) {
                System.out.println(e.getMessage() + remark);
            }
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (Exception e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception e) {
            }
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            }
            catch (Exception e) {
            }
        }
        result = res.toString();
        //要Cookie
        if (getResponseCookie) {
            JSONObject obj = new JSONObject();
            obj.put("result", result);
            obj.put("cookies", cookies);
            obj.put("cookie", JoinCookie(cookies, is12306));
            result = obj.toString();
        }
        //结束
        long endTime = System.currentTimeMillis();
        //日志
        if (writeLog) {
            System.out.println(remark + ">>" + url + ">>time>>" + (endTime - startTime) + ">>statusCode>>"
                    + statusCode + ((location != null && !"-1".equals(location)) ? ">>Location>>" + location : "")
                    + ">>response>>" + result + ">>>writeCookie>>>" + writeCookie);
        }
    }

    public String get12306UrlPath(String url, Map<String, String> header) {
        String newUrl = url;
        if (true) {
            if (header != null && header.containsKey("Referer")) {
                String oldReferer = header.get("Referer");
                String newReferer = oldReferer.replace("https://kyfw.12306.cn/otn/", realTrain12306Url);
                header.put("Referer", newReferer);
            }
            newUrl = url.replace("https://kyfw.12306.cn/otn/", realTrain12306Url);
        }
        return newUrl;
    }


    public String JoinCookie(JSONArray cookies, boolean is12306) {
        StringBuffer buf = new StringBuffer();
        //Set-Cookie:BIGipServerotn=953614602.24610.0000; path=/
        //Set-Cookie:JSESSIONID=0A01D738603F6E8BD646DD5F9C2C1B0CCCB6E392FD; Path=/otn
        String JSESSIONID = "";
        //循环
        for (int i = 0; i < cookies.size(); i++) {
            //可能多个
            String[] cookieArray = cookies.getString(i).split(";");
            //循环拼接
            for (String cookie : cookieArray) {
                if (is12306) {
                    //临时
                    String temp = cookie.toLowerCase().trim();
                    //跳过
                    if (temp.startsWith("path")) {
                        continue;
                    }
                    //ID
                    if (StringIsNull(JSESSIONID) && temp.toUpperCase().startsWith("JSESSIONID")) {
                        JSESSIONID = cookie.trim() + "; ";
                    }
                    else {
                        buf.append(cookie.trim() + "; ");
                    }
                }
                else {
                    buf.append(cookie.trim() + "; ");
                }
            }
        }
        String result = (JSESSIONID + buf.toString()).trim();
        //返回
        return result.endsWith(";") ? result.substring(0, result.length() - 1) : result;
    }

    public static void main(String[] args) {
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("Accept,text/html","application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8");
        headers.put("Accept-Encoding","gzip, deflate, br");
        headers.put("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        headers.put("Connection","keep-alive");
        headers.put("Host","kyfw.12306.cn");
        headers.put("Upgrade-Insecure-Requests","1");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/59.0");

        String proxyInfos = "222.185.23.254";
        for (String s : proxyInfos.split(",")) {
            HttpBeanMethod httpBeanMethod = new HttpBeanMethod("http://www.baidu.com", new
                    HashMap<String, String>(), "get", 6000, "");
            httpBeanMethod.send(s,"3138");
            System.out.println(httpBeanMethod.getStatusCode());
            if (httpBeanMethod.getStatusCode() == 302 || httpBeanMethod.getStatusCode() == 301)
                System.out.println(httpBeanMethod.getLocation());
        }
    }

}
