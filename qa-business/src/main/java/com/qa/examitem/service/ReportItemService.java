
package com.qa.examitem.service;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.qa.examitem.condition.ReportItemCondition;
import com.qa.examitem.pojo.ReportItemPojo;

/**
 * 检查项目结果接口
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-21 zcq 初版
 */
@Validated
public interface ReportItemService {

    /**
     * 条件检索检测项目指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ReportItemPojo> queryReportItem(ReportItemCondition condition);

    /**
     * 条件查询检测项目指标结果并封装在Map中
     * 
     * @author zcq
     * @param condition
     * @return
     */
    Map<String, ReportItemPojo> queryReportItemMap(@NotBlank String tableName, @NotBlank String reportId);

    /**
     * 保存单表指标数据
     * 
     * @author zcq
     * @param reportId
     * @param detailList
     * @param tableName
     */
    Integer addReportItems(String reportId, List<ReportItemPojo> detailList, String tableName);

    /**
     * 保存检测指标
     * 
     * @author zcq
     * @param examItemPojo
     * @param tableName
     * @return
     */
    String addReportItem(ReportItemPojo examItemPojo, String tableName);

    /**
     * 删除指标数据
     * 
     * @author zcq
     * @param tableName
     * @param reportId
     */
    void deleteReportItem(String tableName, String reportId);

}
