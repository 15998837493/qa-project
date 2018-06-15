
package com.qa.examitem.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 危险分层分析结果
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-6 zcq 初版
 */
public class ReportResultPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 检测项目报告编码 */
    @QueryFieldAnnotation
    private String reportId;

    /** 编码 */
    @QueryFieldAnnotation
    private String itemCode;

    /** 名称 */
    @QueryFieldAnnotation
    private String itemName;

    /** 值 */
    @QueryFieldAnnotation
    private String itemValue;

    /** 单位 */
    @QueryFieldAnnotation
    private String itemUnit;

    /** 项目得分 */
    @QueryFieldAnnotation
    private Integer itemScore;

    /** 结果 */
    @QueryFieldAnnotation
    private String itemResult;

    /** 参考值 */
    @QueryFieldAnnotation
    private String itemRefValue;

    /** 比较值 */
    @QueryFieldAnnotation
    private String itemCompare;

    /** 建议 */
    @QueryFieldAnnotation
    private String itemAdvice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Integer getItemScore() {
        return itemScore;
    }

    public void setItemScore(Integer itemScore) {
        this.itemScore = itemScore;
    }

    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult;
    }

    public String getItemRefValue() {
        return itemRefValue;
    }

    public void setItemRefValue(String itemRefValue) {
        this.itemRefValue = itemRefValue;
    }

    public String getItemCompare() {
        return itemCompare;
    }

    public void setItemCompare(String itemCompare) {
        this.itemCompare = itemCompare;
    }

    public String getItemAdvice() {
        return itemAdvice;
    }

    public void setItemAdvice(String itemAdvice) {
        this.itemAdvice = itemAdvice;
    }

}
