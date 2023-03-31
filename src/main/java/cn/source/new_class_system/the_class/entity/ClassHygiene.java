package cn.source.new_class_system.the_class.entity;

import cn.source.new_class_system.user.entity.User;
import lombok.Data;

@Data
public class ClassHygiene {
    private Integer id;
    private User sweepFloor;
    private User moppingFloor;
    private User cleanBlackboard;
    private String hygieneDate;
    private Integer classId;
}
