package cn.source.new_class_system.the_class.mapper;

import cn.source.new_class_system.the_class.entity.UploadAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadAddressMapper extends BaseMapper<UploadAddress> {
    List<UploadAddress> passTaskIdSelectUploadAddress(@Param("taskId") Integer taskId);
}
