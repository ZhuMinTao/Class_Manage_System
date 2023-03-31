package cn.source.new_class_system.user.service.imple;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.entity.UserDetail;
import cn.source.new_class_system.user.mapper.UserDetailOperationMapper;
import cn.source.new_class_system.user.mapper.UserOperationMapper;
import cn.source.new_class_system.user.service.IUserOperationService;
import cn.source.new_class_system.user.service.IVerificationService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
public class UserOperationServiceImpl implements IUserOperationService {

    @Autowired
    private UserDetailOperationMapper userDetailOperationMapper;

    @Autowired
    private UserOperationMapper userOperationMapper;

    @Autowired
    private IVerificationService iVerificationService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void createAccount(User user, UserDetail userDetail) {


        Boolean accountExist = iVerificationService.accountIsExist(user.getUserCode());

        if(accountExist){
            throw new GlobalException(ErrorCode.USER_CODE_ALREADY_EXIST);
        }

        Boolean emailExist = iVerificationService.emailIsExist(user.getEmail());
        if(emailExist){
            throw new GlobalException(ErrorCode.EMAIL_ALREADY_EXIST);
        }
        userDetailOperationMapper.insert(userDetail);

        user.setUserDetailId(userDetail.getId());

        userOperationMapper.insert(user);

    }

    @Override
    public User selectUserDetail(String token) {

        User user =(User) redisTemplate.opsForValue().get(token);

        return  userOperationMapper.selectUserDetail(user);
    }

    @Override
    public void updateUserDetail(UserDetail userDetail) {
        userDetailOperationMapper.updateById(userDetail);
    }

    @Override
    public List<User> selectUserByClassId(Integer classId) {
        return userOperationMapper.selectList(Wrappers.lambdaQuery(User.class).eq(User::getClassId,classId));
    }

    @Override
    public List<User> getTeacherList() {

        List<User> users = userOperationMapper.selectList(Wrappers.lambdaQuery(User.class)
                .inSql(User::getUserDetailId, "select id from tb_user_detail where sn='老师'"));

        return users;
    }
}
