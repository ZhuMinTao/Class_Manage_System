package cn.source.new_class_system.user.service;

import cn.source.new_class_system.Application;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.entity.UserDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
* @Date 2022/12/3 16:17
* @ClassTitle 测试User的service层
* @ClassDescription 用于测试service层功能是否可以实现
* @Author ZhuMT
*/
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class UserOperationServiceTest {

    @Autowired
    private IVerificationService iVerificationService;

    @Autowired
    private IUserOperationService iUserOperationService;

    @Autowired
    private IEmailSendService iEmailSendService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
    * @Date 2022/12/3 16:19
    * @MethodDescription 测试登录登录功能
    * @Param 1.User对象
    */
    @Test
    public void testUserLoginService(){
        User user = new User();
        user.setUserCode("202103090527");
        user.setUserPassword("123");
        String s = iVerificationService.accountLogin(user);
        System.out.println(s);
    }

    /**
    * @Date 2022/12/3 16:39
    * @MethodDescription 判断用户是否存在
    */
    @Test
    public void testUserIsExist(){
        iVerificationService.accountIsExist("202103090528");
    }

    /**
    * @Date 2022/12/3 16:50
    * @MethodDescription 创建一个用户 并创建其详情用户的详情
    */
    @Test
    public void testCreateAccount(){
        User user = new User();
        user.setUserCode("202103090501");
        user.setUserPassword("123");
        user.setEmail("1988822@qq.com");

        UserDetail userDetail = new UserDetail();
        userDetail.setSn("学生");
        iUserOperationService.createAccount(user, userDetail);
    }

    /**
    * @Date 2022/12/5 10:32
    * @MethodDescription 测试发送邮箱
    */
    @Test
    public void testSendEmail(){
        iEmailSendService.sendVerificationCode("1981166068@qq.com");
    }

    /**
    * @Date 2022/12/5 15:26
    * @MethodDescription 测试修改密码
    */
    @Test
    public  void testUpdatePassword(){
        iVerificationService.updatePassword("1988822@qq.com","321");
    }

    /**
    * @Date 2022/12/5 15:50
    * @MethodDescription 验证邮箱是否合法化
    */
    @Test
    public void testEmailVerify(){
        Boolean aBoolean = iEmailSendService.verifyEmailCode("1981166068@qq.com", "730461");
        System.out.println(aBoolean);
    }
    
    /**
    * @Date 2022/12/5 16:41
    * @MethodDescription redis删除指定的key
    */
    @Test
    public void testDeleteRedisKey(){
//        redisTemplate.opsForValue().set("key","value");
        redisTemplate.delete("key");
    }

 
}
