package cn.source.new_class_system.user.web.controller;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.the_class.entity.ClassRoster;
import cn.source.new_class_system.the_class.service.IClassMessageService;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.entity.UserDetail;
import cn.source.new_class_system.user.service.IEmailSendService;
import cn.source.new_class_system.user.service.IUserOperationService;
import cn.source.new_class_system.user.service.IVerificationService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* @Date 2022/12/2 11:30
* @ClassTitle 验证操作
* @ClassDescription 用于验证登录和账号等信息是否合法化
* @Author ZhuMT
*/
@RestController
@RequestMapping("/verification")
public class VerificationController {

    @Autowired
    private IVerificationService iVerificationService;

    @Autowired
    private IEmailSendService iEmailSendService;

    @Autowired
    private IUserOperationService iUserOperationService;

    @Autowired
    private RedisTemplate redisTemplate;



    /**
    * @Date 2022/12/2 11:29
    * @MethodDescription 邮箱登录
    * @Param 1.User对象
    */


    @PostMapping("/email/login")
    public JSONResult emailLogin(String email,String code){

        //判断邮箱是否已被绑定
        Boolean emailExist = iVerificationService.emailIsExist(email);

        if(!emailExist){
            throw new GlobalException(ErrorCode.EMAIL_NONE);
        }

        //判断邮箱验证码是否合法
        Boolean emailVerify = iEmailSendService.verifyEmailCode(email, code);
        if(!emailVerify){
            throw new GlobalException(ErrorCode.EMAIL_CODE_ALREADY_OVERDUE);
        }

        

        String token = iVerificationService.generateTokenSaveRedis(iVerificationService.passEmailGetUser(email));

        return JSONResult.getInstance(token);
    }
    /**
    * @Date 2022/12/3 15:52
    * @MethodDescription 密码登录
    * @Param 1.User对象
    */
    @PostMapping("/account/login")
    public JSONResult accountLogin(@RequestBody User user){

        String token = iVerificationService.accountLogin(user);

        return JSONResult.getInstance(token);

    }

    /**
    * @Date 2022/12/3 16:44
    * @MethodDescription 验证账号是否存在
    * @Param 1. 账号
    */
    @GetMapping("account/exist/{code}")
    public JSONResult accountIsExist(@PathVariable("code") String code){

        Boolean aBoolean = iVerificationService.accountIsExist(code);

        if(!aBoolean){
            return JSONResult.getInstance(ErrorCode.USER_CODE_NONE);
        }

        return JSONResult.getInstance();
    }

    /**
    * @Date 2022/12/3 16:46
    * @MethodDescription 用户注册
    * @Param 1.map
    */
    @PostMapping("account/register")
    public JSONResult registerAccount(@RequestBody Map<String,String> map){

        User user = new User();
        UserDetail userDetail = new UserDetail();
        user.setUserCode(map.get("userCode"));
        user.setUserPassword(map.get("userPassword"));
        userDetail.setSn(map.get("sn"));
        user.setUserName(map.get("userName"));
        iUserOperationService.createAccount(user,userDetail);
        return JSONResult.getInstance();
    }

    /**
    * @Date 2022/12/16 14:54
    * @MethodDescription 邮箱注册
    * @Param 1.map
    */
    @PostMapping("email/register")
    public JSONResult emailRregisteregisterAccount(@RequestBody Map<String,String> map){

        //验证验证码是否合法
        Boolean emailVer = iEmailSendService.verifyEmailCode(map.get("email"), map.get("code"));

        if(!emailVer){
            throw new GlobalException(ErrorCode.EMAIL_CODE_ALREADY_OVERDUE);
        }

        User user = new User();

        UserDetail userDetail = new UserDetail();

        String userCode = map.get("userCode");

        //验证userCode是否存在于花名册中
        ClassRoster classRoster = iVerificationService.rosterInStudent(userCode);

        //如果在花名册中不存在该用户则抛出异常
        if(classRoster==null){
            throw new GlobalException(ErrorCode.STUDENT_NONE);
        }

        user.setUserCode(userCode);

        user.setClassId(classRoster.getClassId());

        user.setUserPassword(map.get("userPassword"));

        userDetail.setSn("学生");

        userDetail.setSex(classRoster.getSex());

        user.setUserName(classRoster.getUserName());

        user.setEmail(map.get("email"));

        iUserOperationService.createAccount(user,userDetail);

        return JSONResult.getInstance() ;
    }


    /**
    * @Date 2022/12/5 9:40
    * @MethodDescription 邮箱是否注册
    * @Param 1.email邮箱
    */
    @GetMapping("email/exist")
    public JSONResult emailIsExist(String email){

        Boolean aBoolean = iVerificationService.emailIsExist(email);

        if (!aBoolean){
            return JSONResult.getInstance(ErrorCode.EMAIL_NONE);
        }
        return JSONResult.getInstance();
    }
    
    /**
    * @Date 2022/12/5 14:51
    * @MethodDescription 修改密码
    * @Param 1.邮箱 2.验证码 3.新密码
    */
    @PostMapping("email/update/password")
    public JSONResult updatePassword(String email,String code,String password){

        //验证邮箱是否合法
        Boolean emailLegal = iEmailSendService.verifyEmailCode(email, code);

        //如果不合法返回错误
        if (!emailLegal){
            return  JSONResult.getInstance(ErrorCode.EMAIL_CODE_ALREADY_OVERDUE);
        }

        //如果验证码合法则修改密码
        iVerificationService.updatePassword(email,password);

        return JSONResult.getInstance();

    }

    /**
    * @Date 2022/12/5 23:17
    * @MethodDescription 邮箱绑定
    */
    @PostMapping("/email/bind")
    public JSONResult emailBind(String userCode,String email,String code){

        //验证该邮箱的code是否合法
        Boolean emailCodeVerity = iEmailSendService.verifyEmailCode(email, code);

        //如果不合法抛出异常
        if(!emailCodeVerity){
            throw new GlobalException(ErrorCode.EMAIL_CODE_ALREADY_OVERDUE);
        }

        //如果合法则进行绑定
        iVerificationService.accountBindEmail(email,userCode);

        return JSONResult.getInstance();
    }



}
