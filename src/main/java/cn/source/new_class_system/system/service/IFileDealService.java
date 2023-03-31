package cn.source.new_class_system.system.service;

import cn.source.new_class_system.the_class.entity.UploadTask;

import java.util.Map;

public interface IFileDealService {
    Map<String,byte[]> getFileListMap(Integer taskId);

}
