package cn.source.new_class_system.the_class.mapper;

import cn.source.new_class_system.the_class.entity.ClassActivity;
import cn.source.new_class_system.the_class.entity.ClassCourseSix;
import cn.source.new_class_system.the_class.entity.ClassHygiene;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassPlanMapper extends BaseMapper<ClassActivity> {
    List<ClassHygiene> selectClassHygiene(@Param("classId") Integer classId,
                                          @Param("date") String date,@Param("afterDate") String afterDate);


    List<ClassCourseSix> selectCourseSix(@Param("classId") Integer classId,
                                         @Param("date") String date,@Param("afterDate") String afterDate);

}
