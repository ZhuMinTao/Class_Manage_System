package cn.source.new_class_system.the_class.entity;

import cn.source.new_class_system.user.entity.User;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UploadAddress {
    private Integer id;
    private String uploadDate;
    private String fileUrl;
    private Integer taskId;
    private Integer userId;
    private String suffix;
    @TableField(exist = false)
    private User user;
}
