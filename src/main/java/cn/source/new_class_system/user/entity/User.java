package cn.source.new_class_system.user.entity;

import cn.source.new_class_system.the_class.entity.ClassSystem;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userCode;
    private String email;
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer userDetailId;

    @TableField(exist = false)
    private UserDetail userDetail;

    private Integer classId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private ClassSystem classSystem;
}
