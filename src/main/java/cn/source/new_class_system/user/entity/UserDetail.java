package cn.source.new_class_system.user.entity;

import lombok.Data;

@Data
public class UserDetail {
    private Integer id;
    private Integer sex;
    private String sn;
    private String phoneNumber;
    private String profilePhoto;
}
