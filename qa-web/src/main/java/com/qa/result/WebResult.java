
package com.qa.result;

import com.qa.main.results.ResultCode;

/**
 * 接口返回信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-12 zcq 初版
 */
public class WebResult<T> {

    /** 操作状态 */
    private String resultCode;

    /** 提示信息 */
    private String resultMessage;

    /** 操作结果 */
    private T value;

    public WebResult() {
        resultCode = "";
        resultMessage = "";// 出于 layer插件 layer.alert(arg)的参数不能为空的考虑，对此进行初始化
    }

    public WebResult<T> setSuc(T value) {
        this.resultCode = ResultCode.SUC;
        this.resultMessage = "成功！";
        this.value = value;
        return this;
    }

    public WebResult<T> setSuc(T value, String resultMessage) {
        this.resultCode = ResultCode.SUC;
        this.resultMessage = resultMessage;
        this.value = value;
        return this;
    }

    public WebResult<T> setError(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        return this;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
