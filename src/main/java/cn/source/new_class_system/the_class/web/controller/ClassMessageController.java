package cn.source.new_class_system.the_class.web.controller;

import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.the_class.entity.ClassCommittees;
import cn.source.new_class_system.the_class.entity.ClassSystem;
import cn.source.new_class_system.the_class.entity.ClassSystemDetail;
import cn.source.new_class_system.the_class.service.IClassMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* @Date 2023/1/3 20:13
* @ClassTitle 班级信息
* @ClassDescription 用于查询班级基本状态已经有关于班级相关信息查询
* @Author ZhuMT
*/
@Slf4j
@RequestMapping("/class_msg")
@RestController
public class ClassMessageController {

    @Autowired
    private IClassMessageService iClassMessageService;

    /**
    * @Date 2023/1/3 20:14
    * @MethodDescription 查询详情班级介绍
    * @Param 1.班级id
    */
    @GetMapping("/class_introduce")
    public JSONResult selectClassBasicMessage(Integer classId){

        return JSONResult.getInstance(iClassMessageService.selectClassIntroduce(classId));
    }


    /**
    * @Date 2023/1/5 18:24
    * @MethodDescription 查询班委会成员
    * @Param 1.班级id
    */
    @GetMapping("/class_committee")
    public JSONResult selectCommittees(Integer classId){

        return JSONResult.getInstance(iClassMessageService.selectCommittee(classId));
    }

    /**
     * @Date 2023/3/22 14:25
     * @MethodDescription 删除班委
    */
    @PostMapping("/delete_committee")
    public JSONResult deleteCommittees(Integer id){

        iClassMessageService.deleteCommittees(id);

        return JSONResult.getInstance();
    }

    /**
    * @Date 2023/1/5 18:53
    * @MethodDescription 查询班级基本信息
    * @Param 1.班级id
    */
    @GetMapping("/class_basic")
    public JSONResult selectClassBasic(Integer classId){

        return JSONResult.getInstance(iClassMessageService.selectClassBasic(classId));
    }


    /**
    * @Date 2023/1/5 19:56
    * @MethodDescription 查询班级花名册
    * @Param 1. 班级id
    */
    @GetMapping("/class_roster")
    public JSONResult selectClassRoster(Integer classId){

        return JSONResult.getInstance(iClassMessageService.selectClassRoster(classId));
    }
    
    /**
     * @Date 2023/3/22 13:36
     * @MethodDescription 修改班级信息
    */
    @PostMapping("/update_class_introduce")
    public JSONResult updateClassIntroduce(@RequestBody ClassSystemDetail classSystemDetail){

        iClassMessageService.updateClassSystemDetail(classSystemDetail);

        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/22 15:34
     * @MethodDescription 新增班委信息
    */
    @PostMapping("/add_committees")
    public JSONResult addCommittees(@RequestBody ClassCommittees classCommittees){

        iClassMessageService.insertClassCommittees(classCommittees);

        return JSONResult.getInstance();
    }




}
