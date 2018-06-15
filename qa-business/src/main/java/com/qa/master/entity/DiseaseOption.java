
package com.qa.master.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.qa.main.entity.MappedEntity;

/**
 * 问卷答案得分配置
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-28 zcq 初版
 */
@Entity
@Table(name = "mas_disease_option")
public class DiseaseOption extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主键 */
    private String id;

    /** 问卷编号 */
    @UpdateAnnotation
    private String questionId;

    /** 问题编号 */
    @UpdateAnnotation
    private String problemId;

    /** 选项编号 */
    @UpdateAnnotation
    private String optionId;

    /** 疾病编码 */
    @UpdateAnnotation
    private String diseaseCode;

    /** 疾病得分 */
    @UpdateAnnotation
    private Integer diseaseScore;

    /** 参考值（文本） */
    @UpdateAnnotation
    private String refString;

    /** 参考值（下限） */
    @UpdateAnnotation
    private BigDecimal refValMin;

    /** 参考值（上限） */
    @UpdateAnnotation
    private BigDecimal refValMax;

    /** 比较条件 */
    @UpdateAnnotation
    private String refCompare;

    /** 得分规则 */
    @UpdateAnnotation
    private String scoreRule;

    /** 得分组合 */
    @UpdateAnnotation
    private String scoreRelation;

    /** 危险因素 */
    @UpdateAnnotation
    private String riskFactor;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "question_id")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Column(name = "problem_id")
    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    @Column(name = "option_id")
    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    @Column(name = "disease_code")
    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    @Column(name = "disease_score")
    public Integer getDiseaseScore() {
        return diseaseScore;
    }

    public void setDiseaseScore(Integer diseaseScore) {
        this.diseaseScore = diseaseScore;
    }

    @Column(name = "ref_string")
    public String getRefString() {
        return refString;
    }

    public void setRefString(String refString) {
        this.refString = refString;
    }

    @Column(name = "ref_val_min")
    public BigDecimal getRefValMin() {
        return refValMin;
    }

    public void setRefValMin(BigDecimal refValMin) {
        this.refValMin = refValMin;
    }

    @Column(name = "ref_val_max")
    public BigDecimal getRefValMax() {
        return refValMax;
    }

    public void setRefValMax(BigDecimal refValMax) {
        this.refValMax = refValMax;
    }

    @Column(name = "ref_compare")
    public String getRefCompare() {
        return refCompare;
    }

    public void setRefCompare(String refCompare) {
        this.refCompare = refCompare;
    }

    @Column(name = "score_rule")
    public String getScoreRule() {
        return scoreRule;
    }

    public void setScoreRule(String scoreRule) {
        this.scoreRule = scoreRule;
    }

    @Column(name = "score_relation")
    public String getScoreRelation() {
        return scoreRelation;
    }

    public void setScoreRelation(String scoreRelation) {
        this.scoreRelation = scoreRelation;
    }

    @Column(name = "risk_factor")
    public String getRiskFactor() {
        return riskFactor;
    }

    public void setRiskFactor(String riskFactor) {
        this.riskFactor = riskFactor;
    }

}
