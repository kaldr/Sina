/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

public class Config {
    //User infomations
    
    public static String user = "ZhangQianming";
    public static final userinfo info=new userinfo(user);
    public static final String folderName = userinfo.foldername;
    public static String uid = userinfo.uid;
    public static String username = userinfo.username;
    public static String path = "E:\\Sina\\weibo4j-oauth2-beta\\weibo4j-oauth2\\src\\network\\" + folderName + "\\";
    //Database Parametors
    public static String db_driver = "com.mysql.jdbc.Driver";
    public static String db_url = "jdbc:mysql://localhost:3306/";
    public static String db_name = "WeiboNews?useUnicode=true&characterEncoding=gb2312";
    public static String db_username = "root";
    public static String db_password = "";
    public static String db_max = "10";
    //Comments
    public static int amount = 400;
    public static int iDate=7;
    
}
