
package com.qa.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.qa.main.entity.MappedEntity;

/**
 * 指标得分配置
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-28 zcq 初版
 */
@Entity
@Table(name = "mas_disease_item")
public class DiseaseItem extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主键 */
    private String id;

    /** 指标编码 */
    @UpdateAnnotation
    private String itemCode;

    /** 疾病编码 */
    @UpdateAnnotation
    private String diseaseCode;

    /** 疾病得分 */
    @UpdateAnnotation
    private Integer diseaseScore;

    /** 得分规则 */
    @UpdateAnnotation
    private String scoreRule;

    /** 得分组合 */
    @UpdateAnnotation
    private String scoreRelation;

    /** 危险因素指标编码 */
    @UpdateAnnotation
    private String riskCode;

    /** 危险因素规则 */
    @UpdateAnnotation
    private String riskRule;

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

    @Column(name = "item_code")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    @Column(name = "risk_code")
    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    @Column(name = "risk_rule")
    public String getRiskRule() {
        return riskRule;
    }

    public void setRiskRule(String riskRule) {
        this.riskRule = riskRule;
    }

    @Column(name = "risk_factor")
    public String getRiskFactor() {
        return riskFactor;
    }

    public void setRiskFactor(String riskFactor) {
        this.riskFactor = riskFactor;
    }

}
