package cn.source.new_class_system.the_class.mapper;

import cn.source.new_class_system.the_class.entity.ClassCommittees;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassCommitteesMapper extends BaseMapper<ClassCommittees> {
    List<ClassCommittees> selectCommittee(@Param("classId") Integer classId);
}
