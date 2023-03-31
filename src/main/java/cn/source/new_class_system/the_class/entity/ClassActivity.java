package cn.source.new_class_system.the_class.entity;

import cn.source.new_class_system.user.entity.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClassActivity {
    private Integer id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer scheme;
    @TableField(exist = false)
    private User schemeUser;
    private String activityContent;
    private String activityDate;
    private String activityPlace;
    private String activityImage;
    private Integer classId;
}
