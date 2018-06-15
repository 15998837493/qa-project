
package com.qa.examitem.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.qa.examitem.condition.ReportCondition;
import com.qa.examitem.entity.Report;
import com.qa.examitem.pojo.ReportPojo;

/**
 * 体检项目结果记录
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-25 zcq 初版
 */
@Validated
public interface ReportService {

    /**
     * 检索报告信息
     * 
     * @author zcq
     * @return
     */
    List<ReportPojo> queryReport(ReportCondition condition);

    /**
     * 查询报告信息
     * 
     * @author zcq
     * @param reportId
     * @return
     */
    ReportPojo getReport(String reportId);

    /**
     * 添加报告信息
     * 
     * @author zcq
     * @param report
     * @return
     */
    String addReport(Report report);

    /**
     * 修改报告信息
     * 
     * @author zcq
     * @param report
     */
    void updateReport(Report report);

}
