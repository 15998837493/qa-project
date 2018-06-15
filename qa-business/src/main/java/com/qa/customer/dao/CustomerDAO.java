
package com.qa.customer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.qa.customer.condition.CustomerCondition;
import com.qa.customer.pojo.CustomerPojo;

/**
 * 客户DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-22 zcq 初版
 */
@Repository
public class CustomerDAO extends HibernateTemplate {

    /**
     * 条件检索客户信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<CustomerPojo> queryCustomer(CustomerCondition condition) {
        if (condition == null) {
            condition = new CustomerCondition();
        }
        // 初始化变量-检索条件类
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "CustomerPojo");

        String querySQL = "SELECT" + DaoUtils.getSQLFields(CustomerPojo.class, "CustomerPojo")
                + "             ,get_age(CustomerPojo.cust_birthday) AS custAge"
                + "             ,report.report_id AS reportId"
                + "        FROM cus_customer AS CustomerPojo"
                + "        JOIN cus_report AS report ON report.cust_code=CustomerPojo.cust_code"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), CustomerPojo.class);
    }

}
