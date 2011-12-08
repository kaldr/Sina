
import java.net.URL;

import java.net.MalformedURLException;

import java.io.IOException;

import java.io.FileNotFoundException;

import java.io.BufferedInputStream;

import java.io.FileOutputStream;

import java.io.File;

//实现代码：
public class TestGetImageFromUrl {

    /**

     * @param args

     */
    public static void main(String[] args) {

        // TODO Auto-generated method stub

        String httpUrl = "http://ww4.sinaimg.cn/bmiddle/78039404jw1dnuupy6vawj.jpg";

        URL url;

        BufferedInputStream in;

        FileOutputStream file;

        try {

            System.out.println("获取网络图片");

            String fileName = httpUrl.substring(httpUrl.lastIndexOf("/") + 1);

            String filePath = "C:\\";

            url = new URL(httpUrl);

            in = new BufferedInputStream(url.openStream());

            file = new FileOutputStream(new File(filePath + fileName));

            int t;

            while ((t = in.read()) != -1) {

                file.write(t);

            }

            file.close();

            in.close();

            System.out.println("图片获取成功");

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
