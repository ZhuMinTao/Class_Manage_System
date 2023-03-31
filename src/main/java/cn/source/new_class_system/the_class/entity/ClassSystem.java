package cn.source.new_class_system.the_class.entity;

import cn.source.new_class_system.user.entity.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ClassSystem {
    private Integer id;
    private String classCode;
    private String exceptName;
    private Integer num;
    @TableField(exist = false)
    private User instructor;
    private Integer userId;
    @TableField(exist = false)
    private ClassSystemDetail classSystemDetail;
    private Integer classDetailId;
    private Integer classCount;
}
