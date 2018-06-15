
package com.qa.examitem.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;

import com.qa.examitem.condition.ReportResultCondition;
import com.qa.examitem.pojo.ReportResultPojo;

/**
 * 检测项目分析结果
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-13 zcq 初版
 */
@Validated
public interface ReportResultService {

    /**
     * 条件检索检测项目指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ReportResultPojo> queryReportResult(ReportResultCondition condition);

    /**
     * 条件查询检测项目指标结果并封装在Map中
     * 
     * @author zcq
     * @param condition
     * @return
     */
    Map<String, ReportResultPojo> queryReportResultMap(String tableName, String reportId);

    /**
     * 保存单表指标数据
     * 
     * @author zcq
     * @param reportId
     * @param tableName
     * @param detailList
     */
    void addReportResults(String reportId, String tableName, List<ReportResultPojo> detailList);

    /**
     * 保存检测指标
     * 
     * @author zcq
     * @param examDataDetail
     * @param tableName
     * @return
     */
    String addReportResult(ReportResultPojo examDataDetail, String tableName);

    /**
     * 删除指标数据
     * 
     * @author zcq
     * @param tableName
     * @param reportId
     */
    void deleteReportResultByReportId(String tableName, String reportId);

}
