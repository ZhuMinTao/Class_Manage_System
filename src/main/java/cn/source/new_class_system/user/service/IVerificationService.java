package cn.source.new_class_system.user.service;


import cn.source.new_class_system.the_class.entity.ClassRoster;
import cn.source.new_class_system.user.entity.User;

public interface IVerificationService {
    /** @PropertyDescription 邮箱登录 **/
    String emailLogin(User user);
    
    /** @PropertyDescription 账号登录 **/
    String accountLogin(User user);

    /** @PropertyDescription 用户是否存在 **/
    Boolean accountIsExist(String code);

//    /** @PropertyDescription 创建一个用户 **/
//    void createAccount(User user, UserDetail userDetail);

    /** @PropertyDescription 邮箱是否存在 **/
    Boolean emailIsExist(String email);

    /** @PropertyDescription 修改密码 **/
    void updatePassword(String email,String password);
    
    /** @PropertyDescription 用户绑定邮箱 **/
    void accountBindEmail(String email,String account);

    /** @PropertyDescription 通过邮箱获取对象 **/
    User passEmailGetUser(String email);

    /** @PropertyDescription 生成token并保存至reids **/
    String generateTokenSaveRedis(User user);

    /** @Description 花名册是否存在该学号 **/
    ClassRoster rosterInStudent(String userCode);

    /** @Description 修改绑定的邮箱 **/
    void accountUpdateBindEmail(String email, Integer userId, String updateEmail);
}
