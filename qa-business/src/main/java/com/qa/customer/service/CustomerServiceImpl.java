/**
 * 
 */

package com.qa.customer.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.maths.CreateRandomCode;
import com.qa.customer.condition.CustomerCondition;
import com.qa.customer.dao.CustomerDAO;
import com.qa.customer.entity.Customer;
import com.qa.customer.pojo.CustomerPojo;
import com.qa.examitem.dao.ReportDAO;
import com.qa.examitem.entity.Report;
import com.qa.main.enums.Flag;
import com.qa.main.service.BaseService;

/**
 * 客户管理
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
@Service
public class CustomerServiceImpl extends BaseService implements CustomerService {

    @Resource
    private CustomerDAO customerDAO;// 患者DAO

    @Resource
    private ReportDAO reportDAO;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerPojo> queryCustomer(CustomerCondition condition) {
        if (condition == null) {
            condition = new CustomerCondition();
        }
        List<CustomerPojo> customerList = customerDAO.queryCustomer(condition);
        return customerList;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerPojo getCustomer(String custId) {
        return customerDAO.getTransformerBean(custId, Customer.class, CustomerPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public CustomerPojo addCustomer(Customer customer) {
        // 去掉名称空格
        if (StringUtils.isNotBlank(customer.getCustName())) {
            customer.setCustName(customer.getCustName().trim());
        }
        customer.setCustCode(CreateRandomCode.getRandomCode(10));
        // 保存客户信息
        customerDAO.save(customer);

        // TODO:
        Report report = TransformerUtils.transformerProperties(Report.class, customer);
        report.setQuestionId("Q001");
        reportDAO.save(report);

        return customerDAO.getTransformerBean(customer.getCustId(), Customer.class, CustomerPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public CustomerPojo updateCustomer(Customer customer) {
        if (customer == null || StringUtils.isEmpty(customer.getCustId())) {
            throw new ServiceException("客户信息为空");
        }
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("custId", HQLSymbol.EQ.toString(), customer.getCustId()));
        // 去掉名称空格
        if (StringUtils.isNotEmpty(customer.getCustName())) {
            customer.setCustName(customer.getCustName().trim());
        }
        customerDAO.updateHQL(customer, conditionParams);

        return customerDAO.getTransformerBean(customer.getCustId(), Customer.class, CustomerPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeCustomer(String custId) {
        if (StringUtils.isBlank(custId)) {
            throw new ServiceException("客户信息为空");
        }
        Customer customer = new Customer();
        customer.setCustId(custId);
        customer.setFlag(Flag.deleted.getValue());
        customerDAO.updateHQL(customer);
    }
}
