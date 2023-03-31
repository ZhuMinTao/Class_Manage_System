package cn.source.new_class_system.user.service.imple;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.utils.MD5Utils;
import cn.source.new_class_system.the_class.entity.ClassRoster;
import cn.source.new_class_system.the_class.mapper.ClassRosterMapper;
import cn.source.new_class_system.user.entity.PowerName;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.mapper.PowerNameMapper;
import cn.source.new_class_system.user.mapper.UserDetailOperationMapper;
import cn.source.new_class_system.user.mapper.UserOperationMapper;
import cn.source.new_class_system.user.service.IVerificationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VerificationServiceImpl implements IVerificationService {

    /** @PropertyDescription 用户账号操作 **/
    @Autowired
    private UserOperationMapper userOperationMapper;
    /** @PropertyDescription 用户账号信息操作 **/
    @Autowired
    private UserDetailOperationMapper userDetailOperationMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ClassRosterMapper classRosterMapper;

    @Autowired
    private PowerNameMapper powerNameMapper;

    @Override
    public String emailLogin(User user) {
        return null;
    }

    @Override
    public String accountLogin(User user) {
        //1.查询是否存在该账户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_code",user.getUserCode());

        queryWrapper.eq("user_password",user.getUserPassword());

        List<User> users = userOperationMapper.selectList(queryWrapper);

        //2.判断是否为0，是则抛出用户名或密码错误
        if(users.size()==0){
            throw new GlobalException(ErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }

        //3.生成token并保存至redis
        return generateTokenSaveRedis(users.get(0));

    }

    @Override
    public String generateTokenSaveRedis(User user){

        String token = MD5Utils.encryption(String.valueOf(new Date().getTime()));

        //1.查询用户权限列表
        List<PowerName> powerNames = powerNameMapper.selectUserPowerList(user.getId());
        //2.保存用户权限到redis
        redisTemplate.opsForValue().set("power:"+token,powerNames);
        //token30分钟失效
//        redisTemplate.opsForValue().set(token,user, Duration.ofMinutes(30));

        redisTemplate.opsForValue().set(token,user);

        return token;

    }

    @Override
    public ClassRoster rosterInStudent(String userCode) {
        return classRosterMapper.selectOne(Wrappers.lambdaQuery(ClassRoster.class)
                .eq(ClassRoster::getStuCode,userCode));
    }

    @Override
    public void accountUpdateBindEmail(String email, Integer userId, String updateEmail) {

        //判断是否与原来的邮箱一致
        if(email.equals(updateEmail)){
            throw new GlobalException(ErrorCode.PARAMETER_ERROR);
        }
        //查询修改后的邮箱是否已经被绑定
        if(emailIsExist(updateEmail)){
            throw new GlobalException(ErrorCode.EMAIL_ALREADY_EXIST);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("id",userId);

        User user = userOperationMapper.selectOne(queryWrapper);

        if(!user.getEmail().equals(email)){
            throw new GlobalException(ErrorCode.PARAM_NO_MATE);
        }

        user.setEmail(updateEmail);

        userOperationMapper.updateById(user);

    }

    @Override
    public Boolean accountIsExist(String code) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_code",code);

        return userOperationMapper.exists(queryWrapper);

    }



    @Override
    public Boolean emailIsExist(String email) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("email",email);

        return userOperationMapper.exists(queryWrapper);

    }

    @Override
    public void updatePassword(String email, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("email",email);

        User user = userOperationMapper.selectOne(queryWrapper);

        user.setUserPassword(password);

        userOperationMapper.updateById(user);
    }

    @Override
    public void accountBindEmail(String email, String account) {

        Boolean aBoolean = emailIsExist(email);
        if(aBoolean){
            throw new GlobalException(ErrorCode.ALREADY_BE_BIND);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_code",account);

        User user = userOperationMapper.selectOne(queryWrapper);

        user.setEmail(email);

        userOperationMapper.updateById(user);

    }

    @Override
    public User passEmailGetUser(String email) {

        QueryWrapper<User> queryWrapper  = new QueryWrapper<>();

        queryWrapper.eq("email",email);

        User user = userOperationMapper.selectOne(queryWrapper);

        return user;
    }

}
