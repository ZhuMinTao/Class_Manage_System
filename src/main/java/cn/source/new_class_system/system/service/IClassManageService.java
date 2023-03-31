package cn.source.new_class_system.system.service;

import cn.source.new_class_system.the_class.entity.ClassSystem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IClassManageService extends IService<ClassSystem> {
    /** @Description 获取所有班级信息 **/
    List<ClassSystem> getAllClassMessage(Boolean isAll,Integer userId, Integer pageSize, Integer pageNum);

    /** @Description 删除指定班级 **/
    void deletePointClass(Integer classId);

    /** @Description 新增班级 **/
    void insertClassContent(ClassSystem classSystem);

    /** @Description 删除班级花名册 **/
    void deleteRoster(Integer classId);
}
