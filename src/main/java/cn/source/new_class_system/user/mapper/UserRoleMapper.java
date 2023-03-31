package cn.source.new_class_system.user.mapper;

import cn.source.new_class_system.user.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
