/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class test2 {
    public static void main(String[] args) {
        String url="http://verified.weibo.com/agency/z/?page=4";
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            /*4 判断访问的状态码*/
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            InputStream response = getMethod.getResponseBodyAsStream();//
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            String line = reader.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }
                if (line.startsWith("<script>STK && STK.pageletM && STK.pageletM.view({\"pid\":\"pl_category_recommandFinal\",")) {
                    System.out.println("found");
                    ///action-data=\"uid=2132157077\" action-type=
                    String alink="action-data=\\\\\"uid=([0-9]*)\\\\\"\\saction-type=";
                    Matcher m = Pattern.compile(alink).matcher(line);
                    int count=0;
                    while (m.find()) {
                        String al = m.group(1);
                        System.out.println(al);                        
                    }
                    break;

                } else {
                    line = reader.readLine();
                }
            }
            reader.close();
            
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            /*6 .释放连接*/
            getMethod.releaseConnection();
        }
        
    }
}
