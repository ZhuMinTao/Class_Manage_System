package cn.source.new_class_system.user.service.imple;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.utils.EmailSendUtils;
import cn.source.new_class_system.base.utils.RandomUtils;
import cn.source.new_class_system.base.utils.StringUtils;
import cn.source.new_class_system.user.service.IEmailSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class EmailSendServiceImpl implements IEmailSendService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void sendVerificationCode(String email) {


        if(!StringUtils.checkEmail(email)){
            throw new GlobalException(ErrorCode.PARAMETER_ERROR);
        }

        String oldRandCode = null;

        try{
            oldRandCode = (String)redisTemplate.opsForValue().get(email);
        }catch (IllegalArgumentException Se){
            oldRandCode = null;
        }

        //判断邮箱是否在redis中，如果存在抛出异常
        if(oldRandCode!=null){
            throw new GlobalException(ErrorCode.EMAIL_ALREADY);
        }

        String randCode = RandomUtils.randomNumber(6);

        redisTemplate.opsForValue().set(email,randCode, Duration.ofMinutes(1));

        String content = "【班级系统提示】您的验证码是"+randCode+"，(5分钟内有效)。如非本人操作，请忽略本条邮箱!";
        try{
            log.info("验证码:{}",randCode);
//            EmailSendUtils.sendEmailMessage("验证码提示",content,email);
        }catch (MailSendException e){
            //lllololll
            redisTemplate.delete(email);
        }
    }

    @Override
    public Boolean verifyEmailCode(String email, String code) {
        String oldRandCode = "";

        try{
            oldRandCode = (String)redisTemplate.opsForValue().get(email);
        }catch (IllegalArgumentException e){
            oldRandCode = "";
        }

        if(oldRandCode==null){
            throw new GlobalException(ErrorCode.SEND_EMAIL_NONE);
        }


        //判断用户提交的验证码和redis中的验证码是否一致
        boolean equals = oldRandCode.equals(code);
        if (equals){
            //如果一致清除redis中的验证码
            redisTemplate.delete(email);
        }

        return equals;
    }
}
