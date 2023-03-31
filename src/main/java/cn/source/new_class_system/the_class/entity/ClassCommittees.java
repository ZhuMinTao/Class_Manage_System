package cn.source.new_class_system.the_class.entity;

import cn.source.new_class_system.user.entity.User;
import lombok.Data;

@Data
public class ClassCommittees {
    private Integer id;
    private String sn;
    private User user;
    private Integer userId;
    private Integer classId;
    private ClassSystem classSystem;
}
