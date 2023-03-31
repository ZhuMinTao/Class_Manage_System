package cn.source.new_class_system.base.query;

import cn.source.new_class_system.user.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class RoleQuery {
    private Integer userId;

    private List<Role> roleList;
}
