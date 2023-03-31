package cn.source.new_class_system.the_class.service;

import cn.source.new_class_system.Application;
import cn.source.new_class_system.base.utils.OSSUtils;
import cn.source.new_class_system.the_class.entity.ClassCourseTable;
import cn.source.new_class_system.the_class.entity.UploadAddress;
import cn.source.new_class_system.the_class.entity.UploadTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class ClassSystemActionServiceTest {

    @Autowired
    private IClassSystemActionService iClassSystemActionService;



    /**
    * @Date 2023/1/16 20:20
    * @MethodDescription 查询指定班级的上传任务
    * @Param 1.班级id
    */
    @Test
    public void selectUploadTaskTest(){

        List<UploadTask> uploadTasks = iClassSystemActionService.selectUploadTaskList(1,6,false);
        System.out.println(uploadTasks);
    }

    /**
    * @Date 2023/1/17 15:52
    * @MethodDescription 查询指定任务的上传列表
    * @Param 1.taskId
    */
    @Test
    public void selectTaskUploadList(){

        List<UploadAddress> uploadAddresses = iClassSystemActionService.passTaskIdSelectUploadAddress(1);

    }

    /**
     * @Date 2023/3/17 14:47
     * @MethodDescription 查询课表
    */
    @Test
    public void selectClassCourseTest() throws Exception {
        OSSUtils ossUtils = new OSSUtils();

        String str = "https://class-system.oss-cn-beijing.aliyuncs.com/1678975657125.png";
        String substring = str.substring(str.lastIndexOf('/')+1);
        byte[] bytes = ossUtils.getObjectByte(substring);

        System.out.println(bytes);
    }

}
