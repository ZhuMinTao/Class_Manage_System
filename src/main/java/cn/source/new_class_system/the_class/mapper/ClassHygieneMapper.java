package cn.source.new_class_system.the_class.mapper;

import cn.source.new_class_system.the_class.entity.ClassHygiene;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassHygieneMapper extends BaseMapper<ClassHygiene> {

    void insertClassHygiene(ClassHygiene classHygiene);

    void updateClassHygiene(ClassHygiene classHygiene);
}
