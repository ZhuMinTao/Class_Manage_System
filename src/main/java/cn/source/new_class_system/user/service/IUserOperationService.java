package cn.source.new_class_system.user.service;

import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.entity.UserDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface IUserOperationService {
    /** @PropertyDescription 创建一个用户 **/
    void createAccount(User user, UserDetail userDetail);

    /** @PropertyDescription 查询用户详情 **/
    User selectUserDetail(String token);

    /** @Description 修改用户详情 **/
    void updateUserDetail(UserDetail userDetail);

    /** @Description 查询指定班级的用户 **/
    List<User> selectUserByClassId(Integer classId);

    /** @Description 查询老师列表 **/
    List<User> getTeacherList();
}
