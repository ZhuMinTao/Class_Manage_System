package cn.source.new_class_system.the_class.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UploadTask {
    private Integer id;
    private String publishDate;
    private String finishDate;
    private String taskComment;
    private Integer completeStatus;
    private Integer classId;
    private String issuer;
    @TableField(exist = false)
    private Integer userCompleteStatus;
}
