/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Administrator
 */
public class test {

    public static void main(String[] arg) {
        String a = "中国";
        System.out.println(a);
        String ab = string2Unicode("更多");
        String abc = chinaToUnicode("更多");
        System.out.println(abc);
        System.out.println(ab);
        String ccc="\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\u3002";
        ccc.replace("\\", "");
        System.out.println(unicode2String(ccc));
    }

    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {//汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }
static String unicode2String(String unicodeStr){
    StringBuffer sb = new StringBuffer();
    String str[] = unicodeStr.toUpperCase().split("U");
    for(int i=0;i<str.length;i++){
      if(str[i].equals("")) continue;
      char c = (char)Integer.parseInt(str[i].trim(),16);
      sb.append(c);
    }
    return sb.toString();
  }
    static String string2Unicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 2; i < bytes.length - 1; i += 2) {
                out.append("u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);

                out.append(str);
                out.append(str1);
                out.append(" ");
            }
            return out.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
