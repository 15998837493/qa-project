
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

import com.qa.examitem.condition.ReportItemCondition;
import com.qa.examitem.dao.ReportItemDAO;
import com.qa.examitem.pojo.ReportItemPojo;
import com.qa.main.service.BaseService;

@Service
public class ReportItemServiceImpl extends BaseService implements ReportItemService {

    @Resource
    private ReportItemDAO reportItemDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ReportItemPojo> queryReportItem(ReportItemCondition condition) {
        return reportItemDAO.queryReportItem(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, ReportItemPojo> queryReportItemMap(String tableName, String reportId) {
        ReportItemCondition condition = new ReportItemCondition();
        condition.setTableName(tableName);
        condition.setReportId(reportId);
        List<ReportItemPojo> examList = reportItemDAO.queryReportItem(condition);

        Map<String, ReportItemPojo> resultMap = new HashMap<String, ReportItemPojo>();
        if (CollectionUtils.isNotEmpty(examList)) {
            for (ReportItemPojo detail : examList) {
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
    public Integer addReportItems(String reportId, List<ReportItemPojo> detailList, String tableName) {
        int count = 0;
        if (CollectionUtils.isNotEmpty(detailList)) {
            String insId = this.getInsId();
            // 保存新数据
            for (ReportItemPojo detail : detailList) {
                if (StringUtils.isNotEmpty(detail.getItemCode()) && StringUtils.isNotEmpty(detail.getItemValue())) {
                    detail.setReportId(reportId);
                    reportItemDAO.addReportItem(detail, tableName, insId);
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addReportItem(ReportItemPojo examItemPojo, String tableName) {
        return reportItemDAO.addReportItem(examItemPojo, tableName, this.getInsId());
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteReportItem(String tableName, String reportId) {
        reportItemDAO.deleteReportItem(tableName, reportId);
    }

}
