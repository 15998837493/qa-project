
package com.qa.customer.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.qa.customer.condition.CustomerCondition;
import com.qa.customer.entity.Customer;
import com.qa.customer.pojo.CustomerPojo;

/**
 * 客户管理
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-6-7 zcq 初版
 */
@Validated
public interface CustomerService {

    /**
     * 检索客户信息
     * 
     * @author zcq
     * @return
     */
    List<CustomerPojo> queryCustomer(CustomerCondition condition);

    /**
     * 查询客户信息--根据【主键】
     * 
     * @author zcq
     * @param custId
     * @return
     */
    CustomerPojo getCustomer(String custId);

    /**
     * 添加客户信息
     * 
     * @author zcq
     * @param customer
     * @return
     */
    CustomerPojo addCustomer(Customer customer);

    /**
     * 修改客户信息
     * 
     * @author zcq
     * @param customer
     */
    CustomerPojo updateCustomer(Customer customer);

    /**
     * 删除客户信息
     * 
     * @author zcq
     * @param custId
     */
    void removeCustomer(String custId);

}
