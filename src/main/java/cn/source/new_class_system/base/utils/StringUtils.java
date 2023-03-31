package cn.source.new_class_system.base.utils;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtils {

    /**
    * @Date 2023/1/17 23:17
    * @MethodDescription 对字符串进行中文编码，让文件名不会产生乱码问题
    */
    public static String fileNameChineseEncoding(String fileName){
    // 对真实文件名进行百分号编码
        String percentEncodedFileName = null;
        try {
            percentEncodedFileName = URLEncoder.encode(fileName, "utf-8")
                    .replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return percentEncodedFileName;
    }

    /**
     * @Date 2023/3/19 15:24
     * @MethodDescription 检查邮箱格式是否合法
     * @Param String
     * @Return Boolean
    */
    public static boolean checkEmail(String email)
    {// 验证邮箱的正则表达式
        System.out.println(email);
        String format = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
        //p{Alpha}:内容是必选的，和字母字符[\p{Lower}\p{Upper}]等价。如：200896@163.com不是合法的。
        //w{2,15}: 2~15个[a-zA-Z_0-9]字符；w{}内容是必选的。 如：dyh@152.com是合法的。
        //[a-z0-9]{3,}：至少三个[a-z0-9]字符,[]内的是必选的；如：dyh200896@16.com是不合法的。
        //[.]:'.'号时必选的； 如：dyh200896@163com是不合法的。
        //p{Lower}{2,}小写字母，两个以上。如：dyh200896@163.c是不合法的。
        if (email.matches(format))
        {
            return true;// 邮箱名合法，返回true
        }
        else
        {
            return false;// 邮箱名不合法，返回false
        }
    }
}
