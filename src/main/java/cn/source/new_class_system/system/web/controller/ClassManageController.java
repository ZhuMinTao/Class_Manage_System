package cn.source.new_class_system.system.web.controller;

import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.system.service.IClassManageService;
import cn.source.new_class_system.the_class.entity.ClassSystem;
import cn.source.new_class_system.user.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/system_class")
public class ClassManageController {

    @Autowired
    private IClassManageService iClassManageService;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * @Date 2023/3/23 12:13
     * @MethodDescription 查询管理班级
    */
    @GetMapping("/manage_msg")
    public JSONResult getManageClassMessage(HttpServletRequest request, @RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) Integer pageNum){

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        return JSONResult.getInstance(iClassManageService.getAllClassMessage(false,user.getId(),pageSize,pageNum));

    }

    /**
     * @Date 2023/3/23 12:13
     * @MethodDescription 查询管理班级
     */
    @GetMapping("/all_msg")
    public JSONResult getAllClassMessage(HttpServletRequest request, @RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) Integer pageNum){

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        return JSONResult.getInstance(iClassManageService.getAllClassMessage(true,user.getId(),pageSize,pageNum));

    }

    /**
     * @Date 2023/3/23 14:31
     * @MethodDescription 删除指定班级
    */
    @Transactional
    @PostMapping("/delete_point")
    public JSONResult deletePoint(Integer classId){

        iClassManageService.deletePointClass(classId);

        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/23 14:41
     * @MethodDescription 新增班级
    */
    @PostMapping("/insert_class")
    public JSONResult insertClassContent(@RequestBody ClassSystem classSystem){

        iClassManageService.insertClassContent(classSystem);

        return JSONResult.getInstance();

    }

    /**
     * @Date 2023/3/24 14:12
     * @MethodDescription 删除班级花名册
    */
    @PostMapping("/delete_roster")
    public JSONResult deleteRoster(@RequestParam Integer classId){

        iClassManageService.deleteRoster(classId);

        return JSONResult.getInstance();
    }
}
