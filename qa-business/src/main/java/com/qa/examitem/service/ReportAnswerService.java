
package com.qa.examitem.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.qa.examitem.condition.ReportAnswerCondition;
import com.qa.examitem.pojo.ReportAnswerPojo;

/**
 * 问卷答案管理
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-25 zcq 初版
 */
@Validated
public interface ReportAnswerService {

    /**
     * 检索问卷答案
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ReportAnswerPojo> queryReportAnswer(ReportAnswerCondition condition);

    /**
     * 添加问卷答案
     * 
     * @author zcq
     * @param reportId
     * @param detailList
     * @param tableName
     */
    Integer addReportAnswers(String reportId, List<ReportAnswerPojo> detailList, String tableName);

    /**
     * 批量添加问卷答案
     * 
     * @author zcq
     * @param reportAnswerPojo
     * @param tableName
     * @return
     */
    String addReportAnswer(ReportAnswerPojo reportAnswerPojo, String tableName);

    /**
     * 删除问卷答案
     * 
     * @author zcq
     * @param reportId
     * @param tableName
     */
    void deleteReportAnswer(String reportId, String tableName);

}
