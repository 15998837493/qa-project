
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
 * 疾病表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-28 zcq 初版
 */
@Entity
@Table(name = "mas_disease")
public class Disease extends MappedEntity {

    private static final long serialVersionUID = 7692318920843956166L;

    /** 关联疾病编码 */
    @UpdateAnnotation
    private String diseaseCode;

    /** 关联疾病名称 */
    @UpdateAnnotation
    private String diseaseName;

    /** 疾病ICD10编码 */
    @UpdateAnnotation
    private String diseaseIcd10;

    /** 干预疾病类型 */
    @UpdateAnnotation
    private String diseaseType;

    /** 流行病学 */
    @UpdateAnnotation
    private String diseaseEpidemiology;

    /** 伤害 */
    @UpdateAnnotation
    private String diseaseHarm;

    /** 概述 */
    @UpdateAnnotation
    private String diseaseDescription;

    /** 预防要点 */
    @UpdateAnnotation
    private String diseasePrevention;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "disease_code")
    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    @Column(name = "disease_name")
    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    @Column(name = "disease_epidemiology")
    public String getDiseaseEpidemiology() {
        return diseaseEpidemiology;
    }

    public void setDiseaseEpidemiology(String diseaseEpidemiology) {
        this.diseaseEpidemiology = diseaseEpidemiology;
    }

    @Column(name = "disease_harm")
    public String getDiseaseHarm() {
        return diseaseHarm;
    }

    public void setDiseaseHarm(String diseaseHarm) {
        this.diseaseHarm = diseaseHarm;
    }

    @Column(name = "disease_description")
    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    @Column(name = "disease_type")
    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    @Column(name = "disease_icd10")
    public String getDiseaseIcd10() {
        return diseaseIcd10;
    }

    public void setDiseaseIcd10(String diseaseIcd10) {
        this.diseaseIcd10 = diseaseIcd10;
    }

    @Column(name = "disease_prevention")
    public String getDiseasePrevention() {
        return diseasePrevention;
    }

    public void setDiseasePrevention(String diseasePrevention) {
        this.diseasePrevention = diseasePrevention;
    }

}
