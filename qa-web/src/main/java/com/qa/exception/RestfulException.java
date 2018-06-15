
package com.qa.exception;

/**
 * 接口访问异常类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-23 zcq 初版
 */
public class RestfulException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 错误码 */
    private String errorCode = "90002";

    /** 错误信息 */
    private String errorMessage;

    /** 错误参数信息 */
    private String[] errorParams;

    public RestfulException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public RestfulException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    public RestfulException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public RestfulException(String errorCode, String[] errorParams) {
        super();
        this.errorCode = errorCode;
        this.errorParams = errorParams;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String[] getErrorParams() {
        return errorParams;
    }

    public void setErrorParams(String[] errorParams) {
        this.errorParams = errorParams;
    }

}
