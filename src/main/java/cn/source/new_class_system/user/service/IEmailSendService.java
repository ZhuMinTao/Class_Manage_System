package cn.source.new_class_system.user.service;

public interface IEmailSendService {

    /** @PropertyDescription 给指定邮箱发送验证码 **/
    void sendVerificationCode(String email);

    /** @PropertyDescription 验证邮箱贺验证码是否合法 **/
    Boolean verifyEmailCode(String email,String code);

}
