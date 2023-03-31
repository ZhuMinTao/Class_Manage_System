package cn.source.new_class_system.the_class.mapper;

import cn.source.new_class_system.the_class.entity.ClassSystem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassSystemMapper extends BaseMapper<ClassSystem> {

    ClassSystem selectClassIntroduce(@Param("classId") Integer classId);
}
