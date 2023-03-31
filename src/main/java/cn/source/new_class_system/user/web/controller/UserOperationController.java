package cn.source.new_class_system.user.web.controller;

import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.entity.UserDetail;
import cn.source.new_class_system.user.service.IEmailSendService;
import cn.source.new_class_system.user.service.IUserOperationService;
import cn.source.new_class_system.user.service.IVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
* @Date 2022/12/28 15:24
* @ClassTitle 用户操作
* @ClassDescription 用于处理基本的用户操作
* @Author ZhuMT
*/
@RestController
@RequestMapping("/user")
public class UserOperationController {

    @Autowired
    private IUserOperationService iUserOperationService;

    @Autowired
    private IEmailSendService iEmailSendService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private  IVerificationService iVerificationService;
    /**
    * @Date 2022/12/28 15:27
    * @MethodDescription 查询用户详情信息
    * @Param 1. HttpServletRequest
    */

    @GetMapping("/detail/msg")
    public JSONResult selectUser(HttpServletRequest httpServletRequest){

        User user = iUserOperationService.selectUserDetail(httpServletRequest.getHeader("token"));

        return JSONResult.getInstance(user);
    }


    @PostMapping("/detail/update")
    public JSONResult updateUserDetail(@RequestBody UserDetail userDetail){
        iUserOperationService.updateUserDetail(userDetail);
        return JSONResult.getInstance();
    }


    /**
     * @Date 2023/3/19 10:23
     * @MethodDescription 更改绑定的邮箱
     */
    @PostMapping("/update_email_bind")
    public JSONResult removeEmailBind(HttpServletRequest request,@RequestParam("email") String email
            ,@RequestParam("code") String code
            ,@RequestParam("updateEmail") String updateEmail){

        //验证该邮箱的code是否合法
        iEmailSendService.verifyEmailCode(updateEmail, code);

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        //如果合法则解除绑定操作
        iVerificationService.accountUpdateBindEmail(email,user.getId(),updateEmail);

        return JSONResult.getInstance();
    }
    
    /**
     * @Date 2023/3/20 22:24
     * @MethodDescription 查询指定班级的用户列表
     * @Param
     * @Return
    */
    @GetMapping("/by/class_number")
    public JSONResult selectUserByClassId(Integer classId){

        return JSONResult.getInstance(iUserOperationService.selectUserByClassId(classId));
    }
}
