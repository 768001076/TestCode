package HttpMethod;

import com.ccservice.Util.httpclient.CCSGetMethod;
import com.ccservice.Util.httpclient.CCSHttpClient;
import com.ccservice.Util.httpclient.CCSPostMethod;
import com.ccservice.Util.httpclient.MySSLProtocolSocketFactory;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.IOException;
import java.util.Map;

public class HttpsClientUtils {

    /**
     *  get访问url
     * 
     * @param search_url 访问的url
     * @param outtime 超时时间
     * @return
     * @time 2015�?�?�?上午11:22:34
     * @author chendong
     */
    public static String gethttpclientdata(String search_url, Long outtime) {
        String json = "-1";
        CCSGetMethod get = null;
        CCSHttpClient httpClient = new CCSHttpClient(false, outtime,"");
        get = new CCSGetMethod(search_url);
        Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);
        get.setFollowRedirects(false);
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        try {
            httpClient.executeMethod(get);
            json = get.getResponseBodyAsString();
        }
        catch (HttpException e) {
        }
        catch (IOException e) {
        }
        return json;
    }

    public String posthttpclientdata(String search_url, Map<String, String> listdata, Long outtime) {
        String json = "";
        CCSPostMethod post = null;
        CCSHttpClient httpClient = new CCSHttpClient(false, outtime,"");
        post = new CCSPostMethod(search_url);
        Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);
        post.setFollowRedirects(false);
        for (String key : listdata.keySet()) {
            post.setParameter(key, listdata.get(key));
        }
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        try {
            httpClient.executeMethod(post);
            json = post.getResponseBodyAsString();
        }
        catch (Exception e) {
            System.out.println(search_url);
            e.printStackTrace();
        }
        return json;
    }
}
