package cn.source.new_class_system.user.web.controller;

import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.user.service.IEmailSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification/email")
public class EmailSendController {

    @Autowired
    private IEmailSendService iEmailSendService;

    /**
    * @Date 2022/12/5 11:24
    * @MethodDescription 发送邮箱验证码
    */
    @GetMapping("/send/code")
    public JSONResult sendEmailCode(String email){

        iEmailSendService.sendVerificationCode(email);


        return JSONResult.getInstance();
    }
}
