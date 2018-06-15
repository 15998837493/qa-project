
package com.qa.master.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 疾病信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-13 zcq 初版
 */
public class DiseasePojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 关联疾病编码 */
    @QueryFieldAnnotation
    private String diseaseCode;

    /** 关联疾病名称 */
    @QueryFieldAnnotation
    private String diseaseName;

    /** 疾病ICD10编码 */
    @QueryFieldAnnotation
    private String diseaseIcd10;

    /** 干预疾病类型 */
    @QueryFieldAnnotation
    private String diseaseType;

    /** 流行病学 */
    @QueryFieldAnnotation
    private String diseaseEpidemiology;

    /** 伤害 */
    @QueryFieldAnnotation
    private String diseaseHarm;

    /** 概述 */
    @QueryFieldAnnotation
    private String diseaseDescription;

    /** 预防要点 */
    @QueryFieldAnnotation
    private String diseasePrevention;

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseIcd10() {
        return diseaseIcd10;
    }

    public void setDiseaseIcd10(String diseaseIcd10) {
        this.diseaseIcd10 = diseaseIcd10;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getDiseaseEpidemiology() {
        return diseaseEpidemiology;
    }

    public void setDiseaseEpidemiology(String diseaseEpidemiology) {
        this.diseaseEpidemiology = diseaseEpidemiology;
    }

    public String getDiseaseHarm() {
        return diseaseHarm;
    }

    public void setDiseaseHarm(String diseaseHarm) {
        this.diseaseHarm = diseaseHarm;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public String getDiseasePrevention() {
        return diseasePrevention;
    }

    public void setDiseasePrevention(String diseasePrevention) {
        this.diseasePrevention = diseasePrevention;
    }

}
