
package com.qa.examitem.condition;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 报告检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-25 zcq 初版
 */
public class ReportCondition implements Serializable {

    private static final long serialVersionUID = -6319783025844304652L;

    /** 报告编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String reportId;

    /** 客户编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custCode;

    /** 客户编码 */
    @QueryConditionAnnotation(name = "custCode", symbol = SymbolConstants.IN)
    private List<String> custCodeList;

    /** 检查日期 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String reportDate;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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

    public List<String> getCustCodeList() {
        return custCodeList;
    }

    public void setCustCodeList(List<String> custCodeList) {
        this.custCodeList = custCodeList;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
