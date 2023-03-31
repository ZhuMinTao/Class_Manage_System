package cn.source.new_class_system.the_class.mapper;

import cn.source.new_class_system.Application;
import cn.source.new_class_system.the_class.entity.ClassCommittees;
import cn.source.new_class_system.the_class.entity.ClassSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
* @Date 2023/1/3 20:45
* @ClassTitle 测试班级信息查询
* @ClassDescription 用于多表查询班级信息sql的正确性
* @Author ZhuMT
*/
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class ClassSystemMapperTest {

    @Autowired
    private ClassSystemMapper classSystemMapper;
    @Autowired
    private ClassCommitteesMapper classCommitteesMapper;

    /**
    * @Date 2023/1/3 20:47
    * @MethodDescription 查询班级详情介绍内容
    */
    @Test
    public void selectClassIntroduce(){
        ClassSystem classSystem = classSystemMapper.selectClassIntroduce(1);
        System.out.println(classSystem);
    }
    /**
    * @Date 2023/1/5 18:42
    * @MethodDescription 查询班委成员
    */
    @Test
    public void selectCommittee(){
        List<ClassCommittees> classCommittees = classCommitteesMapper.selectCommittee(1);
        System.out.println(classCommittees);
    }
}
