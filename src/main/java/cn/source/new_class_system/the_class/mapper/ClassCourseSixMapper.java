package cn.source.new_class_system.the_class.mapper;

import cn.source.new_class_system.the_class.entity.ClassCourseSix;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassCourseSixMapper extends BaseMapper<ClassCourseSix> {
    void insertCourseSixMessage(ClassCourseSix classCourseSix);

    void updateCourseSixMessage(ClassCourseSix classCourseSix);
}
