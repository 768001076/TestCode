package account;

import com.alibaba.fastjson.JSONObject;
import com.ccservice.account.dubbo.util.DubboConsumer;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.ccservice.Util.string.StringUtil.StringIsNull;

public class AccountTest {

    public static Logger logger = null;

    static {
        String properties = "log4j.properties";
        try {
            Properties pro = new Properties();
            pro.load(AccountTest.class.getResourceAsStream("/" + properties));
            PropertyConfigurator.configure(pro);
        }
        catch (IOException e) {
        }
        logger = LoggerFactory.getLogger("AccountTest");
    }

    public static final String UTF8 = "UTF-8";//UTF-8

    public static final int OneFree = 1;//释放次数，1

    public static final int TwoFree = 2;//释放次数，2

    public static final int ZeroCancel = 0;//取消次数，0

    public static final int OneCancel = 1;//取消次数，1

    public static final int ThreeCancel = 3;//取消次数，3

    public static final String NullName = "";//获取账号，空账号名

    public static final Timestamp NullDepartTime = null;//发车时间，空

    public static final boolean waitWhenNoAccount = true;//获取账号，无账号等待

    public static final boolean checkPassenger = true;//释放的是身份验证

    public static final Map<String, String> NullMap = new HashMap<String, String>();//获取账号，备用字段

    /***********账号类型START***********/

    public static final int OrderAccount = 1;//下单账号

    public static final int PassengerAccount = 2;//身份验证账号

    public static final int LoginNameAccount = 3;//账号名获取

    public static final int CustomerAccount = 4;//客户账号

    /***********账号类型END***********/

    /***********释放类型START***********/

    public static final int FreeNoCare = 1;//NoCare

    public static final int FreeCurrent = 2;//仅当天使用

    public static final int FreeDepart = 3;//发车时间后才可使用

    public static final int FreeOther = 4;//分配给其他业务(暂未用)

    public static final int FreePassengerFull = 5;//账号乘客已满

    public static final int FreeNoLogin = 6;//账号未登录

    public static final int FreeNoCheck = 7;//账号待核验

    public static final int FreeBespeakBindingPassenger = 8;//约票绑定乘客释放(约票专用，其他业务勿使)

    /***********释放类型END***********/

    /**********网页请求头属性START**********/

    public static final String HeaderCookie = "Cookie";

    public static final String HeaderReferer = "Referer";

    public static final String HeaderContentType = "Content-Type";

    public static final String HeaderContentLength = "Content-Length";

    public static final String HeaderContentTypeValueUTF8 = "application/x-www-form-urlencoded; charset=UTF-8";

    //登录短信正则表达式
    //    public final String loginRegex = "[a-zA-Z\\d]{6}";//【易订行】您的验证码是:GRHYQU【铁路客服】/回复TD退订
    public final String loginRegex = "*您的验证码是:.*";//【易订行】您的验证码是:GRHYQU【铁路客服】/回复TD退订

    /**********网页请求头属性END**********/

    public static String ACCOUNT_URL = "http://121.41.114.170:45808/12306Account/Account.jsp";

    public static void main(String[] args) {
        String name = "wuxuehucbhv7eo6y";
        logger.info("Start");
        AccountTest accountTest = new AccountTest();
        boolean isGet = false;
        if (isGet) {
            JSONObject account = accountTest.getAccount(LoginNameAccount, name, true, NullMap);
            logger.info("Account:" + account);
            JSONObject checkUser = accountTest.checkUse(account);
            logger.info("CheckUse:" + checkUser);
        }else {
            accountTest.freeAccount(name,0,FreeNoCare,OneFree,ZeroCancel,NullDepartTime,checkPassenger);
        }
        logger.info("End");

    }

    public JSONObject checkUse(JSONObject account) {
        String param = "_json_att=";
        String url = "https://kyfw.12306.cn/otn/login/checkUser";
        Map<String, String> header = RequestUtil.getRequestHeader(true, false, true);
        header.put(HeaderCookie, account.getString("cookie"));
        header.put(HeaderContentType, HeaderContentTypeValueUTF8);
        header.put(HeaderContentLength, String.valueOf(param.length()));
        header.put(HeaderReferer, "https://kyfw.12306.cn/otn/leftTicket/init");
        header.put("User-Agent", account.getJSONObject("repInfo").getString("browserValue"));
        logger.info("header:" + header);
        String result = RequestUtil.post(url, param, UTF8, header, 30 * 1000);
        return JSONObject.parseObject(result);
    }

    public JSONObject getAccount(int type,String name,boolean waitWhenNoAccount,
            Map<String, String> backup) {
        //请求JSON
        JSONObject data = AccountSystemParam("Get");
        //备用字段
        for (String key : backup.keySet()) {
            data.put(key, backup.get(key));
        }
        data.put("type", type);
        data.put("name", name);
        data.put("waitWhenNoAccount", waitWhenNoAccount);
        String param = "param=" + data.toJSONString();
        String ret = RequestUtil.post(ACCOUNT_URL, param, "UTF-8", new HashMap<String, String>(), 10 * 60 * 1000);
        return JSONObject.parseObject(ret);
    }

    public void freeAccount(String name,long repId,int freeType, int freeCount, int cancalCount,
            Timestamp departTime, boolean isCheckPassenger) {
        //请求JSON
        JSONObject data = AccountSystemParam("Free");
        data.put("name", name);
        data.put("freeType", freeType);
        data.put("freeCount", freeCount);
        data.put("repId", repId);//释放REP用
        data.put("cancalCount", cancalCount);
        data.put("isCheckPassenger", isCheckPassenger);
        data.put("isCustomerAccount", false);
        data.put("departTime", departTime == null ? new Timestamp(System.currentTimeMillis())
                : departTime);
        try {
            DubboConsumer.getInstance().getDubboAccount().free12306Account(data);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private JSONObject AccountSystemParam(String method) {
        JSONObject param = new JSONObject();
        try {
            param.put("method", method);
            long longtime = System.currentTimeMillis();
            String reqtime = String.valueOf(longtime);
            param.put("reqtime", reqtime);
            param.put("sign", MD5(MD5(method) + reqtime));
        }
        catch (Exception e) {
            logger.error("拼接参数");
        }
        return param;
    }

    public String MD5(String input) throws Exception {
        if (StringIsNull(input)) {
            return "";
        }
        byte[] buf = input.getBytes("utf-8");
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(buf);
        byte[] md = m.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < md.length; i++) {
            int val = ((int) md[i]) & 0xff;
            if (val < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

}
