
package com.qa;

import java.io.Serializable;

import org.springframework.web.context.WebApplicationContext;

import com.qa.quartz.QuartzCenterJob;

/**
 * 内存项目信息(当启动项目时，自动初始化)相关信息随运行而变化
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-29 zcq 初版
 */
public class CacheProjectInfo implements Serializable {

    private static final long serialVersionUID = 6109705473637741197L;

    private static CacheProjectInfo instance;// 单体实例

    private CacheProjectInfo() {
    }

    public static CacheProjectInfo getInstance() {
        if (instance == null) {
            instance = new CacheProjectInfo();
        }
        return instance;
    }

    /** 项目名 */
    private String projectName = "服务接口";

    /** 项目版本号 */
    private String projectVersion = "v1";

    /** SPRING信息 */
    private WebApplicationContext applicationContext;

    /** 定时任务 */
    private QuartzCenterJob quartzCenterJob;

    /** token值 */
    private String token;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public WebApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public QuartzCenterJob getQuartzCenterJob() {
        return quartzCenterJob;
    }

    public void setQuartzCenterJob(QuartzCenterJob quartzCenterJob) {
        this.quartzCenterJob = quartzCenterJob;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
