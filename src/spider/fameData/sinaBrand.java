package spider.fameData;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 *
 * @author Administrator
 */
public class sinaBrand {

    public static List<String> alURLs = new ArrayList<String>();
    public static List<String> URLs = new ArrayList<String>();
    public static List<String> brandIDs = new ArrayList<String>();
    public static String path = "E:\\Sina\\spider\\";

    public static void main(String[] args) {
        BufferedWriter writer = null;
        try {
            analyseAlURLs();
            for (String s : alURLs) {
                analyseURLs(s);
            }
            for (String s : URLs) {
                analyseID(s);
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + "brandIDs.txt")));
            for (String s : brandIDs) {
                try {
                    writer.write(s + "\r\n");
                } catch (IOException ex) {
                }
            }
            try {
                writer.close();
            } catch (IOException ex) {
            }
        } catch (FileNotFoundException ex) {
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
            }
        }
    }

    private static void analyseID(String url) {
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

                    ///action-data=\"uid=2132157077\" action-type=
                    String alink = "action-data=\\\\\"uid=([0-9]*)\\\\\"\\saction-type=";
                    Matcher m = Pattern.compile(alink).matcher(line);
                    int count = 0;
                    while (m.find()) {
                        String al = m.group(1);
                        brandIDs.add(al);
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

    private static void analyseAlURLs() {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        String url = "http://verified.weibo.com/brand/";
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
                if (line.startsWith("<script>STK && STK.pageletM && STK.pageletM.view({\"pid\":\"pl_home_leftNav\"")) {
                    String alink = "href=\\\\\"(\\\\/brand\\\\/[a-z\\-]\\\\)[^\"]*\"";
                    Matcher m = Pattern.compile(alink).matcher(line);
                    while (m.find()) {
                        String a = "http://verified.weibo.com" + m.group(1).replace("\\", "") + "/";
                        alURLs.add(a);
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

    private static void analyseURLs(String url) {
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
            String al = "";
            int count = 0;
            while (line != null) {

                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }
                if (line.startsWith("<script>STK && STK.pageletM && STK.pageletM.view({\"pid\":\"pl_category_recommandFinal\",")) {
                    ///com\/brand\/h\/?page=2\"
                    String alink = "com\\\\/brand\\\\/([a-z-])\\\\/\\u003Fpage=([0-9]+)\\\\\"";
                    Matcher m = Pattern.compile(alink).matcher(line);
                    while (m.find()) {
                        al = m.group(1);
                        int num = Integer.parseInt(m.group(2));
                        if (num > count) {
                            count = num;
                        }
                        //URLs.add(a);

                    }

                    break;

                } else {
                    line = reader.readLine();
                }
            }
            reader.close();
            for (int i = 1; i <= count; i++) {
                String u = "http://verified.weibo.com/brand/" + al + "/?page=" + i;
                URLs.add(u);
            }
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
