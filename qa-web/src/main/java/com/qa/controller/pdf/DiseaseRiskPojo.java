
package com.qa.controller.pdf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.qa.examitem.pojo.ReportPojo;
import com.qa.examitem.pojo.ReportResultPojo;
import com.qa.master.pojo.DiseasePojo;
import com.qa.master.pojo.ItemPojo;

/**
 * 危险分层PDF数据
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-6 zcq 初版
 */
public class DiseaseRiskPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 客户信息 */
    private ReportPojo customer;

    /** 疾病信息 */
    private Map<String, DiseasePojo> diseaseMap;

    /** 指标基础信息 */
    private Map<String, ItemPojo> itemMap;

    /** 分析结果 */
    private Map<String, ReportResultPojo> resultMap;

    /** 运动建议 */
    private List<String[]> sportAdviceList;

    public ReportPojo getCustomer() {
        return customer;
    }

    public void setCustomer(ReportPojo customer) {
        this.customer = customer;
    }

    public Map<String, DiseasePojo> getDiseaseMap() {
        return diseaseMap;
    }

    public void setDiseaseMap(Map<String, DiseasePojo> diseaseMap) {
        this.diseaseMap = diseaseMap;
    }

    public Map<String, ItemPojo> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, ItemPojo> itemMap) {
        this.itemMap = itemMap;
    }

    public Map<String, ReportResultPojo> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, ReportResultPojo> resultMap) {
        this.resultMap = resultMap;
    }

    public List<String[]> getSportAdviceList() {
        return sportAdviceList;
    }

    public void setSportAdviceList(List<String[]> sportAdviceList) {
        this.sportAdviceList = sportAdviceList;
    }

}
