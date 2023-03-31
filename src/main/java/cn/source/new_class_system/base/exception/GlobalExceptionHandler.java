package cn.source.new_class_system.base.exception;

import cn.source.new_class_system.base.constants.ErrorCode;
import cn.source.new_class_system.base.result.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public JSONResult GlobalException(GlobalException e){
        return JSONResult.getInstance(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public JSONResult Exception(){
        return JSONResult.getInstance(ErrorCode.INSIDE_THE_SERVER_ERROR);
    }

}
