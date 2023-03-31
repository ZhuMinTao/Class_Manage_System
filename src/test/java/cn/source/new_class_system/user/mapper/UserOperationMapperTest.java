package cn.source.new_class_system.user.mapper;

import cn.source.new_class_system.Application;
import cn.source.new_class_system.user.entity.Role;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.entity.UserDetail;
import cn.source.new_class_system.user.mapper.UserOperationMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
* @Date 2022/12/2 12:08
* @ClassTitle 测试mapper
* @ClassDescription  用于测试所需要的操作User表的sql和mybatisPlus的方法使用
* @Author ZhuMT
*/
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class UserOperationMapperTest {

    @Autowired
    private UserOperationMapper userOperationMapper;

    @Autowired
    private  UserDetailOperationMapper userDetailOperationMapper;
    /**
    * @Date 2022/12/2 12:09
    * @MethodDescription mybatisPlus条件测试
    */
    @Test
    public void testUserSelectListMybatisPlus(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_number","202103090528");
        queryWrapper.eq("user_password","123456");
        List<User> users = userOperationMapper.selectList(queryWrapper);
        System.out.println(users);

    }

    /**
    * @Date 2022/12/4 12:03
    * @MethodDescription 测试是否可以添加一个为空的用户详情
    */
    @Test
    public void testUserVoidUserDetail(){
        UserDetail userDetail = new UserDetail();
        userDetail.setSn("学生");
        userDetailOperationMapper.insert(userDetail);
    }

    /**
    * @Date 2022/12/28 14:02
    * @MethodDescription 使用mybatisplus中LambdaQueryWrapper查询指定学生
    */
    @Test
    public void testSelectOneUser(){

        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.lambdaQuery();

        User user = userOperationMapper.selectOne(lambdaQueryWrapper.eq(User::getUserCode, "234232342342"));

        System.out.println(user);

    }
    
    /**
    * @Date 2022/12/28 15:51
    * @MethodDescription 测试
    */
    @Test
    public void testSelectUserDetail(){
        User user = new User();
        user.setId(28);
        System.out.println(userOperationMapper.selectUserDetail(user));

    }

}
