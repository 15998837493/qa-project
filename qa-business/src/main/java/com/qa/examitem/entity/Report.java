
package com.qa.examitem.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.qa.main.entity.MappedEntity;

/**
 * 检查项目结果主表实体
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-12-20 zcq 初版
 */
@Entity
@Table(name = "cus_report")
public class Report extends MappedEntity {

    private static final long serialVersionUID = -4928579249535314727L;

    /** 报告编码 */
    @UpdateAnnotation
    private String reportId;

    /** 客户编码 */
    @UpdateAnnotation
    private String custCode;

    /** 姓名 */
    @UpdateAnnotation
    private String custName;

    /** 性别 */
    @UpdateAnnotation
    private String custSex;

    /** 出生日期 */
    @UpdateAnnotation
    private Date custBirthday;

    /** 身高 */
    @UpdateAnnotation
    private BigDecimal custHeight;

    /** 体重 */
    @UpdateAnnotation
    private BigDecimal custWeight;

    /** 腰围 */
    @UpdateAnnotation
    private BigDecimal custWaistline;

    /** 体力水平： 轻= st_light，中=st_medium，重= st_weight */
    @UpdateAnnotation
    private String custPlevel;

    /** 身份证号码 */
    @UpdateAnnotation
    private String custIcard;

    /** 问卷编码 */
    @UpdateAnnotation
    private String questionId;

    /** 问卷日期 */
    @UpdateAnnotation
    private Date reportDate;

    /** 问卷报告路径 */
    @UpdateAnnotation
    private String reportPdfPath;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "report_id")
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Column(name = "cust_code")
    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    @Column(name = "cust_name")
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    @Column(name = "cust_sex")
    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    @Column(name = "cust_plevel")
    public String getCustPlevel() {
        return custPlevel;
    }

    public void setCustPlevel(String custPlevel) {
        this.custPlevel = custPlevel;
    }

    @Column(name = "cust_icard")
    public String getCustIcard() {
        return custIcard;
    }

    public void setCustIcard(String custIcard) {
        this.custIcard = custIcard;
    }

    @Column(name = "cust_weight")
    public BigDecimal getCustWeight() {
        return custWeight;
    }

    public void setCustWeight(BigDecimal custWeight) {
        this.custWeight = custWeight;
    }

    @Column(name = "cust_waistline")
    public BigDecimal getCustWaistline() {
        return custWaistline;
    }

    public void setCustWaistline(BigDecimal custWaistline) {
        this.custWaistline = custWaistline;
    }

    @Column(name = "cust_height")
    public BigDecimal getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(BigDecimal custHeight) {
        this.custHeight = custHeight;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "cust_birthday")
    public Date getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(Date custBirthday) {
        this.custBirthday = custBirthday;
    }

    @Column(name = "question_id")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "report_date")
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Column(name = "report_pdf_path")
    public String getReportPdfPath() {
        return reportPdfPath;
    }

    public void setReportPdfPath(String reportPdfPath) {
        this.reportPdfPath = reportPdfPath;
    }

}
