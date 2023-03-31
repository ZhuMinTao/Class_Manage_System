package cn.source.new_class_system.the_class.mapper;

import cn.source.new_class_system.Application;
import cn.source.new_class_system.the_class.entity.ClassCourseSix;
import cn.source.new_class_system.the_class.entity.ClassHygiene;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
* @Date 2023/1/3 11:58
* @ClassTitle 测试班级安排功能
* @ClassDescription 具体测试班级安排查询语句规范是否正确
* @Author ZhuMT
*/
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class ClassPlanMapperTest {
    @Autowired
    private ClassPlanMapper classPlanMapper;
    /**
    * @Date 2023/1/3 11:59
    * @MethodDescription 查询班级卫生安排
    */
    @Test
    public void selectHygieneTest(){
    }

    /**
    * @Date 2023/1/3 14:27
    * @MethodDescription 查询班级课6安排
    */
    @Test
    public void selectCourseSixMessageTest(){
    }

    /**
    * @Date 2023/1/3 14:50
    * @MethodDescription 查询班级活动安排
    */
    @Test
    public void selectActivityTest(){
        System.out.println(SimpleDateFormat.getDateTimeInstance().format(new Date()));
    }

}
