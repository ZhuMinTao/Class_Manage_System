package cn.source.new_class_system.user.mapper;

import cn.source.new_class_system.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOperationMapper extends BaseMapper<User> {

    User selectUserDetail(@Param("user") User user);
}
