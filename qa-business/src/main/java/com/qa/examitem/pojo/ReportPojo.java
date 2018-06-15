
package com.qa.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 检查项目结果
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-28 zcq 初版
 */
public class ReportPojo implements Serializable {

    private static final long serialVersionUID = -4928579249535314727L;

    /** 报告编码 */
    @QueryFieldAnnotation
    private String reportId;

    /** 客户编码 */
    @QueryFieldAnnotation
    private String custCode;

    /** 姓名 */
    @QueryFieldAnnotation
    private String custName;

    /** 性别 */
    @QueryFieldAnnotation
    private String custSex;

    /** 出生日期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date custBirthday;

    /** 身高 */
    @QueryFieldAnnotation
    private BigDecimal custHeight;

    /** 体重 */
    @QueryFieldAnnotation
    private BigDecimal custWeight;

    /** 腰围 */
    @QueryFieldAnnotation
    private BigDecimal custWaistline;

    /** 体力水平： 轻= st_light，中=st_medium，重= st_weight */
    @QueryFieldAnnotation
    private String custPlevel;

    /** 身份证号码 */
    @QueryFieldAnnotation
    private String custIcard;

    /** 问卷编码 */
    @QueryFieldAnnotation
    private String questionId;

    /** 问卷日期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportDate;

    /** 问卷报告路径 */
    @QueryFieldAnnotation
    private String reportPdfPath;

    /** 年龄 */
    private Integer custAge;

    /** BMI */
    private Double bmi;

    /** 推荐热量 */
    private Integer normalEnergy;

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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    public Date getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(Date custBirthday) {
        this.custBirthday = custBirthday;
    }

    public BigDecimal getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(BigDecimal custHeight) {
        this.custHeight = custHeight;
    }

    public BigDecimal getCustWeight() {
        return custWeight;
    }

    public void setCustWeight(BigDecimal custWeight) {
        this.custWeight = custWeight;
    }

    public BigDecimal getCustWaistline() {
        return custWaistline;
    }

    public void setCustWaistline(BigDecimal custWaistline) {
        this.custWaistline = custWaistline;
    }

    public String getCustPlevel() {
        return custPlevel;
    }

    public void setCustPlevel(String custPlevel) {
        this.custPlevel = custPlevel;
    }

    public String getCustIcard() {
        return custIcard;
    }

    public void setCustIcard(String custIcard) {
        this.custIcard = custIcard;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportPdfPath() {
        return reportPdfPath;
    }

    public void setReportPdfPath(String reportPdfPath) {
        this.reportPdfPath = reportPdfPath;
    }

    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Integer getNormalEnergy() {
        return normalEnergy;
    }

    public void setNormalEnergy(Integer normalEnergy) {
        this.normalEnergy = normalEnergy;
    }

}
