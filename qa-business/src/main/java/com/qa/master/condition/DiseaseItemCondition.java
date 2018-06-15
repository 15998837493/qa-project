
package com.qa.master.condition;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 报告检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-25 zcq 初版
 */
public class DiseaseItemCondition implements Serializable {

    private static final long serialVersionUID = -6319783025844304652L;

    /** 报告编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 客户编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemCode;

    /** 客户编码 */
    @QueryConditionAnnotation(name = "itemCode", symbol = SymbolConstants.IN)
    private List<String> itemCodeList;

    /** 疾病编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseCode;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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

    public List<String> getItemCodeList() {
        return itemCodeList;
    }

    public void setItemCodeList(List<String> itemCodeList) {
        this.itemCodeList = itemCodeList;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
