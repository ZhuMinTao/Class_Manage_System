package cn.source.new_class_system.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSendUtils {

    private static JavaMailSender javaMailSender;

    private static RedisTemplate redisTemplate;

    private static String QQMailbox;


    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender){
        EmailSendUtils.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate){
        EmailSendUtils.redisTemplate = redisTemplate;
    }

    @Value("${spring.mail.username}")
    public void setQQMailBox(String username){
        EmailSendUtils.QQMailbox = username;
    }

    public static void sendEmailMessage(String title,String content,String qqCodeMail){

        //创建一个简单的消息
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        //发送端的的邮箱
        mailMessage.setFrom(QQMailbox);

        //接受的邮箱
        mailMessage.setTo(qqCodeMail);

        //设置标题
        mailMessage.setSubject(title);

        //设置内容
        mailMessage.setText(content);

        //执行发送
        javaMailSender.send(mailMessage);
    }
}
