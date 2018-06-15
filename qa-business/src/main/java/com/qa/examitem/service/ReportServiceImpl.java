
package com.qa.examitem.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qa.examitem.condition.ReportCondition;
import com.qa.examitem.dao.ReportDAO;
import com.qa.examitem.entity.Report;
import com.qa.examitem.pojo.ReportPojo;
import com.qa.main.service.BaseService;

/**
 * 体检项目结果记录
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-25 zcq 初版
 */
@Service
public class ReportServiceImpl extends BaseService implements ReportService {

    @Resource
    private ReportDAO reportDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ReportPojo> queryReport(ReportCondition condition) {
        if (condition == null) {
            condition = new ReportCondition();
        }
        List<ReportPojo> customerList = reportDAO.queryReport(condition);
        return customerList;
    }

    @Override
    @Transactional(readOnly = true)
    public ReportPojo getReport(String reportId) {
        return reportDAO.getTransformerBean(reportId, Report.class, ReportPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addReport(Report report) {
        return (String) reportDAO.save(report);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateReport(Report report) {
        reportDAO.updateHQL(report);
    }

}
