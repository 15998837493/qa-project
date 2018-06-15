
package com.qa.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 访问权限验证
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-23 zcq 初版
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 程序支持的输入输出数据格式
        // String input = "application/json";
        // String output = "application/json";
        //
        // // 1 验证 Accept 和 Content-Type 格式是否正确
        // String accept = (String) request.getHeader("Accept");
        // if (StringUtils.isEmpty(accept) || !input.equals(accept)) {
        // throw new RestfulException(ResultCode.ERROR_70000);
        // }
        // String contentType = (String) request.getHeader("Content-Type");
        // if (StringUtils.isEmpty(contentType) || !output.equals(contentType)) {
        // throw new RestfulException(ResultCode.ERROR_70000);
        // }

        // 2 接入平台身份验证
        /*
         * String authorization = (String) request.getHeader("Authorization");
         * 
         * if (StringUtils.isEmpty(authorization)) {
         * throw new RestfulException(ResultCode.NO_PERMISSION);
         * }
         * 
         * Author author = authorCache.getAuthor(authorization);
         * if (author==null) {
         * throw new RestfulException(ResultCode.NO_PERMISSION);
         * }
         */

        // 3 登陆用户身份验证（建议：rest请求不保存用户状态）请求的uri
        /*
         * String uri = request.getRequestURI();
         * if (StringUtils.isNotEmpty(uri)) {
         * if (uri.contains("unauth")) {
         * return true;
         * }
         * }
         * 
         * // 获取loginId
         * String loginId = (String) request.getHeader("loginId");
         * if (StringUtils.isEmpty(loginId)) {
         * throw new RestfulException(ResultCode.NO_LOGIN);
         * }
         * // 检验缓存中是否有该用户
         * Loginer loginer = loginCache.getLogin(loginId);
         * if (loginer == null) {
         * throw new RestfulException(ResultCode.NO_LOGIN);
         * }
         */

        boolean bResult = false;
        String token = (String) request.getSession().getAttribute("userCode");
        // LoggerFactory.getLogger("[" + titleName + "]").info("登录用户IP地址：" + request.getRemoteAddr());
        if (StringUtils.isEmpty(token)) {
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                    .getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf(
                    "XMLHttpRequest") > -1))) {
                // 如果不是异步请求
                System.out.println("-------------------------timeout.action---------------------------");
                response.sendRedirect(request.getContextPath() + "/timeout.action");
            } else {
                // AJAX返回
                try {
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    writer.write("timeout");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bResult;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

}
