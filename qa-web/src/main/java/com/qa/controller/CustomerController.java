
package com.qa.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.qa.customer.entity.Customer;
import com.qa.customer.pojo.CustomerPojo;
import com.qa.customer.service.CustomerService;
import com.qa.result.WebResult;

/**
 * 问卷首页
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-5-30 zcq 初版
 */
@Controller
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;

    /**
     * 检索客户信息列表
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = "/rest/query_customer.action")
    public @ResponseBody
    WebResult<List<CustomerPojo>> queryCustomer() {
        WebResult<List<CustomerPojo>> webResult = new WebResult<List<CustomerPojo>>();
        List<CustomerPojo> customerList = customerService.queryCustomer(null);
        return webResult.setSuc(customerList);
    }

    /**
     * 添加客户信息
     * 
     * @author zcq
     * @param customerPojo
     * @return
     */
    @RequestMapping(value = "/rest/add_customer.action")
    public @ResponseBody
    WebResult<CustomerPojo> addCustomer(@RequestBody CustomerPojo customerPojo) {
        WebResult<CustomerPojo> webResult = new WebResult<CustomerPojo>();
        CustomerPojo resultCustomer = customerService.addCustomer(
                TransformerUtils.transformerProperties(Customer.class, customerPojo));
        return webResult.setSuc(resultCustomer);
    }

    /**
     * 修改客户信息
     * 
     * @author zcq
     * @param customerPojo
     * @return
     */
    @RequestMapping(value = "/rest/update_customer.action")
    public @ResponseBody
    WebResult<CustomerPojo> updateCustomer(@RequestBody CustomerPojo customerPojo) {
        WebResult<CustomerPojo> webResult = new WebResult<CustomerPojo>();
        CustomerPojo resultCustomer = customerService.updateCustomer(
                TransformerUtils.transformerProperties(Customer.class, customerPojo));
        return webResult.setSuc(resultCustomer);
    }

    /**
     * 删除客户信息
     * 
     * @author zcq
     * @param custId
     * @return
     */
    @RequestMapping(value = "/rest/remove_customer.action")
    public @ResponseBody
    WebResult<Boolean> removeCustomer(String custId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        customerService.removeCustomer(custId);
        return webResult.setSuc(true);
    }

}
