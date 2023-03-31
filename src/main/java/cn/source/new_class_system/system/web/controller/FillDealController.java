package cn.source.new_class_system.system.web.controller;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.utils.StringUtils;
import cn.source.new_class_system.base.utils.ZipUtils;
import cn.source.new_class_system.system.service.IFileDealService;
import cn.source.new_class_system.the_class.entity.UploadTask;
import cn.source.new_class_system.the_class.service.IClassSystemActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
* @Date 2023/1/17 15:39
* @ClassTitle 后台文件处理
* @ClassDescription 后台对系统中文件的处理
* @Author ZhuMT
*/
@RestController
@RequestMapping("/system")
public class FillDealController {

    @Autowired
    private IClassSystemActionService iClassSystemActionService;

    @Autowired
    private IFileDealService iFillDealService;


    /**
    * @Date 2023/1/17 15:40
    * @MethodDescription 用于下载某个任务中的所以文件并以压缩包形式返回
    * @Param 1. taskId;
    */
    @GetMapping("/download/task_file")
    public void downloadTaskFile(HttpServletResponse response, Integer taskId){

//        //获取一个Map值,key为文件名,value是byte[]
        Map<String, byte[]> fileListMap = iFillDealService.getFileListMap(taskId);

        UploadTask uploadTask = iClassSystemActionService.passTaskIdSelectTask(taskId);


        String fileName = uploadTask.getIssuer();

        response.setHeader("content-type", "application/octet-stream");

        response.setContentType("application/octet-stream");

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                + uploadTask.getPublishDate()
                + StringUtils.fileNameChineseEncoding(fileName));

        try {
            ZipUtils.downloadZipForByteMore(response.getOutputStream(), fileListMap);
        } catch (IOException e) {
            throw new GlobalException(ErrorCode.EXPORT_ERROR);
        }

    }
    
    
    
}
