
package com.qa.exception;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.qa.controller.BaseController;
import com.qa.main.results.ResultCode;
import com.qa.result.WebResult;

/**
 * 异常统一处理类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-21 zcq 初版
 */
@ControllerAdvice
public class GlobalExceptionController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionController.class);

    /**
     * 异常处理
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> exceptionHandle(Exception e) {

        WebResult<String> result = new WebResult<String>();
        result.setError(ResultCode.ERROR_90000, e.getMessage());

        this.insertLog(result, e);
        return result;
    }

    /**
     * 验证请求参数异常
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> bindExceptionHandle(BindException e) {
        WebResult<String> result = new WebResult<String>();
        List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuffer fieldErrorString = new StringBuffer();
        for (FieldError fieldError : fieldErrors) {
            fieldErrorString.append(fieldError.getDefaultMessage()).append("、");
        }
        String error = fieldErrorString.substring(0, fieldErrorString.length() - 1);
        result.setError(ResultCode.ERROR_80000, error);

        this.insertLog(result, e);
        return result;
    }

    /**
     * 缺少请求参数
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> missingServletRequestParameterExceptionHandle(MissingServletRequestParameterException e) {
        WebResult<String> result = new WebResult<String>();
        result.setError(ResultCode.ERROR_80000, this.getMessage(ResultCode.ERROR_80000));

        this.insertLog(result, e);
        return result;
    }

    /**
     * 自定义业务异常
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(RestfulException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> restfulExceptionHandle(RestfulException e) {
        WebResult<String> result = new WebResult<String>();
        String errorMessage = e.getErrorMessage();
        if (StringUtils.isEmpty(errorMessage)) {
            errorMessage = this.getMessage(e.getErrorCode());
        }
        result.setError(e.getErrorCode(), errorMessage);

        return result;
    }

    /**
     * 不支持的请求方法
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> httpRequestMethodNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e) {
        WebResult<String> result = new WebResult<String>();
        result.setError(ResultCode.ERROR_70000, this.getMessage(ResultCode.ERROR_70000));

        this.insertLog(result, e);
        return result;
    }

    /**
     * 无效的方法请求参数
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        WebResult<String> result = new WebResult<String>();
        result.setError(ResultCode.ERROR_80000, this.getMessage(ResultCode.ERROR_80000));

        this.insertLog(result, e);
        return result;
    }

    /**
     * 类型不匹配
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> typeMismatchExceptionHandle(TypeMismatchException e) {
        WebResult<String> result = new WebResult<String>();
        result.setError(ResultCode.ERROR_80000, this.getMessage(ResultCode.ERROR_80000));

        this.insertLog(result, e);
        return result;
    }

    /**
     * 传入参数不正确
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> httpMessageNotReadableExceptionHandle(HttpMessageNotReadableException e) {
        WebResult<String> result = new WebResult<String>();
        result.setError(ResultCode.ERROR_80000, this.getMessage(ResultCode.ERROR_80000));

        this.insertLog(result, e);
        return result;
    }

    /**
     * 请求URL没找到或不正确
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> NoHandlerFoundExceptionHandle(NoHandlerFoundException e) {
        WebResult<String> result = new WebResult<String>();
        result.setError(ResultCode.ERROR_70000, this.getMessage(ResultCode.ERROR_70000));

        this.insertLog(result, e);
        return result;
    }

    /**
     * 参数错误
     * 
     * @author zcq
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WebResult<String> IllegalArgumentException(IllegalArgumentException e) {
        WebResult<String> result = new WebResult<String>();
        result.setError(ResultCode.ERROR_90000, this.getMessage(ResultCode.ERROR_90000));

        this.insertLog(result, e);
        return result;
    }

    /**
     * 记录日志
     * 
     * @author zcq
     * @param result
     * @param e
     */
    private void insertLog(WebResult<String> result, Exception e) {
        e.printStackTrace();
        LOGGER.error("[qa接口服务]" + e.getMessage());
    }

}
