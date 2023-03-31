package cn.source.new_class_system.the_class.web.controller;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.result.JSONResult;
import cn.source.new_class_system.base.utils.FastDfsUtils;
import cn.source.new_class_system.base.utils.OSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadFileController {


   /*
   @PostMapping("/file")
    public JSONResult uploadFile(MultipartFile file) {
        Map<String,String> strings = null;
        try {
            strings = FastDfsUtils.uploadFile(file.getInputStream(), file.getOriginalFilename());
        }
        catch (Exception e) {
            throw new GlobalException(ErrorCode.INSIDE_THE_SERVER_ERROR);
        }
        return JSONResult.getInstance(strings);
    }
    */

    @Value("${oss.http_file_url}")
    private String ossUrl;
    /**
     * @Date 2023/1/16 20:41
     * @MethodDescription 上传文件
     * @Param 1. 文件
     */
    @PostMapping("/file")
    public Map<String, String> uploadFile(MultipartFile file) throws Exception {
        OSSUtils ossUtils = new OSSUtils();
        String s = ossUtils.uploadImg2Oss(file);
        Map<String,String> map = new HashMap<>();
        map.put("url",ossUrl+s);
        map.put("message","success");
        map.put("code","200");
        return map;
    }

}
