package cn.source.new_class_system.the_class.service;

import cn.source.new_class_system.the_class.entity.ClassCommittees;
import cn.source.new_class_system.the_class.entity.ClassRoster;
import cn.source.new_class_system.the_class.entity.ClassSystem;
import cn.source.new_class_system.the_class.entity.ClassSystemDetail;

import java.util.List;

public interface IClassMessageService {

    /** @PropertyDescription 查询班级基本信息及荣誉**/
    ClassSystem selectClassIntroduce(Integer classId);

    /** @PropertyDescription 查询班委会成员 **/
    List<ClassCommittees> selectCommittee(Integer classId);

    /** @PropertyDescription 查询班级基本信息 **/
    ClassSystem selectClassBasic(Integer classId);

    /** @PropertyDescription 查询班级花名册 **/
    List<ClassRoster> selectClassRoster(Integer classId);

    /** @Description 修改班级信息 **/
    void updateClassSystemDetail(ClassSystemDetail classSystemDetail);

    /** @Description 删除班委信息 **/
    void deleteCommittees(Integer id);

    /** @Description 新增班委记录 **/
    void insertClassCommittees(ClassCommittees classCommittees);
}
