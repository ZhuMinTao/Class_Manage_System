package cn.source.new_class_system.the_class.service.imple;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.query.PagingQuery;
import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.base.result.PagingParam;
import cn.source.new_class_system.base.utils.Base64Utils;
import cn.source.new_class_system.base.utils.Session;
import cn.source.new_class_system.base.utils.StringUtils;
import cn.source.new_class_system.the_class.entity.*;
import cn.source.new_class_system.the_class.mapper.*;
import cn.source.new_class_system.the_class.service.IClassPlanService;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.mapper.UserOperationMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClassPlanServiceImpl implements IClassPlanService {

    @Autowired
    private ClassPlanMapper classPlanMapper;

    @Autowired
    private ClassMoocMapper classMoocMapper;

    @Autowired
    private ClassWeekTestMapper classWeekTestMapper;

    @Autowired
    private ClassWeekWorkMapper classWeekWorkMapper;

    @Autowired
    private EducationalSystemUserMapper educationalSystemUserMapper;

    @Autowired
    private StudentPerformanceMapper studentPerformanceMapper;

    @Autowired
    private UserOperationMapper userOperationMapper;

    @Autowired
    private ClassHygieneMapper classHygieneMapper;

    @Autowired ClassCourseSixMapper classCourseSixMapper;

    @Autowired ClassActivityMapper classActivityMapper;

    @Override
    public List<ClassHygiene> selectClassHygiene(Integer classId, String date,String afterDate) {

        return classPlanMapper.selectClassHygiene(classId,date,afterDate);
    }

    @Override
    public List<ClassCourseSix> selectCourseSixMessage(Integer classId, String date,String afterDate) {

        return classPlanMapper.selectCourseSix(classId,date,afterDate);
    }

    @Override
    public Page<ClassActivity> selectActivity(Integer classId, Boolean isDate, Integer pageNum) {

        if(isDate==null)isDate=false;

        Page<ClassActivity> page = null;

        if(pageNum!=null){
            page = new Page<>(pageNum,5);
        }

        Page<ClassActivity> classActivityPage = new Page<>();
        if(isDate){
            if(pageNum==null){
                List<ClassActivity> classActivities = classPlanMapper.selectList(Wrappers.lambdaQuery(ClassActivity.class)
                        .eq(ClassActivity::getClassId, classId));
                classActivityPage.setRecords(classActivities);

            }else{
                classActivityPage = classPlanMapper.selectPage(page, Wrappers.lambdaQuery(ClassActivity.class)
                        .eq(ClassActivity::getClassId, classId));
            }

        }else{

            if(pageNum==null){
                List<ClassActivity> classActivities = classPlanMapper.selectList(Wrappers.lambdaQuery(ClassActivity.class)
                        .eq(ClassActivity::getClassId, classId));
                classActivityPage.setRecords(classActivities);
            }else{
                classActivityPage = classPlanMapper.selectPage(page,Wrappers.lambdaQuery(ClassActivity.class)
                        .eq(ClassActivity::getClassId, classId)
                        .ge(ClassActivity::getActivityDate, DateFormat.getDateInstance().format(new Date())));
            }

        }

        //将策划人加入到集合中
        List<ClassActivity> collect = classActivityPage.getRecords().stream().map(e -> {
            User user = userOperationMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getId, e.getScheme()));
            e.setSchemeUser(user);
            return e;
        }).collect(Collectors.toList());

        classActivityPage.setRecords(collect);

        return classActivityPage;
    }

    @Override
    public List<ClassMooc> selectMooc(Integer classId,Boolean isDate) {
        if(isDate==null)isDate=false;

        if(isDate){
            return classMoocMapper.selectList(Wrappers.lambdaQuery(ClassMooc.class)
                    .eq(ClassMooc::getClassId,classId));
        }else{
            return classMoocMapper.selectList(Wrappers.lambdaQuery(ClassMooc.class)
                    .eq(ClassMooc::getClassId,classId)
                    .gt(ClassMooc::getExamineDate,SimpleDateFormat.getDateTimeInstance()
                            .format(new Date())));
        }




    }

    @Override
    public List<ClassWeekTest> selectWeekTest(Integer classId,Boolean isDate) {
        if(isDate==null)isDate=false;
        if(isDate){
            return classWeekTestMapper.selectList(Wrappers.lambdaQuery(ClassWeekTest.class)
                    .eq(ClassWeekTest::getClassId,classId).orderBy(true,true,ClassWeekTest::getTestTime));
        }else{
            return classWeekTestMapper.selectList(Wrappers.lambdaQuery(ClassWeekTest.class)
                    .eq(ClassWeekTest::getClassId,classId)
                    .orderBy(true,true,ClassWeekTest::getTestTime)
                    .gt(ClassWeekTest::getTestTime,SimpleDateFormat.getDateTimeInstance().format(new Date())));
        }

    }

    @Override
    public List<ClassWeekWork> selectWeekWork(Integer classId, Boolean isDate) {

        if(isDate==null)isDate=false;

        if(isDate) {
            return classWeekWorkMapper.selectList(Wrappers.lambdaQuery(ClassWeekWork.class)
                    .eq(ClassWeekWork::getClassId, classId));
        }else{
            return classWeekWorkMapper.selectList(Wrappers.lambdaQuery(ClassWeekWork.class)
                    .eq(ClassWeekWork::getClassId, classId)
                    .ge(ClassWeekWork::getWorkTime,SimpleDateFormat.getDateInstance().format(new Date())));
        }


    }

    @Override
    public List<StudentPerformance> queryPerformance(String userCode, String password) {

        Session instance = new Session();
        //登录参数
        Map<String, String> loginDate = new HashMap<>();
        loginDate.put("userAccount", userCode);
        loginDate.put("userPassword", "");
        loginDate.put("encoded", Base64Utils.base64Encode(userCode) +"%%%"+Base64Utils.base64Encode(password));
        loginDate.put("pwdstr1", "");
        loginDate.put("pwdstr2", "");
        //成绩参数
        Map<String, String> performanceDate = new HashMap<>();
        performanceDate.put("kksj","");
        performanceDate.put("kcxz","");
        performanceDate.put("kcmc","");
        performanceDate.put("xsfs","all");

        String text = null;
        try {
            //登录
            Map<String, String> loginResult = instance.postFormUrlEncoded("http://hngczy.cn:9001/jsxsd/xk/LoginToXk", loginDate);
            //获取成绩信息
             Map<String, String> myGrade = instance.postForm("http://hngczy.cn:9001/jsxsd/kscj/cjcx_list", performanceDate);

             text = myGrade.get("text");

            for(Map.Entry<String,String> entry:myGrade.entrySet()){
            }

        } catch (IOException e) {
            throw new GlobalException(ErrorCode.NETWORK_REQUEST_ERROR);
        }

        List<StudentPerformance> performanceList = null;


        Document parse = Jsoup.parse(text);

        //判断用户账号密码是否正确
        Boolean verityUser = parse.select("title").text().equals("学生个人考试成绩");


        if(!verityUser){
            throw new GlobalException(ErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }

        try {
            performanceList = new ArrayList<>();
            //解析参数
            List<Element> tbody_tr = parse.select("tbody tr").stream().collect(Collectors.toList());
            for(Element e : tbody_tr){
                List<Element> td = e.select("td").stream().collect(Collectors.toList());
                if(td.size()==0)continue;
                td.remove(4);
                td.remove(5);
                td.remove(8);
                td.remove(12);
                StudentPerformance studentPerformance = new StudentPerformance(null
                        , td.get(1).text()
                        , td.get(2).text()
                        , td.get(3).text()
                        , td.get(4).text()
                        , Double.parseDouble(td.get(5).text())
                        , Integer.parseInt(td.get(6).text())
                        , Double.parseDouble(td.get(7).text())
                        , td.get(8).text()
                        , td.get(9).text()
                        , td.get(10).text()
                        , td.get(11).text());

                performanceList.add(studentPerformance);
            }
        }catch (Exception e){
            return performanceList;
        }
        return performanceList;
    }

    @Override
    public Boolean verityEduLegal(String userCode, String password) {
        System.out.println(userCode+password);
        Session instance = new Session();
        //登录参数
        Map<String, String> loginDate = new HashMap<>();
        loginDate.put("userAccount", userCode);
        loginDate.put("userPassword", "");
        loginDate.put("encoded", Base64Utils.base64Encode(userCode) +"%%%"+Base64Utils.base64Encode(password));
        loginDate.put("pwdstr1", "");
        loginDate.put("pwdstr2", "");
        //成绩参数
        Map<String, String> performanceDate = new HashMap<>();
        performanceDate.put("kksj","");
        performanceDate.put("kcxz","");
        performanceDate.put("kcmc","");
        performanceDate.put("xsfs","all");


        String text = null;
        try {
            //登录
            Map<String, String> loginResult = instance.postFormUrlEncoded("http://hngczy.cn:9001/jsxsd/xk/LoginToXk", loginDate);
            //获取成绩信息
            Map<String, String> myGrade = instance.postForm("http://hngczy.cn:9001/jsxsd/kscj/cjcx_list", performanceDate);

            text = myGrade.get("text");

            for(Map.Entry<String,String> entry:myGrade.entrySet()){
            }

        } catch (IOException e) {
            throw new GlobalException(ErrorCode.NETWORK_REQUEST_ERROR);
        }

        EducationalSystemUser educationalSystemUser = null;

        List<StudentPerformance> performanceList = null;

        Document parse = Jsoup.parse(text);

        //判断用户账号密码是否正确
        Boolean verityUser = parse.select("title").text().equals("学生个人考试成绩");

       if(verityUser){
           return true;
       }else{
           return false;
       }
    }

    @Override
    public void insertClassHygiene(ClassHygiene classHygiene) {

        classHygieneMapper.insertClassHygiene(classHygiene);

    }

    @Override
    public void updateClassHygiene(ClassHygiene classHygiene) {
        classHygieneMapper.updateClassHygiene(classHygiene);
    }

    @Override
    public void deleteClassHygiene(Integer id) {
        classHygieneMapper.delete(Wrappers.lambdaQuery(ClassHygiene.class).eq(ClassHygiene::getId,id));
    }

    @Override
    public void insertCourseSixMessage(ClassCourseSix classCourseSix) {

        classCourseSixMapper.insertCourseSixMessage(classCourseSix);
    }

    @Override
    public void updateCourseSixMessage(ClassCourseSix classCourseSix) {
        classCourseSixMapper.updateCourseSixMessage(classCourseSix);
    }

    @Override
    public void deleteClassSix(Integer id) {
        classCourseSixMapper.delete(Wrappers.lambdaQuery(ClassCourseSix.class).eq(ClassCourseSix::getId,id));
    }

    @Override
    public void insertClassActivity(ClassActivity classActivity) {
        classActivityMapper.insert(classActivity);
    }

    @Override
    public void updateClassActivity(ClassActivity classActivity) {


        classActivity.setScheme(classActivity.getSchemeUser().getId());

        classActivityMapper.updateById(classActivity);
    }

    @Override
    public void deleteClassActivity(Integer id) {
        classActivityMapper.deleteById(id);
    }

    @Override
    public void insertMooc(ClassMooc classMooc) {
        classMooc.setPublishDate(SimpleDateFormat.getDateTimeInstance().format(new Date()));
        classMoocMapper.insert(classMooc);
    }

    @Override
    public void deleteMooc(Integer id) {
        classMoocMapper.deleteById(id);
    }

    @Override
    public void updateMooc(ClassMooc classMooc) {
        classMoocMapper.updateById(classMooc);
    }

    @Override
    public void insertWeekTest(ClassWeekTest classWeekTest) {
        classWeekTestMapper.insert(classWeekTest);
    }

    @Override
    public void deleteWeekTest(Integer id) {
        classWeekTestMapper.deleteById(id);
    }

    @Override
    public void updateWeekTest(ClassWeekTest classWeekTest) {
        classWeekTestMapper.updateById(classWeekTest);
    }

    @Override
    public void insertWeekWork(ClassWeekWork classWeekWork) {
        classWeekWorkMapper.insert(classWeekWork);
    }

    @Override
    public void deleteWeekWork(Integer id) {
        classWeekWorkMapper.deleteById(id);
    }

    @Override
    public void updateWeekWork(ClassWeekWork classWeekWork) {
        classWeekWorkMapper.updateById(classWeekWork);
    }

    @Override
    public EducationalSystemUser selectPerformanceMsg(Integer userId) {

        return educationalSystemUserMapper.selectOne(Wrappers.lambdaQuery(EducationalSystemUser.class).eq(EducationalSystemUser::getUserId,userId));
    }

    @Override
    public void bindEduSystem(Integer userId, String userCode, String password) throws GlobalException {

        Boolean aBoolean = verityEduLegal(userCode, password);
        //验证账号密码是否正确
        if(!aBoolean){
            throw new GlobalException(ErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }
        //判断该账号是已被绑定
        Long aLong = educationalSystemUserMapper.selectCount(Wrappers.lambdaQuery(EducationalSystemUser.class)
                .eq(EducationalSystemUser::getUserCode, userCode));

        if(aLong>0){
            throw new GlobalException(ErrorCode.ALREADY_BE_BIND);
        }

        Long aLong1 = educationalSystemUserMapper.selectCount(Wrappers.lambdaQuery(EducationalSystemUser.class)
                .eq(EducationalSystemUser::getUserId, userId));

        //判断是否已绑定账号
        if(aLong1>0) {
            throw new GlobalException(ErrorCode.ALREADY_BIND);
        }
        EducationalSystemUser educationalSystemUser = new EducationalSystemUser();
        educationalSystemUser.setUserId(userId);
        educationalSystemUser.setUserCode(userCode);
        educationalSystemUser.setUserPwd(password);

        educationalSystemUserMapper.insert(educationalSystemUser);
    }

    @Override
    public void updateBindEduSystem(Integer userId, String userCode, String updateCode, String updatePwd) {

        //验证更改账号和密码是否正确
        Boolean aBoolean = verityEduLegal(updateCode, updatePwd);
        System.out.println(updateCode);
        System.out.println(updatePwd);
        if(!aBoolean){
            throw new GlobalException(ErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }

        EducationalSystemUser educationalSystemUser = educationalSystemUserMapper.selectOne(Wrappers.lambdaQuery(EducationalSystemUser.class)
                .eq(EducationalSystemUser::getUserId, userId));

        if(userCode==educationalSystemUser.getUserCode()){
            throw new GlobalException(ErrorCode.PARAM_NO_MATE);
        }
        educationalSystemUser.setUserCode(updateCode);
        educationalSystemUser.setUserPwd(updatePwd);
        educationalSystemUserMapper.updateById(educationalSystemUser);

    }
}
