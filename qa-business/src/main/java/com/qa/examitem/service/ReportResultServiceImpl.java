
package com.qa.examitem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qa.examitem.condition.ReportResultCondition;
import com.qa.examitem.dao.ReportResultDAO;
import com.qa.examitem.pojo.ReportResultPojo;
import com.qa.main.service.BaseService;

/**
 * 检测项目分析结果
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-13 zcq 初版
 */
@Service
public class ReportResultServiceImpl extends BaseService implements ReportResultService {

    @Resource
    private ReportResultDAO reportResultDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ReportResultPojo> queryReportResult(ReportResultCondition condition) {
        return reportResultDAO.queryReportResult(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, ReportResultPojo> queryReportResultMap(String tableName, String reportId) {
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(reportId)) {
            return null;
        }
        ReportResultCondition condition = new ReportResultCondition();
        condition.setTableName(tableName);
        condition.setReportId(reportId);
        List<ReportResultPojo> examList = reportResultDAO.queryReportResult(condition);

        Map<String, ReportResultPojo> resultMap = new HashMap<String, ReportResultPojo>();
        if (CollectionUtils.isNotEmpty(examList)) {
            for (ReportResultPojo detail : examList) {
                String itemCode = detail.getItemCode();
                if (!resultMap.containsKey(itemCode)) {
                    resultMap.put(itemCode, detail);
                }
            }
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addReportResults(String reportId, String tableName, List<ReportResultPojo> detailList) {
        if (CollectionUtils.isNotEmpty(detailList)) {
            String insId = this.getInsId();
            // 保存新数据
            for (ReportResultPojo detail : detailList) {
                detail.setReportId(reportId);
                reportResultDAO.addReportResult(detail, tableName, insId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addReportResult(ReportResultPojo examDataDetail, String tableName) {
        return reportResultDAO.addReportResult(examDataDetail, tableName, this.getInsId());
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteReportResultByReportId(String tableName, String reportId) {
        reportResultDAO.deleteReportResultByReportId(tableName, reportId);
    }

}
