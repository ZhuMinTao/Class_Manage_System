package cn.source.new_class_system.base.result;


import cn.source.new_class_system.base.constants.ErrorCode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JSONResult {

    private Integer code = 200;

    private String message = "success";

    private Long total;

    private String token;

    private Long pageSize;

    private Object data;



    public static JSONResult getInstance(){
        return new JSONResult();
    }

    public static JSONResult getInstance(Object obj){
        JSONResult jsonResult = new JSONResult();
        jsonResult.data = obj;
        if(obj instanceof List){
            List list = (List)obj;
            jsonResult.total = Long.valueOf(list.size());
        }
        return jsonResult;
    }




    public static JSONResult getInstance(Page page){
        JSONResult jsonResult = new JSONResult();
        jsonResult.pageSize = page.getPages()==0?null:page.getPages();
        jsonResult.data =  page.getRecords();
        jsonResult.total = Long.valueOf(page.getTotal()==0?page.getRecords().size():page.getTotal());
        return jsonResult;
    }

    public static JSONResult getInstance(String token){
        JSONResult jsonResult = new JSONResult();
        jsonResult.token = token;
        return jsonResult;
    }


    public static JSONResult getInstance(ErrorCode errorCode){
        JSONResult jsonResult = new JSONResult();
        jsonResult.code = errorCode.getCode();
        jsonResult.message = errorCode.getMessage();
        return jsonResult;
    }

    public static JSONResult getStringInstance(String text) {
        JSONResult jsonResult = new JSONResult();
        jsonResult.data = text;
        return jsonResult;
    }
}
