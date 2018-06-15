
package com.qa.master.condition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 疾病检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-16 zcq 初版
 */
public class DiseaseCondition implements Serializable {

    private static final long serialVersionUID = -6319783025844304652L;

    /** 关联疾病编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseCode;

    /** 关联疾病名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String diseaseName;

    /** 疾病ICD10编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseIcd10;

    /** 干预疾病类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseType;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
