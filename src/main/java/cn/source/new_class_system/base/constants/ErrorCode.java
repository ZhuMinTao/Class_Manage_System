package cn.source.new_class_system.base.constants;

 /**
  * @Date 2022/11/17 15:14
  * @ClassTitle 返回错误
  * @ClassDescription 用于反馈给用户错误信息内容
  * @Author ZhuMT
  */
public enum ErrorCode{

     USERNAME_OR_PASSWORD_ERROR(403,"用户名或密码错误"),

     EMAIL_ALREADY(403,"操作频繁,请稍后重试"),

     SEND_EMAIL_NONE(403,"未发送验证码"),

     EMAIL_CODE_ALREADY_OVERDUE(403,"验证码已过期或不合法"),

     USER_CODE_NONE(404,"账号不存在"),

     USER_CODE_ALREADY_EXIST(500,"账号已存在"),

     EMAIL_ALREADY_EXIST(500,"邮箱已被其它账号绑定"),

     LOGIN_NONE(401,"未登录"),

     INSIDE_THE_SERVER_ERROR(500,"服务器内部错误"),

     EMAIL_NONE(404, "邮箱不存在"),

     HOME_NONE_BIND(500, "该类型轮播图不能绑定班级"),

     CLASS_NONE_FIND(404,"未有该班级"),

     NONE_FIND_RESOURCE(404,"未找到资源"),

     FILE_NONE_DATA(404,"该文件无数据"),

     PARAMETER_ERROR(500,"参数错误"),

     EXPORT_ERROR(500, "导出错误,请联系管理员"),

     TASK_NONE_EXISTS(404, "任务不存在"),

     NOT_CAN_IS_NULL(500, "参数不能为NULL"),

     ALREADY_SUBMIT(403,"已提交" ),

     FILL_MAX_ERROR(500,"文件过大" ),

     NETWORK_REQUEST_ERROR(500, "服务器网络请求异常"),

     STUDENT_NONE(404, "学号不存在"),

     REPETITION_VALUE(500, "重复值"),

     NONE_POWER(403, "无权访问"),

     ALREADY_BIND(500  , "已绑定"),

     ALREADY_BE_BIND(500, "已被绑定"),

     NONE_ALREADY_BIND(500, "未绑定"),

     PARAM_NO_MATE(500, "上传参数,与原绑定不匹配");

    private Integer code;

    private String message;

     public Integer getCode() {
         return code;
     }

     public String getMessage() {
         return message;
     }

     ErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
