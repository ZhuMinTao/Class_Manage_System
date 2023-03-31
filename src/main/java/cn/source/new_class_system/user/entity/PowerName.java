package cn.source.new_class_system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_power")
public class PowerName {
    private Integer id;
    private String powerName;
    private String url;
    private String powerIntroduce;
    private Integer menuId;
}
