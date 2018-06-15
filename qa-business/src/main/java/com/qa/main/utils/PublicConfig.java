
package com.qa.main.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 公共配置参数
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-18 zcq 初版
 */
@Component(value = "config-web")
public class PublicConfig {

    /** resource资源服务器路径 */
    @Value("${resource.server.path}")
    public String resourceServerPath;

    /** resource资源绝对路径 */
    @Value("${resource.absolute.path}")
    public String resourceAbsolutePath;

    /** resource报告访问路径 */
    @Value("${resource.report.path}")
    public String resourceReportPath;

    public String getResourceServerPath() {
        return resourceServerPath;
    }

    public void setResourceServerPath(String resourceServerPath) {
        this.resourceServerPath = resourceServerPath;
    }

    public String getResourceAbsolutePath() {
        return resourceAbsolutePath;
    }

    public void setResourceAbsolutePath(String resourceAbsolutePath) {
        this.resourceAbsolutePath = resourceAbsolutePath;
    }

    public String getResourceReportPath() {
        return resourceReportPath;
    }

    public void setResourceReportPath(String resourceReportPath) {
        this.resourceReportPath = resourceReportPath;
    }

}
