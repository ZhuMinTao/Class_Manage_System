package cn.source.new_class_system.the_class.entity;

import lombok.Data;

@Data
public class ClassWeekWork {
    private Integer id;
    private String fileUrl;
    private String workTime;
    private String remark;
    private Integer classId;
}
