package cn.source.new_class_system.the_class.entity;

import cn.source.new_class_system.user.entity.User;
import lombok.Data;

@Data
public class ClassCourseSix {
    private Integer id;
    private User greetOne;
    private User greetTwo;
    private User presideOver;
    private User lectureOne;
    private User lectureTwo;
    private String courseSixDate;
    private Integer classId;
}
