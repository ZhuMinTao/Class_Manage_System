package cn.source.new_class_system.user.mapper;

import cn.source.new_class_system.user.entity.PowerName;
import cn.source.new_class_system.user.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerNameMapper extends BaseMapper<PowerName> {
    List<PowerName> selectUserPowerList(@Param("id") Integer id);

    List<Role> selectPowerList();

    List<Role> selectUserRoleListByUserId(@Param("userId") Integer userId);

}
