package cn.source.new_class_system.the_class.web.controller;

import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.base.utils.Session;
import cn.source.new_class_system.the_class.entity.UploadTask;
import cn.source.new_class_system.the_class.service.IClassSystemActionService;
import cn.source.new_class_system.user.entity.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @Date 2022/12/31 17:26
* @ClassTitle 系统功能
* @ClassDescription 用于处理系统功能
* @Author ZhuMT
*/
@Slf4j
@Controller
@RequestMapping("/function")
public class ClassSystemActionController {


    @Autowired
    private IClassSystemActionService iClassSystemActionService;


    @Autowired
    private RedisTemplate redisTemplate;

    /**
    * @Date 2022/12/31 17:27
    * @MethodDescription 上传班级花名册
    * @Param 1.文件 2.班级id
    */
    @ResponseBody
    @Transactional
    @PostMapping("/upload/class_roster")
    public JSONResult uploadClassRoster(@RequestParam("file")MultipartFile file,
                                        @RequestParam("classId") Integer classId) throws IOException {

        iClassSystemActionService.uploadClassRoster(file,classId);

        return JSONResult.getInstance();
    }

    /**
    * @Date 2023/1/2 11:23
    * @MethodDescription 导出花名册
    * @Param 1.班级id
    */
    @GetMapping("/download/class_roster")
    public JSONResult downLoadClassRoster( HttpServletResponse response, Integer classId) throws IOException {

        iClassSystemActionService.exportRoster(classId,response);

        return JSONResult.getInstance();

    }

    /**
    * @Date 2023/1/16 18:36
    * @MethodDescription 查询上传任务列表
    * @Param 1. 班级id
    */
    @ResponseBody
    @GetMapping("/upload_task_list")
    public JSONResult uploadTaskList(HttpServletRequest request,Integer classId,Boolean isDate){

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        List<UploadTask> uploadTasks = iClassSystemActionService.selectUploadTaskList(classId,user.getId(),isDate);

        return JSONResult.getInstance(uploadTasks);
    }

    /**
     * @Date 2023/1/16 18:36
     * @MethodDescription 通过任务id查询某个任务详情
     * @Param 1. 班级id
     */
    @ResponseBody
    @GetMapping("/upload_task_list/{taskId}")
    public JSONResult uploadTaskList(HttpServletRequest request, @PathVariable("taskId") Integer taskId){

        System.out.println(taskId);
        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        UploadTask uploadTasks = iClassSystemActionService.selectUploadTaskById(taskId,user);

        return JSONResult.getInstance(uploadTasks);
    }
    
    /**
    * @Date 2023/1/17 10:43
    * @MethodDescription 上传文件url地址并存储其对于信息
    * @Param 1.上传任务id
    */
    @ResponseBody
    @GetMapping("/upload/file_url")
    public  JSONResult uploadFileUrl(HttpServletRequest request,Integer taskId, String fileUrl){


        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        iClassSystemActionService.uploadFillUrl(taskId,fileUrl,user);

        return JSONResult.getInstance();
    }
    
    /**
     * @Date 2023/3/16 23:33
     * @MethodDescription 查询教务处课表
    */
    @ResponseBody
    @GetMapping("/course_table")
    public JSONResult selectClassCourseTable(HttpServletRequest request) throws IOException {

        String token = request.getHeader("token");

        //获取用户的信息
        User user = (User)redisTemplate.opsForValue().get(token);

        return JSONResult.getInstance(iClassSystemActionService.selectClassCourseTable(user.getId()));
    }

    /**
     * @Date 2023/3/18 9:51
     * @MethodDescription 查看新闻
    */
    @ResponseBody
    @GetMapping("/news")
    public JSONResult selectApiNews() throws IOException {

        Session session = new Session();

        Map<String, String> map = session.get("http://c.m.163.com/nc/article/headline/T1348647853363/0-40.html");

        return JSONResult.getStringInstance(map.get("text"));
    }
    
    /**
     * @Date 2023/3/19 1:22
     * @MethodDescription 查询用户教务处和邮箱绑定信息
    */
    @ResponseBody
    @GetMapping("/bind_message")
    public JSONResult selectUserEduAndEmailBindMessage(HttpServletRequest request){

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        return JSONResult.getInstance(iClassSystemActionService.selectUserEduAndEmailBindMessage(user.getId()));
    }

    /**
     * @Date 2023/3/22 8:49
     * @MethodDescription 新增上传任务
    */
    @ResponseBody
    @PostMapping("/publish_upload_task")
    public JSONResult insertUploadTask( @RequestBody UploadTask uploadTask){

        String format = SimpleDateFormat.getDateTimeInstance().format(new Date());

        uploadTask.setPublishDate(format);

        iClassSystemActionService.insertUploadTask(uploadTask);

        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/22 8:50
     * @MethodDescription 删除上传任务
    */
    @ResponseBody
    @PostMapping("/delete_upload_task")
    public JSONResult deleteUploadTask(@RequestParam Integer id){

        iClassSystemActionService.deleteUploadTask(id);

        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/22 8:51
     * @MethodDescription 修改上传任务
    */
    @ResponseBody
    @PostMapping("/update_upload_task")
    public JSONResult updateUploadTask(@RequestBody UploadTask uploadTask){

        iClassSystemActionService.updateUploadTask(uploadTask);

        return JSONResult.getInstance();
    }

    /**
     * @Date 2023/3/29 14:16
     * @MethodDescription 查询未完成任务的名单
    */
    @ResponseBody
    @GetMapping("/task_unfinished_roster")
    public JSONResult selectUnfinishedRoster(HttpServletRequest request,Integer taskId){

        User user = (User)redisTemplate.opsForValue().get(request.getHeader("token"));

        return JSONResult.getInstance(iClassSystemActionService.selectUnfinishedRoster(taskId,user.getClassId()));
    }
 }
