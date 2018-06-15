
package com.qa.controller.response;

import java.io.Serializable;

/**
 * 检查项目结果
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-28 zcq 初版
 */
public class QuestionReport implements Serializable {

    private static final long serialVersionUID = -4928579249535314727L;

    /** 报告编码 */
    private String reportId;

    /** 客户编码 */
    private String custCode;

    /** 问卷日期 */
    private String reportDate;

    /** 问卷报告路径 */
    private String reportPdfPath;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportPdfPath() {
        return reportPdfPath;
    }

    public void setReportPdfPath(String reportPdfPath) {
        this.reportPdfPath = reportPdfPath;
    }

}
