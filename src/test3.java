
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import weibo4j.org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class test3 {

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:\\Sina\\spider\\src\\PiPiTimer.txt")));
        String PiPiTimeMachinepre = "http://weibo.com/app/aj_relateduser.php?id=3auC5p&is_att=&page=";
        String pagesub = "&_t=0&__rnd=1323253172701";
        String TimeMachinepre = "http://weibo.com/app/aj_relateduser.php?id=3DeKLj&is_att=1&page=1&_t=0&__rnd=1323166603960";
        String ZhouBoTongpre = "http://weibo.com/app/aj_relateduser.php?id=k8LoT&is_att=&page=1&_t=0&__rnd=1323166690879";
        String TimerVpre = "http://weibo.com/app/aj_relateduser.php?id=4CbBLc&is_att=&page=1&_t=0&__rnd=1323167066772";
        String TimerMicro = "http://weibo.com/app/aj_relateduser.php?id=4TCbD6&is_att=&page=1&_t=0&__rnd=1323167121251";
        String TimerMicroBlog = "http://weibo.com/app/aj_relateduser.php?id=3rpFDH&is_att=&page=1&_t=0&__rnd=1323167150335";
        String TimerShowOne = "http://weibo.com/app/aj_relateduser.php?id=5xl7bL&is_att=&page=1&_t=0&__rnd=1323167261775";
        String TimerSun = "http://weibo.com/app/aj_relateduser.php?id=2swjrd&is_att=&page=1&_t=0&__rnd=1323167435658";
        for (int i = 201; i < 202; i++){
            String PiPiTimeMachine = PiPiTimeMachinepre + i + pagesub;
            getIDUN(PiPiTimeMachine, writer);
            System.out.println(i);
        }
        writer.close();
    }

    public static void getIDUN(String url, BufferedWriter writer) throws IOException {
        String matchname = "<a\\shref=\"/[^\"]*\"\\stitle=\"([^\"]*)\"\\starget=\"_blank\"><img src=";
        String matchfunnum = "<strong>([0-9]*)</strong>";
        String matchid = "action-data=\"userid=([0-9]*)\"><img";
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
            String r = getMethod.getResponseBodyAsString();//

            JsonFactory jsonFactory = new MappingJsonFactory();
            JsonParser jsonParser = jsonFactory.createJsonParser(r);
            jsonParser.nextToken();
            HashMap<String, String> map = new HashMap<String, String>();
            // while循环遍历Json结果
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                // 跳转到Value
                jsonParser.nextToken();
                // 将Json中的值装入Map中
                map.put(jsonParser.getCurrentName(), jsonParser.getText());
            }
            String data = map.get("data");
            ByteArrayInputStream stream = new ByteArrayInputStream(data.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = reader.readLine();
            String username = "";
            int funnum = 0;
            String id = "";
            while (line != null) {
                if (!line.isEmpty()) {

                    Matcher m = Pattern.compile(matchname).matcher(line);
                    while (m.find()) {
                        username = m.group(1);
                        System.out.print(m.group(1) + " ");
                    }
                    m = Pattern.compile(matchfunnum).matcher(line);
                    while (m.find()) {
                        funnum = Integer.parseInt(m.group(1));
                        System.out.print(m.group(1) + " ");
                    }
                    m = Pattern.compile(matchid).matcher(line);
                    while (m.find()) {
                        id = m.group(1);
                        System.out.print(m.group(1) + "\n");
                        if (funnum > 1000) {
                            writer.write(id + " " + username + " " + funnum + "\r\n");
                        }
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
