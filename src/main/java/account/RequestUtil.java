package account;

import com.ccservice.Util.file.ExceptionUtil;
import com.ccservice.Util.file.WriteLog;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;

import static com.ccservice.Util.string.StringUtil.StringIsNull;

public class RequestUtil {

    private RequestUtil() {
    }

    public static String post(String url, String data, String charset, Map<String, String> header, int timeout) {
        //结果
        String result = "";
        //异常
        try {
            result = submit(url, data, "post", charset, header, timeout);
        }
        catch (Exception e) {
            WriteLog.write("http请求异常捕获", url + "?" + data + "--->" + timeout + "<---");
            ExceptionUtil.writelogByException("http请求异常捕获", e);
        }
        //返回
        return StringIsNull(result) ? "" : result;
    }

    public static String get(String url, String charset, Map<String, String> header, int timeout) {
        //结果
        String result = "";
        //异常
        try {
            result = submit(url, "", "get", charset, header, timeout);
        }
        catch (Exception e) {
            WriteLog.write("http请求异常捕获", url + "--->" + timeout + "<---");
            ExceptionUtil.writelogByException("http请求异常捕获", e);
        }
        //返回
        return StringIsNull(result) ? "" : result;
    }

    /**
     * @param url 请求链接，以http或https开头
     * @param data 请求数据，post请求有效
     * @param reqType 请求类型，get或post，不填视为post
     * @param charset 字符集，不填视为"utf-8"
     * @param header 请求头
     */
    private static String submit(String url, String data, String reqType, String charset, Map<String, String> header,
            int timeout) throws Exception { //HTTPS SSL
        url = url.trim();
        charset = StringIsNull(charset) ? "utf-8" : charset.trim();
        //使用代理
        String AgentIp = "";
        if (header != null && header.size() > 0) {
            AgentIp = header.get("AgentIp");
        }
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
        StringBuffer res = new StringBuffer();
        InputStream in = null;
        URLConnection con = null;
        BufferedReader reader = null;
        DataOutputStream out = null;
        try {
            //使用代理
            if (!StringIsNull(AgentIp) && AgentIp.contains(":")) {
                String ip = AgentIp.split(":")[0];
                int port = Integer.parseInt(AgentIp.split(":")[1]);
                con = (new URL(url)).openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)));
            }
            else {
                con = (new URL(url)).openConnection();
            }
            con.setDoOutput(true);
            con.setUseCaches(false);
            if (timeout > 0) {
                con.setReadTimeout(timeout);
                con.setConnectTimeout(timeout);
            }
            else {
                con.setReadTimeout(5 * 60 * 1000);
                con.setConnectTimeout(5 * 60 * 1000);
            }
            if (header != null && header.size() > 0) {
                for (String key : header.keySet()) {
                    con.setRequestProperty(key, header.get(key));
                }
            }
            if ("get".equalsIgnoreCase(reqType)) {
                con.connect();
            }
            else {
                out = new DataOutputStream(con.getOutputStream());
                out.write(data.getBytes(charset));
            }
            in = con.getInputStream();
            //判断是否压缩
            String ContentEncoding = con.getHeaderField("Content-Encoding");
            if ("gzip".equalsIgnoreCase(ContentEncoding)) {
                reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(in), charset));
            }
            else {
                reader = new BufferedReader(new InputStreamReader(in, charset));
            }
            String lineTxt = null;
            while ((lineTxt = reader.readLine()) != null) {
                res.append(lineTxt);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
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
        return res.toString();
    }

    private static class myX509TrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static class myHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * 网页公共请求头
     *
     * @param isAjaxRequest
     *            网页异步请求 --> X-Requested-With:XMLHttpRequest
     * @param completeAccept
     *            完整的Accept -->
     *            Accept:text/html,application/xhtml+xml,application/xml;q=0.9..
     *            .
     * @param IfModifiedSince
     *            true时 --> If-Modified-Since：0
     * @return HeaderMap，key：请求头名称，value：请求头值
     */
    public static Map<String, String> getRequestHeader(boolean isAjaxRequest, boolean completeAccept,
            boolean IfModifiedSince) {
        return getRequestHeader("", "", "", "", isAjaxRequest, completeAccept, IfModifiedSince);
    }

    /*
     * public static Map<String, String> getRequestHeader1(boolean
     * isAjaxRequest, boolean completeAccept, boolean IfModifiedSince) { return
     * getRequestHeader1("", "", "", "", isAjaxRequest, completeAccept,
     * IfModifiedSince); }
     */

    /**
     * 网页公共请求头
     *
     * @param Cookie
     * @param Referer
     * @param ContentType，如application/x-www-form-urlencoded;
     *            charset=UTF-8
     * @param ContentLength
     *            请求参数长度
     * @param isAjaxRequest
     *            网页异步请求 --> X-Requested-With:XMLHttpRequest
     * @param completeAccept
     *            完整的Accept -->
     *            Accept:text/html,application/xhtml+xml,application/xml;q=0.9..
     *            .
     * @param IfModifiedSince
     *            true时 --> If-Modified-Since：0
     * @return HeaderMap，key：请求头名称，value：请求头值
     */
    public static Map<String, String> getRequestHeader(String Cookie, String Referer, String ContentType,
            String ContentLength, boolean isAjaxRequest, boolean completeAccept, boolean IfModifiedSince) {
        return getRequestHeader(Cookie, Referer, ContentType, ContentLength, isAjaxRequest, completeAccept,
                IfModifiedSince, false);
    }

    public static Map<String, String> getRequestHeader(String Cookie, String Referer, String ContentType,
            String ContentLength, boolean isAjaxRequest, boolean completeAccept, boolean IfModifiedSince,
            boolean pretendIp) {
        Map<String, String> map = new HashMap<String, String>();
        // PUT
        if (!StringIsNull(Cookie)) {
            map.put("Cookie", Cookie);
        }
        if (!StringIsNull(Referer)) {
            map.put("Referer", Referer);
        }
        if (IfModifiedSince) {
            map.put("If-Modified-Since", "0");
        }
        if (!StringIsNull(ContentType)) {
            map.put("Content-Type", ContentType);
        }
        if (!StringIsNull(ContentLength)) {
            map.put("Content-Length", ContentLength);
        }
        if (isAjaxRequest) {
            map.put("X-Requested-With", "XMLHttpRequest");
        }
        if (pretendIp) {
            map.put("Cdn-Src-Ip", getRandomIp());
        }
        map.put("DNT", "1");
        map.put("Pragma", "no-cache");
        map.put("Host", "kyfw.12306.cn");
        map.put("Accept-Language", "zh-CN");
        map.put("Connection", "keep-alive");
        map.put("Cache-Control", "no-cache");
        map.put("Accept-Encoding", "gzip, deflate");
        map.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        map.put("Accept",
                completeAccept ? "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" : "*/*");
        // 返回
        return map;
    }

    private static String getRandomIp() {
        Random random = new Random();
        String randomIp = (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1)
                + "." + (random.nextInt(253) + 1);
        return randomIp;
    }
}