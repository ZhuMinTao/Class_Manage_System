package cn.source.new_class_system.user.service.imple;

import cn.source.new_class_system.user.entity.FontMenu;
import cn.source.new_class_system.user.entity.PowerName;
import cn.source.new_class_system.user.entity.Role;
import cn.source.new_class_system.user.mapper.FontMenuMapper;
import cn.source.new_class_system.user.mapper.PowerNameMapper;
import cn.source.new_class_system.user.service.IPowerNameService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PowerNameServiceImpl extends ServiceImpl<PowerNameMapper, PowerName> implements IPowerNameService {

    @Autowired
    private FontMenuMapper fontMenuMapper;

    @Autowired
    private PowerNameMapper powerNameMapper;

    @Override
    public List<FontMenu> getMenuList(List<PowerName> powerNameList) {
        List<FontMenu> menuList = new ArrayList<>();
        //进行去重获取所有菜单id
        List<Integer> collect = powerNameList.stream().map(e -> {
            return e.getMenuId();
        }).distinct().collect(Collectors.toList());

        collect.stream().forEach(e->{
            FontMenu fontMenu = fontMenuMapper.selectOne(Wrappers.lambdaQuery(FontMenu.class).eq(FontMenu::getId, e));
            menuList.add(fontMenu);
        });

        return menuList;
    }

    @Override
    public List<Role> selectPowerList() {

        return powerNameMapper.selectPowerList();
    }

    @Override
    public List<Role> getUserPowerByUserId(Integer userId) {


        return powerNameMapper.selectUserRoleListByUserId(userId);
    }
}
