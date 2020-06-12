package com.lemonfish.exception;

import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.util.MyJsonResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.example.demo.exception
 * @date 2020/4/27 8:58
 */
@RestControllerAdvice
public class BaseExceptionHandler {
    /**
     * 通用异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public MyJsonResult baseExceptionHandler(BaseException exception) {
        return MyJsonResult.fail(exception.getCodeEnum(),exception.getAdditionalMsg());
    }

    /**
     * @Description 校验ResponseBody
     *
     * @param ex ex
     *
     * @return com.example.demo.util.MyJsonResult
     * @author Lemonfish
     * @date 2020/4/27 9:18
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MyJsonResult handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return MyJsonResult.fail(CodeEnum.FAIL_INVALID_PARAM,errors);
    }
/*    *//**
     * @Description 验证请求参数(Path Variables 和 Request Parameters)
     *
     * @param e e
     *
     * @return com.example.demo.util.MyJsonResult
     * @author Lemonfish
     * @date 2020/4/27 9:19
     *//*
    @ExceptionHandler(ConstraintViolationException.class)
    MyJsonResult handleConstraintViolationException(ConstraintViolationException e) {
        return MyJsonResult.fail(CodeEnum.FAIL_INVALID_PARAM,e.getMessage());
    }*/
}
