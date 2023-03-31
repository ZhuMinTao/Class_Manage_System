package cn.source.new_class_system.user.web.controller;

import cn.source.new_class_system.base.query.RoleQuery;
import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.user.entity.PowerName;
import cn.source.new_class_system.user.entity.Role;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.entity.UserRole;
import cn.source.new_class_system.user.service.IPowerNameService;
import cn.source.new_class_system.user.service.IUserRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/power")
public class PowerNameController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IPowerNameService iPowerNameService;

    @Autowired
    private IUserRoleService iUserRoleService;
    
    /**
     * @Date 2023/3/22 20:43
     * @MethodDescription 获取权限对应的菜单列表
    */
    @GetMapping("/menu_list")
    public JSONResult getMenuList(HttpServletRequest request){

        List<PowerName> powerNameList =  (List<PowerName>)redisTemplate.opsForValue().get("power:"+request.getHeader("token"));

        return JSONResult.getInstance( iPowerNameService.getMenuList(powerNameList));
    }
    
    /**
     * @Date 2023/3/24 16:06
     * @MethodDescription 查询所有权限
    */
    @GetMapping("/power_list")
    public JSONResult getPowerList(){
        return JSONResult.getInstance(iPowerNameService.selectPowerList());
    }

    /**
     * @Date 2023/3/25 16:06
     * @MethodDescription 设置用户权限
    */
    @PostMapping("/power_user_set")
    public JSONResult setUserPower(@RequestBody RoleQuery roleQuery){

        iUserRoleService.remove(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getUserId,roleQuery.getUserId()));

        roleQuery.getRoleList().forEach(e->{
            UserRole userRole = new UserRole();
            userRole.setUserId(roleQuery.getUserId());
            userRole.setRoleId(e.getId());
            iUserRoleService.save(userRole);
        });
        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/25 16:33
     * @MethodDescription 获取某个用户的权限
    */
    @GetMapping("/user_power")
    public JSONResult getUserPowerByUserId(Integer userId){


        return JSONResult.getInstance(iPowerNameService.getUserPowerByUserId(userId));
    }

    /**
     * @Date 2023/3/27 12:54
     * @MethodDescription 设置用户的权限
    */
    @PostMapping("/set_user_role")
    public JSONResult setUserRole(Integer userId,List<Integer> roleList){

        return JSONResult.getInstance();
    }
}
