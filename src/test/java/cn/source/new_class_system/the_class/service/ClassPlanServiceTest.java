package cn.source.new_class_system.the_class.service;

import cn.source.new_class_system.Application;
import cn.source.new_class_system.the_class.entity.ClassActivity;
import cn.source.new_class_system.the_class.entity.ClassMooc;
import cn.source.new_class_system.the_class.entity.ClassWeekTest;
import cn.source.new_class_system.the_class.entity.ClassWeekWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* @Date 2023/1/3 14:52
* @ClassTitle 测试班级功能service层
* @ClassDescription 主要用于发现service层活动是否符合要求
* @Author ZhuMT
*/
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class ClassPlanServiceTest {

    @Autowired
    private IClassPlanService iClassPlanService;


    /**
    * @Date 2023/1/3 14:53
    * @MethodDescription 查询班级活动
    */
    @Test
    public void selectActivityTest(){
        iClassPlanService.selectActivity(1,null,null);
    }

    /**
    * @Date 2023/1/3 16:13
    * @MethodDescription 查询慕课安排
    */
    @Test
    public void selectMoocTest(){
        List<ClassMooc> classMoocs = iClassPlanService.selectMooc(1,false);
        System.out.println(classMoocs.size());
    }

    /**
    * @Date 2023/1/3 17:26
    * @MethodDescription 查询周测安排
    */
    @Test
    public void selectWeekTestTest(){
    }

    /**
    * @Date 2023/1/3 19:51
    * @MethodDescription 查询周末作业安排
    */
    @Test
    public void selectWeekWorkTest(){
        List<ClassWeekWork> classWeekWorks = iClassPlanService.selectWeekWork(1, false);

        System.out.println(classWeekWorks);
    }
}
