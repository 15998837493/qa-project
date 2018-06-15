
package com.qa.examitem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.qa.examitem.condition.ReportCondition;
import com.qa.examitem.pojo.ReportPojo;

/**
 * 检查项目记录
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-21 zcq 初版
 */
@Repository
public class ReportDAO extends HibernateTemplate {

    /**
     * 条件检索客户信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<ReportPojo> queryReport(ReportCondition condition) {
        if (condition == null) {
            condition = new ReportCondition();
        }
        // 初始化变量-检索条件类
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ReportPojo");

        String querySQL = "SELECT" + DaoUtils.getSQLFields(ReportPojo.class, "ReportPojo")
                + "        FROM cus_report AS ReportPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), ReportPojo.class);
    }

}
