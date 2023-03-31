package cn.source.new_class_system.base.exception;

import cn.source.new_class_system.base.constants.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
    private ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
