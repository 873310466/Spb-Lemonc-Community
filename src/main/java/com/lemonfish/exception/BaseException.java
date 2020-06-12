package com.lemonfish.exception;

import com.lemonfish.enumcode.CodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Masics
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException{

    private CodeEnum codeEnum;
    private String additionalMsg;

    public BaseException(CodeEnum codeEnum,String additionalMsg) {
        super(codeEnum.getLabel());
        this.codeEnum = codeEnum;
        this.additionalMsg = additionalMsg;
    }

    public BaseException(CodeEnum codeEnum) {
        super(codeEnum.getLabel());
        this.codeEnum = codeEnum;
    }
}
