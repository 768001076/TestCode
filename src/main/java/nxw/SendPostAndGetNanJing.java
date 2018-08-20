package nxw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.ccservice.Util.PropertyUtil;



public class SendPostAndGetNanJing {

	/**     标记
	 *      
	 * @Method: sortParams
	 * @Description: 参数重新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sortParams(Map<String, String> params)
			throws Exception {
		String result = "";
		String[] args = new String[params.size()];
		Set<String> keys = params.keySet();
		Iterator<String> iterator = keys.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			args[i] = iterator.next();
			i++;
		}
		Arrays.sort(args);
		// 重组url
		for (int j = 0; j < args.length; j++) {
			String key = args[j];
			String value = params.get(key);
			result += "&" + key + "=" + value;
		}
		if (result.length() > 0) {
			result = result.substring(1);
		}
		return result;
	}
	
	
	/**
	 * 方法说明 ：
	 *
	 * @author 作者 hefan 
	 * @version 创建时间：2017年10月27日 下午6:06:18
	 */
	public static String sendHttpMsg(String url,String content) throws Exception {
//		String url=ManagerUtil.getProperty("src/conf/cert-key.properties").getProperty("jsb.eis.url");
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
	//	conn.setInstanceFollowRedirects(true);
//		conn.setRequestProperty("content-type", "application/json");
		conn.connect();
		//OutputStream out = conn.getOutputStream();
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		PrintWriter pw = new PrintWriter(out);
		pw.print(content);
		pw.flush();
		out.flush();
		pw.close();
		out.close();
		
		InputStream is  = conn.getInputStream();
		//InputStreamReader isr = new InputStreamReader(is);
		BufferedReader isr = new BufferedReader(
				new InputStreamReader(is, "utf-8"));
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
		
	}
}
