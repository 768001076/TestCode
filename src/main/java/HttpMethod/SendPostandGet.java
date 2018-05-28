package HttpMethod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class SendPostandGet {
    public static StringBuffer submitPost(String url, String paramContent, String codetype) {
        System.out.println(132);
        StringBuffer responseMessage = null;
        URLConnection connection = null;
        URL reqUrl = null;
        OutputStreamWriter reqOut = null;
        InputStream in = null;
        BufferedReader br = null;
        String param = paramContent;

        try {
            responseMessage = new StringBuffer();
            reqUrl = new URL(url);
            connection = reqUrl.openConnection();
            connection.setDoOutput(true);
            reqOut = new OutputStreamWriter(connection.getOutputStream());
            reqOut.write(param);
            reqOut.flush();
            in = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(in, codetype));

            int charCount;
            while((charCount = br.read()) != -1) {
                responseMessage.append((char)charCount);
            }
        } catch (Exception var19) {
            System.out.println("Exception:" + url + "?" + paramContent);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

                if (reqOut != null) {
                    reqOut.close();
                }
            } catch (Exception var18) {
                var18.printStackTrace();
                System.out.println("Exception:" + url + "?" + paramContent);
            }

        }

        return responseMessage;
    }
}
