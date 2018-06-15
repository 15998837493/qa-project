
package com.qa.master.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;

import com.qa.master.condition.ItemCondition;
import com.qa.master.pojo.ItemPojo;

/**
 * 指标信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-16 zcq 初版
 */
@Validated
public interface ItemService {

    /**
     * 根据性别查询指标信息
     * 
     * @author zcq
     * @param custSex
     * @return
     */
    Map<String, ItemPojo> getMasItemMap(String custSex);

    /**
     * 从缓存中获取指标基础信息 key=masItemCodes
     * 
     * @author zcq
     * @param key
     * @return
     */
    List<String> getMasItemCodeList();

    /**
     * 检索指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ItemPojo> queryItem(ItemCondition condition);

}
