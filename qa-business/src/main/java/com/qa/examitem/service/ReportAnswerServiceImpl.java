
package com.qa.examitem.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qa.examitem.condition.ReportAnswerCondition;
import com.qa.examitem.dao.ReportAnswerDAO;
import com.qa.examitem.pojo.ReportAnswerPojo;
import com.qa.main.service.BaseService;

@Service
public class ReportAnswerServiceImpl extends BaseService implements ReportAnswerService {

    @Resource
    private ReportAnswerDAO reportAnswerDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ReportAnswerPojo> queryReportAnswer(ReportAnswerCondition condition) {
        return reportAnswerDAO.queryReportAnswer(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public Integer addReportAnswers(String reportId, List<ReportAnswerPojo> detailList, String tableName) {
        int count = 0;
        if (StringUtils.isNotEmpty(reportId) && CollectionUtils.isNotEmpty(detailList)) {
            String insId = this.getInsId();
            // 保存新数据
            for (ReportAnswerPojo detail : detailList) {
                if (StringUtils.isNotEmpty(detail.getProblemId())
                        && StringUtils.isNotEmpty(detail.getProblemOptionId())) {
                    detail.setReportId(reportId);
                    reportAnswerDAO.addReportAnswer(detail, tableName, insId);
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addReportAnswer(ReportAnswerPojo reportAnswerPojo, String tableName) {
        return reportAnswerDAO.addReportAnswer(reportAnswerPojo, tableName, this.getInsId());
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteReportAnswer(String reportId, String tableName) {
        reportAnswerDAO.deleteReportAnswer(tableName, reportId);
    }

}
