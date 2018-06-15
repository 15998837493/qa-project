
package com.qa.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.qa.main.utils.PublicConfig;

/**
 * controller 基类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-21 zcq 初版
 */
@Component
public class BaseController {

    @Resource
    public PublicConfig publicConfig;

    @Resource
    private MessageSource messageSource;

    @Resource
    public HttpServletRequest request;

    /**
     * 读取配置文件
     * 
     * @author zcq
     * @return
     */
    public Properties readProperties() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config-web.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }

    /**
     * 通过返回码取返回信息
     * 
     * @author zcq
     * @param errCode
     * @param params
     * @return
     */
    protected String getMessage(String errCode, String... params) {
        Locale myLocale = Locale.getDefault();// 获得系统默认的国家/语言环境
        return messageSource.getMessage("ERROR_" + errCode, params, myLocale);
    }

}
