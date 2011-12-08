/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spider.fameData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 *
 * @author Administrator
 */
public class sinaFameAll {

    public static List<String> lever1stUrl = new ArrayList<String>();
    public static List<String> lever2ndUrl = new ArrayList<String>();
    public static List<String> lever3rdUrl = new ArrayList<String>();
    public static List<String> lever4thUrl = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        getLevel1stURL();
    }

    public static void getLevel1stURL() throws IOException {
        String url = "http://verified.weibo.com/fame/";
        String findMark = "<script>STK && STK.pageletM && STK.pageletM.view({\"pid\":\"pl_home_leftNav\"";
        String match = "<a\\shref=\\\\\"\\\\/fame\\\\/([^\\\\]*)\\\\\"\\sclass=\\\\\"child_link\\\\\">";

        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            int statusCode = client.executeMethod(getMethod);
            /*判断访问的状态码*/
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            InputStream response = getMethod.getResponseBodyAsStream();//
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            String line = reader.readLine();
            while (line != null) {
                if (!line.isEmpty()) {
                    if (line.startsWith(findMark)) {
                        Matcher m = Pattern.compile(match).matcher(line);
                        while (m.find()) {
                            String sub = m.group(1);
                            String a = url + sub + "/";
                            if (sub.equals("anhui?srt=4&city=1")) {
                                break;
                            }
                            print(a);
                            lever1stUrl.add(sub);
                        }
                        break;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (HttpException e) {
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        }
    }

    public static void getLevel2ndURL(String url) throws IOException {

        String findMark = "<script>STK && STK.pageletM && STK.pageletM.view({\"pid\":\"pl_home_leftNav\"";
        String match = "<a href=\\\\\"\\\\/[^\\\\]*\\\\/([^\\\\]*)\\\\\" class=\\\\\"a_link\\\\\">";

        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {

            int statusCode = client.executeMethod(getMethod);
            /*判断访问的状态码*/
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            InputStream response = getMethod.getResponseBodyAsStream();//
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            String line = reader.readLine();
            while (line != null) {
                if (!line.isEmpty()) {
                    if (line.startsWith(findMark)) {
                        Matcher m = Pattern.compile(match).matcher(line);
                        while (m.find()) {
                            String sub = m.group(1).replace("\\", "");
                            String a = url + sub + "/";
                            if (sub.equals("anhui")) {
                                break;
                            }
                            lever1stUrl.add(a);
                        }
                        break;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (HttpException e) {
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        }
    }

    public static void print(String s) {
        System.out.println(s);
    }
}
