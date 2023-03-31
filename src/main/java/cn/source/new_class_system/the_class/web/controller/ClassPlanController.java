package cn.source.new_class_system.the_class.web.controller;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.the_class.entity.*;
import cn.source.new_class_system.the_class.service.IClassPlanService;
import cn.source.new_class_system.user.entity.User;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/plan")
public class ClassPlanController {

    @Autowired
    private IClassPlanService iClassPlanService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
    * @Date 2023/1/3 10:55
    * @MethodDescription 查询卫生安排
    * @Param 1. 班级id 2.时间
    */
    @GetMapping("/hygiene")
    public JSONResult selectClassHygiene(Integer classId,String date,String afterDate){

        return JSONResult.getInstance(iClassPlanService.selectClassHygiene(classId,date,afterDate));

    }

    /**
     * @Date 2023/3/2 19:20
     * @MethodDescription 新增卫生安排
    */
    @PostMapping("/publish_heygiene")
    public JSONResult insertClassHygiene(@RequestBody ClassHygiene classHygiene){

        iClassPlanService.insertClassHygiene(classHygiene);

        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/2 19:56
     * @MethodDescription 修改卫生安排
    */
    @PostMapping("/update_heygiene")
    public JSONResult updateClassHygiene(@RequestBody ClassHygiene classHygiene){
        iClassPlanService.updateClassHygiene(classHygiene);
        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/2 20:04
     * @MethodDescription 删除卫生安排
    */
    @PostMapping("/delete_heygiene")
    public JSONResult deleteClassHygiene(Integer id){
        System.out.println(id);
        iClassPlanService.deleteClassHygiene(id);
        return JSONResult.getInstance();
    }





    /**
    * @Date 2023/1/3 13:47
    * @MethodDescription 查询课六信息
    * @Param 1. 班级id 2.时间
    */
    @GetMapping("/course_six")
    public JSONResult selectCourseSixMessage(Integer classId,String date,String afterDate){

        return JSONResult.getInstance(iClassPlanService.selectCourseSixMessage(classId,date,afterDate));
    }
    
    
    /**
     * @Date 2023/3/2 20:11
     * @MethodDescription 新增课六信息
    */
    @PostMapping("/publish_six")
    public JSONResult insertCourseSixMessage(@RequestBody ClassCourseSix classCourseSix){

        iClassPlanService.insertCourseSixMessage(classCourseSix);

        return JSONResult.getInstance();

    }

    /**
     * @Date 2023/3/3 11:33
     * @MethodDescription 修改课六安排
    */
    @PostMapping("/update_six")
    public JSONResult updateCourseSixMessage(@RequestBody ClassCourseSix classCourseSix){

        iClassPlanService.updateCourseSixMessage(classCourseSix);

        return JSONResult.getInstance();

    }
    
    /**
     * @Date 2023/3/3 11:45
     * @MethodDescription 删除课六安排
    */
    @PostMapping("/delete_six")
    public JSONResult deleteClassSix(Integer id){
        iClassPlanService.deleteClassSix(id);
        return JSONResult.getInstance();
    }
    
    /**
    * @Date 2023/1/3 14:43
    * @MethodDescription 查询活动安排
    * @Param 1.班级id 2.时间
    */
    @GetMapping("/activity")
    public JSONResult selectActivity(Integer classId,Boolean isDate,Integer pageNum){

        Page<ClassActivity> classActivityPage = iClassPlanService.selectActivity(classId, isDate, pageNum);

        return JSONResult.getInstance(classActivityPage);
    }
    /**
     * @Date 2023/3/3 11:49
     * @MethodDescription 新增活动安排
    */
    @PostMapping("/publish_activity")
    public JSONResult insertActivity(@RequestBody ClassActivity classActivity){

        iClassPlanService.insertClassActivity(classActivity);

        return JSONResult.getInstance();
    }
    /**
     * @Date 2023/3/3 12:01
     * @MethodDescription 修改活动安排
    */
    @PostMapping("/update_activity")
    public JSONResult updateClassActivity(@RequestBody ClassActivity classActivity){

        iClassPlanService.updateClassActivity(classActivity);

        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/3 12:06
     * @MethodDescription 删除活动安排
     * @Param 1.活动id
    */
    @PostMapping("/delete_activity")
    public JSONResult deleteClassActivity(Integer id){
        iClassPlanService.deleteClassActivity(id);
        return JSONResult.getInstance();
    }

    /**
    * @Date 2023/1/3 15:25
    * @MethodDescription 查询慕课安排
    * @Param 1. 班级id
    */
    @GetMapping("/mooc")
    public JSONResult selectMooc(Integer classId,Boolean isDate){

        return JSONResult.getInstance(iClassPlanService.selectMooc(classId,isDate));
    }

    /**
     * @Date 2023/3/14 15:37
     * @MethodDescription 新增慕课安排
    */
    @PostMapping("/public_mooc")
    public JSONResult insertMooc(@RequestBody ClassMooc classMooc){

        iClassPlanService.insertMooc(classMooc);
        return JSONResult.getInstance();
    }
    
    /**
     * @Date 2023/3/14 15:42
     * @MethodDescription 删除慕课安排
    */
    @PostMapping("/delete_mooc")
    public JSONResult deleteMooc(Integer id){

        iClassPlanService.deleteMooc(id);

        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/14 15:46
     * @MethodDescription 修改慕课信息
    */
    @PostMapping("/update_mooc")
    public JSONResult updateMooc(@RequestBody ClassMooc classMooc){

        iClassPlanService.updateMooc(classMooc);
        return JSONResult.getInstance();
    }



    /**
    * @Date 2023/1/3 17:13
    * @MethodDescription 查询周测安排
    * @Param 1. 班级id
    */
    @GetMapping("/week_test")
    public JSONResult selectWeekTest(Integer classId,Boolean isDate){

        return JSONResult.getInstance(iClassPlanService.selectWeekTest(classId,isDate));
    }
    
    /**
     * @Date 2023/3/14 15:51
     * @MethodDescription 新增周测安排
    */
    @PostMapping("/publish_weektest")
    public JSONResult insertWeekTest(@RequestBody ClassWeekTest classWeekTest){

        iClassPlanService.insertWeekTest(classWeekTest);
        return JSONResult.getInstance();
    }
    
    /**
     * @Date 2023/3/14 15:53
     * @MethodDescription 删除周测安排
    */
    @PostMapping("/delete_weektest")
    public JSONResult deleteWeekTest(Integer id){
        iClassPlanService.deleteWeekTest(id);
        return JSONResult.getInstance();
    }
    
    /**
     * @Date 2023/3/14 15:55
     * @MethodDescription 修改周测安排
    */
    @PostMapping("/update_weektest")
    public JSONResult updateWeekTest(@RequestBody ClassWeekTest classWeekTest){

        iClassPlanService.updateWeekTest(classWeekTest);

        return JSONResult.getInstance();
    }
    
    /**
    * @Date 2023/1/3 19:45
    * @MethodDescription 查询周末作业安排
    * @Param 1. 班级id 2.时间
    */
    @GetMapping("/week_work")
    public JSONResult selectWeekWork(Integer classId,Boolean isDate){

        return JSONResult.getInstance(iClassPlanService.selectWeekWork(classId,isDate));
    }
    
    /**
     * @Date 2023/3/14 15:57
     * @MethodDescription 新增周末作业安排
    */
    @PostMapping("/publish_homework")
    public JSONResult insertWeekWork(@RequestBody ClassWeekWork classWeekWork){

        iClassPlanService.insertWeekWork(classWeekWork);

        return JSONResult.getInstance();
    }
    
    /**
     * @Date 2023/3/14 15:59
     * @MethodDescription 删除周末作业安排
    */
    @PostMapping("/delete_homework")
    public JSONResult deleteWeekWork(Integer id){

        iClassPlanService.deleteWeekWork(id);

        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/14 16:01
     * @MethodDescription 修改周末作业
    */
    @PostMapping("/update_homework")
    public JSONResult updateWeekWork(@RequestBody ClassWeekWork classWeekWork){
        iClassPlanService.updateWeekWork(classWeekWork);
        return JSONResult.getInstance();
    }

    /**
    * @Date 2023/1/18 22:54
    * @MethodDescription 查询指定成绩
    * @Param 1.账号 2.密码
    */
    @GetMapping("/query/performance")
    public JSONResult queryPerformance(HttpServletRequest request){

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        EducationalSystemUser educationalSystemUser = iClassPlanService.selectPerformanceMsg(user.getId());

        List<StudentPerformance> list = iClassPlanService.queryPerformance(educationalSystemUser.getUserCode(), educationalSystemUser.getUserPwd());

        return JSONResult.getInstance(list);
    }
    

    /**
     * @Date 2023/3/17 1:04
     * @MethodDescription 绑定教务处
    */
    @PostMapping("/bind/edu_system")
    public JSONResult bindEduSystem(HttpServletRequest request,
                                    @RequestParam("userCode") String userCode,
                                    @RequestParam("userPwd") String password){

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        iClassPlanService.bindEduSystem(user.getId(),userCode,password);

        return JSONResult.getInstance();
    }


    /**
     * @Date 2023/3/17 1:04
     * @MethodDescription 查询用户是否绑定教务处
     */
    @GetMapping("/is_bind_edu")
    public JSONResult isBindEdu(HttpServletRequest request){

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        EducationalSystemUser educationalSystemUser = iClassPlanService.selectPerformanceMsg(user.getId());

        if(educationalSystemUser==null){
            throw new GlobalException(ErrorCode.NONE_ALREADY_BIND);
        }

        return JSONResult.getInstance();
    }
    
    /**
     * @Date 2023/3/19 21:54
     * @MethodDescription 教务处绑定信息
    */
    @PostMapping("/update_bind/edu_system")
    public JSONResult updateBindEduSystem(HttpServletRequest request,@RequestParam("userCode") String userCode,
                                          @RequestParam("updatePwd") String updatePwd,
                                          @RequestParam("updateCode") String updateCode){

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        iClassPlanService.updateBindEduSystem(user.getId(),userCode,updateCode,updatePwd);

        return JSONResult.getInstance();
    }


}
