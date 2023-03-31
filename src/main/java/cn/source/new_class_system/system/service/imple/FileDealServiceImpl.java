package cn.source.new_class_system.system.service.imple;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.exception.GlobalException;
import cn.source.new_class_system.base.utils.FastDfsUtils;
import cn.source.new_class_system.base.utils.OSSUtils;
import cn.source.new_class_system.system.service.IFileDealService;
import cn.source.new_class_system.the_class.entity.UploadAddress;
import cn.source.new_class_system.the_class.service.IClassSystemActionService;
import cn.source.new_class_system.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FileDealServiceImpl implements IFileDealService {

    @Autowired
    private IClassSystemActionService iClassSystemActionService;

    @Override
    public Map<String, byte[]> getFileListMap(Integer taskId) {

        List<UploadAddress> uploadAddresses = iClassSystemActionService.passTaskIdSelectUploadAddress(taskId);

        OSSUtils ossUtils = new OSSUtils();

        Map<String,byte[]> map = new HashMap<>();

        uploadAddresses.stream().forEach(e->{
            try {
                byte[] bytes =  ossUtils.getObjectByte(e.getFileUrl());
                User user = e.getUser();
                map.put(user.getUserCode()+user.getUserName()+'.'+e.getSuffix(),bytes);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });


        //fastDFs

//        Map<String,byte[]> map = new HashMap<>();
//
//        uploadAddresses.stream().forEach(e->{
//            String[] split = e.getFileUrl().split("/",2);
//
//            byte[] bytes = FastDfsUtils.downloadFileByte(split[0], split[1]);
//
//            User user = e.getUser();
//
//            map.put(user.getUserCode()+user.getUserName()+'.'+e.getSuffix(),bytes);
//
//        });
//
        if(map.size()==0){
            throw new GlobalException(ErrorCode.NONE_FIND_RESOURCE);
        }
        return map;

        //OSS
    }
}
