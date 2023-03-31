package cn.source.new_class_system.the_class.service.imple;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.utils.Base64Utils;
import cn.source.new_class_system.base.utils.ExcelPoiUtils;
import cn.source.new_class_system.base.utils.Session;
import cn.source.new_class_system.the_class.entity.*;
import cn.source.new_class_system.the_class.mapper.*;
import cn.source.new_class_system.the_class.service.IClassSystemActionService;
import cn.source.new_class_system.user.entity.User;
import cn.source.new_class_system.user.mapper.UserOperationMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClassSystemActionServiceImpl implements IClassSystemActionService {


    @Autowired
    private ClassRosterMapper classRosterMapper;

    @Autowired
    private ClassSystemMapper classSystemMapper;

    @Autowired
    private UploadTaskMapper uploadTaskMapper;


    @Autowired
    private UploadAddressMapper uploadAddressMapper;

    @Autowired
    private UserOperationMapper userOperationMapper;

    @Autowired
    private EducationalSystemUserMapper educationalSystemUserMapper;



    @Override
    public void uploadClassRoster(MultipartFile file, Integer classId) throws IOException {
        ExcelImportResult<ClassRoster> classRosterExcelImportResult =
                ExcelPoiUtils.importExcelMore(file.getInputStream(),
                        0, 1,
                        ClassRoster.class);
        List<ClassRoster> list = classRosterExcelImportResult.getList();
        try {
            list.forEach(e->{
                e.setClassId(classId);
                classRosterMapper.insert(e);
                System.out.println(e);
            });
        }catch (DuplicateKeyException e){
            throw new GlobalException(ErrorCode.REPETITION_VALUE);
        }

    }

    @Override
    public List<ClassRoster> selectByClass(Integer id) {

        log.debug("班级id值:{}",id);
        if(id==null||!classSystemMapper
                .exists(Wrappers.lambdaQuery(ClassSystem.class).
                        eq(ClassSystem::getId,id))){
            throw new GlobalException(ErrorCode.PARAMETER_ERROR);
        }

        return classRosterMapper.selectList(Wrappers.lambdaQuery(ClassRoster.class).eq(ClassRoster::getClassId, id));

    }

    @Override
    public List<UploadTask> selectUploadTaskList(Integer classId,Integer userId,Boolean isDate) {

        if(isDate==null)isDate = false;
        List<UploadTask> uploadTasks = null;

        if(isDate){
            //查询所以上传任务列表
            uploadTasks = uploadTaskMapper
                    .selectList(Wrappers
                            .lambdaQuery(UploadTask.class)
                            .eq(UploadTask::getClassId, classId));
        }else{
            //查询所以上传任务列表
            uploadTasks = uploadTaskMapper
                    .selectList(Wrappers
                            .lambdaQuery(UploadTask.class)
                            .eq(UploadTask::getClassId, classId)
                            .ge(UploadTask::getFinishDate,SimpleDateFormat.getDateTimeInstance().format(new Date())));
        }
        //进行流处理，判断此用户那些任务上传了哪些任务没有上传
        uploadTasks.stream().forEach(e->{
            //判断
            boolean exists = uploadAddressMapper.exists(Wrappers.lambdaQuery(UploadAddress.class)
                    .eq(UploadAddress::getUserId, userId).eq(UploadAddress::getTaskId,e.getId()));
            if(exists){
                e.setUserCompleteStatus(1);
            }else{
                e.setUserCompleteStatus(0);
            }
        });
        return uploadTasks;
    }

    @Override
    public void uploadFillUrl(Integer taskId, String fileUrl, User user) {

        boolean taskExists = uploadTaskMapper.exists(Wrappers.lambdaQuery(UploadTask.class).eq(UploadTask::getId, taskId));

        if(!taskExists){
            throw new GlobalException(ErrorCode.TASK_NONE_EXISTS);
        }

        boolean empty = StringUtils.isEmpty(fileUrl);
        if(empty){
            throw new GlobalException(ErrorCode.NOT_CAN_IS_NULL);
        }

        UploadAddress uploadAddress = new UploadAddress();

        uploadAddress.setUploadDate(SimpleDateFormat.getDateTimeInstance().format(new Date()));

        uploadAddress.setFileUrl(fileUrl);

        uploadAddress.setUserId(user.getId());

        uploadAddress.setTaskId(taskId);

        String[] split = fileUrl.split("\\.");

        if(split.length>1)
            uploadAddress.setSuffix(split[split.length-1]);

        //判断用户是否已经上传了这个任务
        List<UploadAddress> uploadAddresses = uploadAddressMapper.selectList(Wrappers.lambdaQuery(UploadAddress.class)
                .eq(UploadAddress::getTaskId, taskId)
                .eq(UploadAddress::getUserId, user.getId()));

        if(uploadAddresses.size()>=1){
            throw new GlobalException(ErrorCode.ALREADY_SUBMIT);
        }

        //上传任务
        uploadAddressMapper.insert(uploadAddress);

        /** @Description 用于判断上传任务是否完成 **/
        //查询该任务上传的数量
        Long taskUploadCount = uploadAddressMapper.selectCount(Wrappers.lambdaQuery(UploadAddress.class)
                .eq(UploadAddress::getTaskId, taskId));
        //查询该任务班级人数: 1.通过任务查询班级id 2.通过班级id查询用户数量
        UploadTask uploadTask = uploadTaskMapper.selectOne(Wrappers.lambdaQuery(UploadTask.class)
                .eq(UploadTask::getId, taskId));

        Long classStudentCount = userOperationMapper.selectCount(Wrappers.lambdaQuery(User.class)
                .eq(User::getClassId, uploadTask.getClassId()));

        if(taskUploadCount==classStudentCount){
            uploadTask.setCompleteStatus(1);
            uploadTaskMapper.updateById(uploadTask);
        }else{
            uploadTask.setCompleteStatus(0);
            uploadTaskMapper.updateById(uploadTask);
        }


    }

    @Override
    public List<UploadAddress> passTaskIdSelectUploadAddress(Integer taskId) {

        return uploadAddressMapper.passTaskIdSelectUploadAddress(taskId);
    }

    @Override
    public UploadTask passTaskIdSelectTask(Integer taskId) {

        UploadTask uploadTask = uploadTaskMapper.selectOne(Wrappers.lambdaQuery(UploadTask.class)
                .eq(UploadTask::getId, taskId));

        String formatDate = null;
        try {
            Date date= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(uploadTask.getPublishDate());

            formatDate = DateFormat.getDateInstance().format(date);

        } catch (ParseException e) {
            throw new GlobalException(ErrorCode.INSIDE_THE_SERVER_ERROR);
        }

        uploadTask.setPublishDate(formatDate);

        return uploadTask;
    }

    @Override
    public void exportRoster(Integer classId, HttpServletResponse response) throws IOException {

        List<ClassRoster> classRosters = classRosterMapper.selectList(Wrappers.lambdaQuery(ClassRoster.class).eq(ClassRoster::getClassId, classId));

        ExcelPoiUtils.exportExcel(classRosters,null,"花名册"
                ,ClassRoster.class,"class_rosters",response);
    }

    @Override
    public UploadTask selectUploadTaskById(Integer taskId,User user) {
        UploadTask uploadTask = uploadTaskMapper.selectOne(Wrappers.lambdaQuery(UploadTask.class).eq(UploadTask::getId, taskId));

        boolean exists = uploadAddressMapper.exists(Wrappers.lambdaQuery(UploadAddress.class)
                .eq(UploadAddress::getUserId, user.getId()).eq(UploadAddress::getTaskId,taskId));
        if(exists){
            uploadTask.setUserCompleteStatus(1);
        }else{
            uploadTask.setUserCompleteStatus(0);
        }
        return uploadTask;
    }

    @Override
    public List<ClassCourseTable> selectClassCourseTable(Integer userId) throws IOException {

        EducationalSystemUser educationalSystemUser = educationalSystemUserMapper.selectOne(Wrappers.lambdaQuery(EducationalSystemUser.class)
                .eq(EducationalSystemUser::getUserId, userId));

        if(educationalSystemUser==null){
            throw new GlobalException(ErrorCode.NONE_ALREADY_BIND);
        }

        //执行登录
        Session session =submitEduSystem(educationalSystemUser.getUserCode(),
                            educationalSystemUser.getUserPwd());

        //获取课表网页
        String courseTableHtml = getCourseTableHtml(session);


        //解析网页数据获取课表数据
        return jsoupClassCourseTable(courseTableHtml);
    }

    @Override
    public Map<String,String> selectUserEduAndEmailBindMessage(Integer id) {

        User user = userOperationMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getId, id));

        EducationalSystemUser educationalSystemUser = educationalSystemUserMapper.selectOne(Wrappers.lambdaQuery(EducationalSystemUser.class).eq(EducationalSystemUser::getUserId, id));

        Map<String,String> bindMessage = new HashMap<>();
        if(user!=null){
            bindMessage.put("emailBind",user.getEmail()==null?"":user.getEmail());
        }else{
            bindMessage.put("emailBind","");
        }
        if(educationalSystemUser!=null){
            bindMessage.put("eduAccount",educationalSystemUser.getUserCode());
        }else{
            bindMessage.put("eduAccount","");
        }

        return bindMessage;

    }

    @Override
    public void insertUploadTask(UploadTask uploadTask) {

        uploadTaskMapper.insert(uploadTask);
    }

    @Override
    public void deleteUploadTask(Integer id) {
        uploadAddressMapper.delete(Wrappers.lambdaQuery(UploadAddress.class).eq(UploadAddress::getTaskId,id));

        uploadTaskMapper.deleteById(id);
    }

    @Override
    public void updateUploadTask(UploadTask uploadTask) {

        uploadTaskMapper.updateById(uploadTask);

    }

    @Override
    public List<User> selectUnfinishedRoster(Integer taskId, Integer classId) {
        List<UploadAddress> uploadAddresses = uploadAddressMapper.selectList(Wrappers.lambdaQuery(UploadAddress.class).eq(UploadAddress::getTaskId, taskId));

        //查询所有完成的名单
        List<Integer> finishRoster = uploadAddresses.stream().map(e -> {
            return e.getUserId();
        }).collect(Collectors.toList());

        //查询该班级所有成员
        List<User> users = userOperationMapper.selectList(Wrappers.lambdaQuery(User.class).eq(User::getClassId, classId));
        List<User> resultRoster = users.stream().filter(e -> {
            return !finishRoster.contains(e.getId());
        }).collect(Collectors.toList());

        return resultRoster;
    }


    /**
     * @Date 2023/3/17 16:11
     * @MethodDescription 解析课程表数据并返回
    */
    public List<ClassCourseTable> jsoupClassCourseTable(String html){

        Document courseTableJsoup = Jsoup.parse(html);

        //获取课程信息
        List<ClassCourseTable> collect = courseTableJsoup.select("tbody tr").stream().map(element -> {
            ClassCourseTable classCourseTable = new ClassCourseTable();

            List<Element> td = element.select("td").stream().collect(Collectors.toList());

            classCourseTable.setWhichSection(td.get(0).text());
            td.remove(0);
            List<String> title = td.stream().map(element1 -> {
                return element1.select("p").attr("title");
            }).collect(Collectors.toList());

            classCourseTable.setCourseList(title);

            return classCourseTable;
        }).collect(Collectors.toList());

        return collect;
    }

    /**
     * @Date 2023/3/17 15:56
     * @MethodDescription 获取课表网页
    */
    public String getCourseTableHtml(Session session) throws IOException {
        Map<String, String> performanceDate = new HashMap<>();
        performanceDate.put("rq",new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
        //获取课表信息
        Map<String, String>  classTable = session.postFormUrlEncoded("http://hngczy.cn:9001/jsxsd/framework/main_index_loadkb.jsp", performanceDate);

        return classTable.get("text");
    }


    /**
     * @Date 2023/3/17 15:48
     * @MethodDescription 执行教务处登录操作
    */
    public Session submitEduSystem(String userCode,String userPwd) throws IOException {

        Session instance = new Session();
        //登录参数
        Map<String, String> loginDate = new HashMap<>();
        loginDate.put("userAccount", userCode);
        loginDate.put("userPassword", "");
        loginDate.put("encoded", Base64Utils.base64Encode(userCode) +"%%%"+Base64Utils.base64Encode(userPwd));
        loginDate.put("pwdstr1", "");
        loginDate.put("pwdstr2", "");

        instance.postFormUrlEncoded("http://hngczy.cn:9001/jsxsd/xk/LoginToXk", loginDate);

        return instance;
    }


}
