/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import weibo4j.Weibo;

import weibo4j.model.MySSLSocketFactory;

/**
 *
 * @author Administrator
 */
public class Auth4Code {

    public static String getCode() {
        Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);
        String result = null;
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("https://api.weibo.com/oauth2/authorize");
        postMethod.addParameter("client_id", "1956772442"); //appkey
        postMethod.addParameter("redirect_uri", "http://192.168.0.168");      //oauth2 回调地址
        postMethod.addParameter("response_type", "code");
        postMethod.addParameter("action", "submit");
        
        postMethod.addParameter("userId",Config.info.account);    //微博帐号
        postMethod.addParameter("passwd", Config.info.password);    //帐号密码
        /*
        postMethod.addParameter("userId", "13910081596@139.com");    //微博帐号
        postMethod.addParameter("passwd", "abcdefg");    //帐号密码
         * 
         */
        /*postMethod.addParameter("userId", "telecom@frostchina.com");    //微博帐号
        postMethod.addParameter("passwd", "123456");    //帐号密码*/
        try {
            client.executeMethod(postMethod);

        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = postMethod.getResponseHeader("location").getValue();
        String params = url.substring(url.lastIndexOf("?") + 1);
        Map<String, String> paramsMap = new HashMap<String, String>();
        for (String s : params.split("&")) {
            String[] t = s.split("=");
            paramsMap.put(t[0], t[1]);
        }
        String code = paramsMap.get("code");
        PostMethod tokenMethod = new PostMethod("https://api.weibo.com/oauth2/access_token");
        tokenMethod.addParameter("client_id", "1956772442");       //appkey
        tokenMethod.addParameter("client_secret", "e84f41c45f0f9686218407dba5d53fe3");   //appsecret
        tokenMethod.addParameter("grant_type", "authorization_code");
        tokenMethod.addParameter("code", code);           //上一步骤拿到的code
        tokenMethod.addParameter("redirect_uri", "http://192.168.0.168");   //回调地址
        try {
            client.executeMethod(tokenMethod);
            result = tokenMethod.getResponseBodyAsString().split(",")[0].split(":")[1].replace("\"", "");
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Weibo weibo = new Weibo();
        weibo.setToken(result);
        return result;
    }
}
