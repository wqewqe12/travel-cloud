package com.travel.lpz.core.exception;

import com.travel.lpz.core.untils.R;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private Integer code = R.CODE_ERROR;

    public BusinessException() {
        super(R.MSG_ERROR);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code,String message) {
        super(message);
        this.code = code;
    }


}
