/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

/**
 *
 * @author Administrator
 */
public class userinfo {

    public static String username = "";
    public static String uid = "";
    public static String foldername = "";
    public static String account = "";
    public static String password = "";

    userinfo(String user) {
        String[] userinfo = new String[5];
        userinfo = getUserInfo(user);
        username = userinfo[0];
        uid = userinfo[1];
        foldername = userinfo[2];
        if (userinfo.length > 3) {
            account = userinfo[3];
            password = userinfo[4];
        }

    }

    public static String[] getUserInfo(String user) {
        String[] userinfo = new String[5];
        if (user.equals("YuXing")) {
            userinfo[0] = "幸福的动感光波";
            userinfo[1] = "1912958980";
            userinfo[2] = "YuXing";

        } else if (user.equals("KaldrArt")) {
            userinfo[0] = "黄宇KaldrArt";
            userinfo[1] = "1778830754";
            userinfo[2] = user;
            userinfo[3] = "kaldr@sina.cn";
            userinfo[4] = "nevergiveup";
        } else if (user.equals("WangYuquan")) {
            userinfo[0] = "王煜全";
            userinfo[1] = "1634074550";
            userinfo[2] = user;
            userinfo[3] = "telecom@frostchina.com";
            userinfo[4] = "123456";
        } else if (user.equals("LiDongping")) {
            userinfo[0] = "LiDongPing";
            userinfo[1] = "1765769663";
            userinfo[2] = user;
            userinfo[3] = "13910081596@139.com";
            userinfo[4] = "abcdefg";
        } else if (user.equals("ZhangZike")) {
            userinfo[0] = "Zico_";
            userinfo[1] = "1877540477";
            userinfo[2] = "ZhangZike";
        } else if (user.equals("AnJiaXiaoNuo")) {
            userinfo[0] = "安家小诺";
            userinfo[1] = "1913694965";
            userinfo[2] = "AnJiaXiaoNuo";
        } else if (user.equals("ZhangQianming")) {
            userinfo[0] = "千明uestc";
            userinfo[1] = "2008266387";
            userinfo[2] = "ZhangQianming";
            userinfo[3] = "air_spire@163.com";
            userinfo[4] = "rainbow1";
        } else if (user.equals("LiKaifu")) {
            userinfo[0] = "李开复";
            userinfo[1] = "1197161814";
            userinfo[2] = "LiKaifu";
        } else if (user.equals("DaS")) {
            userinfo[0] = "大S";
            userinfo[1] = "1730336902";
            userinfo[2] = "DaS";
        }
        return userinfo;
    }
}
