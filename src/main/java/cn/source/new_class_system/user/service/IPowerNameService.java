package cn.source.new_class_system.user.service;

import cn.source.new_class_system.user.entity.FontMenu;
import cn.source.new_class_system.user.entity.PowerName;
import cn.source.new_class_system.user.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IPowerNameService extends IService<PowerName> {

    /** @Description 获取用户权限菜单列表 **/
    List<FontMenu> getMenuList(List<PowerName> powerNameList);

    /** @Description 查询所有权限 **/
    List<Role> selectPowerList();

    /** @Description 获取某个用户的角色和权限 **/
    List<Role> getUserPowerByUserId(Integer userId);
}
