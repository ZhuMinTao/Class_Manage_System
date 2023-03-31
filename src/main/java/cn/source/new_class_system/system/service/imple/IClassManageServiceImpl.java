package cn.source.new_class_system.system.service.imple;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.system.service.IClassManageService;
import cn.source.new_class_system.the_class.entity.ClassRoster;
import cn.source.new_class_system.the_class.entity.ClassSystem;
import cn.source.new_class_system.the_class.entity.ClassSystemDetail;
import cn.source.new_class_system.the_class.mapper.ClassRosterMapper;
import cn.source.new_class_system.the_class.mapper.ClassSystemDetailMapper;
import cn.source.new_class_system.the_class.mapper.ClassSystemMapper;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.mapper.UserOperationMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IClassManageServiceImpl extends ServiceImpl<ClassSystemMapper, ClassSystem> implements IClassManageService {

    @Autowired
    private ClassSystemMapper classSystemMapper;

    @Autowired
    private ClassSystemDetailMapper classSystemDetailMapper;

    @Autowired
    private UserOperationMapper operationMapper;

    @Autowired
    private ClassRosterMapper rosterMapper;

    @Override
    public List<ClassSystem> getAllClassMessage(Boolean isAll, Integer userId, Integer pageSize,Integer pageNum) {
        LambdaQueryWrapper<ClassSystem> eq = null;
        if(!isAll){
                    eq= Wrappers.lambdaQuery(ClassSystem.class).eq(ClassSystem::getUserId, userId);
        }
        if(pageSize!=null&&pageNum!=null){
            Page<ClassSystem> page = new Page(pageNum,pageSize);
             return classSystemMapper.selectPage(page,eq).getRecords().stream().map(e->{
                 User user = operationMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getId, e.getUserId()));
                 e.setInstructor(user);
                 return e;
             }).collect(Collectors.toList());
        }

        return classSystemMapper.selectList(eq).stream().map(e->{
            User user = operationMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getId, e.getUserId()));
            e.setInstructor(user);
            return e;
        }).collect(Collectors.toList());
    }

    @Override
    public void deletePointClass(@RequestParam Integer classId) {
        System.out.println(classId);
        ClassSystem classSystem = classSystemMapper.selectById(classId);
        System.out.println(classSystem);
        //删除班级信息和班级详情信息
        classSystemMapper.deleteById(classId);
        classSystemDetailMapper.deleteById(classSystem.getClassDetailId());

    }

    @Override
    public void insertClassContent(ClassSystem classSystem) {
        if(classSystemMapper.exists(Wrappers.lambdaQuery(ClassSystem.class).eq(ClassSystem::getClassCode, classSystem.getClassCode()))){
            throw new GlobalException(ErrorCode.REPETITION_VALUE);
        }

        ClassSystemDetail classSystemDetail = new ClassSystemDetail();

        classSystemDetailMapper.insert(classSystemDetail);

        classSystem.setClassDetailId(classSystemDetail.getId());

        classSystemMapper.insert(classSystem);
    }

    @Override
    public void deleteRoster(Integer classId) {

        rosterMapper.delete(Wrappers.lambdaQuery(ClassRoster.class).eq(ClassRoster::getClassId,classId));

    }
}
