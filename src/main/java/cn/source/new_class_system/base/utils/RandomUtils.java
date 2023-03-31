package cn.source.new_class_system.base.utils;

import java.util.Random;

public class RandomUtils {

    /**
    * @Date 2022/12/5 11:39
    * @MethodDescription 产生n位随机的数字
    */
    public static String randomNumber(Integer num){

        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for(int i = 0;i<num;i++){
            int a = random.nextInt(9);
            stringBuffer.append(a);
        }
        return stringBuffer.toString();
    }

}
