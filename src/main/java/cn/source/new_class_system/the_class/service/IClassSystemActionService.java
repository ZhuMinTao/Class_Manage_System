package cn.source.new_class_system.the_class.service;

import cn.source.new_class_system.the_class.entity.ClassCourseTable;
import cn.source.new_class_system.the_class.entity.ClassRoster;
import cn.source.new_class_system.the_class.entity.UploadAddress;
import cn.source.new_class_system.the_class.entity.UploadTask;
import cn.source.new_class_system.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IClassSystemActionService {

    /** @PropertyDescription 用于上传班级花名册 **/
    void uploadClassRoster(MultipartFile file, Integer classId) throws IOException;

    /** @PropertyDescription 通过班级查询班级花名册 **/
    List<ClassRoster> selectByClass(Integer id);

    /** @PropertyDescription 查询发布的任务列表 **/
    List<UploadTask> selectUploadTaskList(Integer classId,Integer userId,Boolean isDate);

    /** @PropertyDescription 上传任务对应文件地址 **/
    void uploadFillUrl(Integer taskId, String fileUrl, User user);

    /** @PropertyDescription 通过任务id查询上传任务文件列表 **/
    List<UploadAddress> passTaskIdSelectUploadAddress(Integer taskId);

    /** @PropertyDescription 通过某个任务id查询任务 **/
    UploadTask passTaskIdSelectTask(Integer taskId);

    /** @Description 导出花名册 **/
    void exportRoster(Integer classId, HttpServletResponse response) throws IOException;

    /** @Description 通过任务id查询任务 **/
    UploadTask selectUploadTaskById(Integer taskId,User user);

    /** @Description 查询班级课表 **/
    List<ClassCourseTable> selectClassCourseTable(Integer userId) throws IOException;

    /** @Description 查询用户绑定教务系统和邮箱信息 **/
    Map<String,String> selectUserEduAndEmailBindMessage(Integer id);

    /** @Description 新增上传任务 **/
    void insertUploadTask(UploadTask uploadTask);

    /** @Description 删除上传任务 **/
    void deleteUploadTask(Integer id);

    /** @Description 修改上传任务 **/
    void updateUploadTask(UploadTask uploadTask);

    /** @Description 查询未完成名单 **/
    List<User> selectUnfinishedRoster(Integer taskId,Integer classId);
}
