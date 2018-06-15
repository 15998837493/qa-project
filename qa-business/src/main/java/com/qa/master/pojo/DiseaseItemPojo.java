
package com.qa.master.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 问卷指标
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-7 zcq 初版
 */
public class DiseaseItemPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 指标编码 */
    @QueryFieldAnnotation
    private String itemCode;

    /** 疾病编码 */
    @QueryFieldAnnotation
    private String diseaseCode;

    /** 疾病得分 */
    @QueryFieldAnnotation
    private Integer diseaseScore;

    /** 得分规则 */
    @QueryFieldAnnotation
    private String scoreRule;

    /** 得分组合 */
    @QueryFieldAnnotation
    private String scoreRelation;

    /** 危险因素指标编码 */
    @QueryFieldAnnotation
    private String riskCode;

    /** 危险因素规则 */
    @QueryFieldAnnotation
    private String riskRule;

    /** 危险因素 */
    @QueryFieldAnnotation
    private String riskFactor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    public String getRiskRule() {
        return riskRule;
    }

    public void setRiskRule(String riskRule) {
        this.riskRule = riskRule;
    }

    public String getRiskFactor() {
        return riskFactor;
    }

    public void setRiskFactor(String riskFactor) {
        this.riskFactor = riskFactor;
    }

}
