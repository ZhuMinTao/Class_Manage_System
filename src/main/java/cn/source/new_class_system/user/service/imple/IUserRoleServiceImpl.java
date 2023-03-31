package cn.source.new_class_system.user.service.imple;

import cn.source.new_class_system.user.entity.UserRole;
import cn.source.new_class_system.user.mapper.UserRoleMapper;
import cn.source.new_class_system.user.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
