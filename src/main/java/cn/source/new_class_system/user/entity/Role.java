package cn.source.new_class_system.user.entity;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private Integer id;
    private String roleName;
    private List<PowerName> powerNameList;
}
