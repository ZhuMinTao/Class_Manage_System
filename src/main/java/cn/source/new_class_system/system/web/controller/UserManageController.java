package cn.source.new_class_system.system.web.controller;

import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.user.service.IUserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user_manage")
public class UserManageController {

    @Autowired
    private IUserOperationService iUserOperationService;

    /**
     * @Date 2023/3/23 23:38
     * @MethodDescription 查询老师列表
    */
    @GetMapping("/teacher_list")
    public JSONResult getTeacherList(){

        return JSONResult.getInstance(iUserOperationService.getTeacherList());

    }
}
