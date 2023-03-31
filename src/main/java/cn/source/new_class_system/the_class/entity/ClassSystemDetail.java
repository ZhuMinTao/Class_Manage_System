package cn.source.new_class_system.the_class.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ClassSystemDetail {
    private Integer id;
    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private String classIntroduce;
    private String classRole;
    private String classHonor;

}
