
package com.qa.aop;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.mnt.health.core.exception.ServiceException;
import com.qa.exception.RestfulException;
import com.qa.main.results.ResultCode;

/**
 * Controller-aop
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-12 zcq 初版
 */
@Component
@Aspect
public class ControllerAspectHandler {

    private static final String CONTROLLER_POINT_CUT = "execution (* com.qa.controller..*.*(..))";

    @Autowired
    private HttpServletRequest request;

    @Resource
    public MessageSource messageSource;

    /**
     * 调用前通知
     * 
     * @author zcq
     * @param jp
     * @throws Throwable
     */
    @Before(value = CONTROLLER_POINT_CUT)
    public void controllerBefore(JoinPoint jp) throws Throwable {
        // LoggerFactory.getLogger(jp.getTarget().getClass()).info("登录用户IP地址：" + request.getRemoteAddr());
        LoggerFactory.getLogger(jp.getTarget().getClass()).info("【请求url】：【" + request.getRequestURI() + "】");
        Object[] methodParams = jp.getArgs();
        String webMethodName = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
        StringBuffer methodParamStr = new StringBuffer("【Controller】调用方法：【" + webMethodName + "】输入参数：");
        if (methodParams != null) {
            for (int i = 0; i < methodParams.length && methodParams[i] != null; i++) {
                String simpleName = methodParams[i].getClass().getSimpleName();
                methodParamStr.append("【" + simpleName + "：" + methodParams[i].toString() + "】");
            }
            LoggerFactory.getLogger(jp.getTarget().getClass()).info(methodParamStr.toString());
        }
    }

    /**
     * 调用中通知
     * 
     * @author zcq
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = CONTROLLER_POINT_CUT)
    public Object controllerAround(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = null;
        String errorCode = ResultCode.SUC;
        String errorMessage = "";
        try {
            retVal = pjp.proceed();
        } catch (ServiceException e) {
            errorCode = e.getErrorCode();
            errorMessage = e.getErrorMessage();
            if (StringUtils.isEmpty(errorMessage)) {
                errorMessage = getMessageByCode(e.getErrorCode(), e.getErrorParams());
            }
            throw new RestfulException(errorCode, errorMessage);
        } catch (RestfulException e) {
            errorCode = e.getErrorCode();
            errorMessage = e.getErrorMessage();
            if (StringUtils.isEmpty(errorMessage)) {
                errorMessage = getMessageByCode(e.getErrorCode(), e.getErrorParams());
            }
            throw new RestfulException(errorCode, errorMessage);
        } catch (Exception e) {
            errorCode = ResultCode.ERROR_90000;
            errorMessage = e.getMessage();
            if (StringUtils.isEmpty(errorMessage)) {
                errorMessage = getMessageByCode(errorCode, new String[] {});
            }
            throw e;
        } finally {
            if (StringUtils.isEmpty(errorMessage)) {
                errorMessage = "成功！";
            }
            LoggerFactory.getLogger(pjp.getTarget().getClass()).info("调用类=" + pjp.getTarget().getClass().getName()
                    + ",调用接口名=" + pjp.getSignature().getName()
                    + ",调用接口返回码=" + errorCode
                    + ",调用接口返回信息=" + errorMessage);
        }
        return retVal;
    }

    /**
     * 调用返回后通知
     * 
     * @author zcq
     * @param jp
     * @param result
     */
    @AfterReturning(value = CONTROLLER_POINT_CUT, returning = "result")
    public void afterReturn(JoinPoint jp, Object result) {
        String methodName = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
        LoggerFactory.getLogger(jp.getTarget().getClass()).info(
                "【Controller】调用方法：【" + methodName + "】返回值：【" + ToStringBuilder.reflectionToString(result) + "】\n");
    }

    /**
     * 通过返回码取返回信息
     * 
     * @author zcq
     * @param resultCode
     * @return
     */
    private String getMessageByCode(String resultCode, String... params) {
        Locale myLocale = Locale.getDefault();// 获得系统默认的国家/语言环境
        return messageSource.getMessage("ERROR_" + resultCode, params, myLocale);
    }

}
