package cn.source.new_class_system.the_class.service;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.query.PagingQuery;
import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.base.result.PagingParam;
import cn.source.new_class_system.the_class.entity.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface IClassPlanService {
    /** @PropertyDescription 查询卫生安排 **/
    List<ClassHygiene> selectClassHygiene(Integer classId, String date,String afterDate);

    /** @PropertyDescription 查询课六安排 **/
    List<ClassCourseSix> selectCourseSixMessage(Integer classId, String date,String afterDate);

    /** @PropertyDescription 查询班级活动安排 **/
    Page<ClassActivity> selectActivity(Integer classId, Boolean date, Integer pageNum);

    /** @PropertyDescription 查询慕课安排 **/
    List<ClassMooc> selectMooc(Integer classId,Boolean isDate);

    /** @PropertyDescription 查询周测安排 **/
    List<ClassWeekTest> selectWeekTest(Integer classId,Boolean isDate);

    /** @PropertyDescription 查询周末作业安排 **/
    List<ClassWeekWork> selectWeekWork(Integer classId, Boolean date);

    /** @PropertyDescription 获取其教务处所以成绩 **/
    List<StudentPerformance> queryPerformance(String userCode, String password);

    /** @Description 验证教务处账号密码是否合法 **/
    Boolean verityEduLegal(String userCode,String password);

    /** @Description 新增卫生 **/
    void insertClassHygiene(ClassHygiene classHygiene);

    /** @Description 修改卫生安排 **/
    void updateClassHygiene(ClassHygiene classHygiene);

    /** @Description 删除某项卫生安排 **/
    void deleteClassHygiene(Integer Id);

    /** @Description 新增课六信息 **/
    void insertCourseSixMessage(ClassCourseSix classCourseSix);

    /** @Description 修改课六信息 **/
    void updateCourseSixMessage(ClassCourseSix classCourseSix);

    /** @Description 删除课六信息 **/
    void deleteClassSix(Integer id);

    /** @Description 新增班级活动 **/
    void insertClassActivity(ClassActivity classActivity);

    /** @Description 修改班级活动 **/
    void updateClassActivity(ClassActivity classActivity);

    /** @Description 删除班级活动 **/
    void deleteClassActivity(Integer id);

    /** @Description 新增慕课 **/
    void insertMooc(ClassMooc classMooc);

    /** @Description 删除慕课 **/
    void deleteMooc(Integer id);

    /** @Description 修改慕课 **/
    void updateMooc(ClassMooc classMooc);

    /** @Description 新增周测安排 **/
    void insertWeekTest(ClassWeekTest classWeekTest);

    /** @Description 删除周测安排 **/
    void deleteWeekTest(Integer id);

    /** @Description 修改周测安排 **/
    void updateWeekTest(ClassWeekTest classWeekTest);

    /** @Description 新增周末作业 **/
    void insertWeekWork(ClassWeekWork classWeekWork);

    /** @Description 删除周末作业 **/
    void deleteWeekWork(Integer id);

    /** @Description 修改周末作业 **/
    void updateWeekWork(ClassWeekWork classWeekWork);

    /** @Description 查询bind的教务处用户信息 **/
    EducationalSystemUser selectPerformanceMsg(Integer userId);

    /** @Description 绑定教务处账号密码 **/
    void bindEduSystem(Integer id, String userCode, String password) throws GlobalException;

    /** @Description 修改用户教务处账号和密码 **/
    void updateBindEduSystem(Integer userId,String userCode, String updateCode, String updatePwd);
}
