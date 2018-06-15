
package com.qa.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 检测项目结果指标信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-18 zcq 初版
 */
public class ReportItemPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 检测项目报告编码 */
    @QueryFieldAnnotation
    private String reportId;

    /** 指标编码 */
    @QueryFieldAnnotation
    private String itemCode;

    /** 指标名称 */
    @QueryFieldAnnotation
    private String itemName;

    /** 指标机构名称 */
    @QueryFieldAnnotation
    private String itemInsName;

    /** 指标标准参考值（文本） */
    @QueryFieldAnnotation
    private String itemRefStr;

    /** 指标标准参考值（下限） */
    @QueryFieldAnnotation
    private BigDecimal itemRefValMin;

    /** 指标标准参考值（上限） */
    @QueryFieldAnnotation
    private BigDecimal itemRefValMax;

    /** 指标单位 */
    @QueryFieldAnnotation
    private String itemUnit;

    /** 指标值类型 */
    @QueryFieldAnnotation
    private String itemValueType;

    /** 指标值 */
    @QueryFieldAnnotation
    private String itemValue;

    /** 指标结果 */
    @QueryFieldAnnotation
    private String itemResult;

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

    public String getItemInsName() {
        return itemInsName;
    }

    public void setItemInsName(String itemInsName) {
        this.itemInsName = itemInsName;
    }

    public String getItemRefStr() {
        return itemRefStr;
    }

    public void setItemRefStr(String itemRefStr) {
        this.itemRefStr = itemRefStr;
    }

    public BigDecimal getItemRefValMin() {
        return itemRefValMin;
    }

    public void setItemRefValMin(BigDecimal itemRefValMin) {
        this.itemRefValMin = itemRefValMin;
    }

    public BigDecimal getItemRefValMax() {
        return itemRefValMax;
    }

    public void setItemRefValMax(BigDecimal itemRefValMax) {
        this.itemRefValMax = itemRefValMax;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemValueType() {
        return itemValueType;
    }

    public void setItemValueType(String itemValueType) {
        this.itemValueType = itemValueType;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult;
    }

}
