
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class test4 {
    
    public static void main(String[] args) throws IOException {

        String url = "http://verified.weibo.com/fame/1712?srt=4/?rt=0&srt=4&letter=e&page=17";
        String findMark = "<script>STK && STK.pageletM && STK.pageletM.view({\"pid\":\"pl_category_recommandFinal\"";
        String match = "uid=([0-9]*)&extra=";

        GetMethod getMethod = new GetMethod(url);
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            int statusCode = client.executeMethod(getMethod);
            /*4 判断访问的状态码*/
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
                            //String a = url + sub + "/";
                            System.out.println(sub);
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
}
