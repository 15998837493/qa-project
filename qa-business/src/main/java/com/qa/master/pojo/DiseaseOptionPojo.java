
package com.qa.master.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 问卷答案
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-8 zcq 初版
 */
public class DiseaseOptionPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 问卷编号 */
    @QueryFieldAnnotation
    private String questionId;

    /** 问题编号 */
    @QueryFieldAnnotation
    private String problemId;

    /** 选项编号 */
    @QueryFieldAnnotation
    private String optionId;

    /** 疾病编码 */
    @QueryFieldAnnotation
    private String diseaseCode;

    /** 疾病得分 */
    @QueryFieldAnnotation
    private Integer diseaseScore;

    /** 参考值（文本） */
    @QueryFieldAnnotation
    private String refString;

    /** 参考值（下限） */
    @QueryFieldAnnotation
    private BigDecimal refValMin;

    /** 参考值（上限） */
    @QueryFieldAnnotation
    private BigDecimal refValMax;

    /** 比较条件 */
    @QueryFieldAnnotation
    private String refCompare;

    /** 得分规则 */
    @QueryFieldAnnotation
    private String scoreRule;

    /** 得分组合 */
    @QueryFieldAnnotation
    private String scoreRelation;

    /** 危险因素 */
    @QueryFieldAnnotation
    private String riskFactor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public Integer getDiseaseScore() {
        return diseaseScore;
    }

    public void setDiseaseScore(Integer diseaseScore) {
        this.diseaseScore = diseaseScore;
    }

    public String getRefString() {
        return refString;
    }

    public void setRefString(String refString) {
        this.refString = refString;
    }

    public BigDecimal getRefValMin() {
        return refValMin;
    }

    public void setRefValMin(BigDecimal refValMin) {
        this.refValMin = refValMin;
    }

    public BigDecimal getRefValMax() {
        return refValMax;
    }

    public void setRefValMax(BigDecimal refValMax) {
        this.refValMax = refValMax;
    }

    public String getRefCompare() {
        return refCompare;
    }

    public void setRefCompare(String refCompare) {
        this.refCompare = refCompare;
    }

    public String getScoreRule() {
        return scoreRule;
    }

    public void setScoreRule(String scoreRule) {
        this.scoreRule = scoreRule;
    }

    public String getScoreRelation() {
        return scoreRelation;
    }

    public void setScoreRelation(String scoreRelation) {
        this.scoreRelation = scoreRelation;
    }

    public String getRiskFactor() {
        return riskFactor;
    }

    public void setRiskFactor(String riskFactor) {
        this.riskFactor = riskFactor;
    }

}
