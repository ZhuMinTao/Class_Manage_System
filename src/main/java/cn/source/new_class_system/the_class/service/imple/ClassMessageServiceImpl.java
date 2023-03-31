package cn.source.new_class_system.the_class.service.imple;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.the_class.entity.ClassCommittees;
import cn.source.new_class_system.the_class.entity.ClassRoster;
import cn.source.new_class_system.the_class.entity.ClassSystem;
import cn.source.new_class_system.the_class.entity.ClassSystemDetail;
import cn.source.new_class_system.the_class.mapper.ClassCommitteesMapper;
import cn.source.new_class_system.the_class.mapper.ClassRosterMapper;
import cn.source.new_class_system.the_class.mapper.ClassSystemDetailMapper;
import cn.source.new_class_system.the_class.mapper.ClassSystemMapper;
import cn.source.new_class_system.the_class.service.IClassMessageService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassMessageServiceImpl implements IClassMessageService {

    @Autowired
    private ClassSystemMapper classSystemMapper;

    @Autowired
    private ClassCommitteesMapper classCommitteesMapper;

    @Autowired
    private ClassRosterMapper classRosterMapper;

    @Autowired
    private ClassSystemDetailMapper classSystemDetailMapper;

    @Override
    public ClassSystem selectClassIntroduce(Integer classId) {

        return classSystemMapper.selectClassIntroduce(classId);
    }

    @Override
    public List<ClassCommittees> selectCommittee(Integer classId) {


        return classCommitteesMapper.selectCommittee(classId);
    }

    @Override
    public ClassSystem selectClassBasic(Integer classId) {
        return classSystemMapper.selectOne(Wrappers.lambdaQuery(ClassSystem.class).eq(ClassSystem::getId,classId));
    }

    @Override
    public List<ClassRoster> selectClassRoster(Integer classId) {
        return classRosterMapper.selectList(Wrappers.lambdaQuery(ClassRoster.class).eq(ClassRoster::getClassId,classId));
    }

    @Override
    public void updateClassSystemDetail(ClassSystemDetail classSystemDetail) {

        classSystemDetailMapper.updateById(classSystemDetail);

    }

    @Override
    public void deleteCommittees(Integer id) {

        classCommitteesMapper.deleteById(id);

    }

    @Override
    public void insertClassCommittees(ClassCommittees classCommittees) {
        boolean exists = classCommitteesMapper.exists(Wrappers.lambdaQuery(ClassCommittees.class).eq(ClassCommittees::getUserId, classCommittees.getUserId()));
        if(exists){
            throw new GlobalException(ErrorCode.REPETITION_VALUE);
        }
        classCommitteesMapper.insert(classCommittees);
    }


}
