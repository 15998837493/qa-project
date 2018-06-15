
package com.qa.master.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qa.main.service.BaseService;
import com.qa.master.condition.ItemCondition;
import com.qa.master.dao.ItemDAO;
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
@Service
public class ItemServiceImpl extends BaseService implements ItemService {

    @Resource
    private ItemDAO itemDAO;

    @Override
    @Transactional(readOnly = true)
    public Map<String, ItemPojo> getMasItemMap(String custSex) {
        return itemDAO.getMasItemMap("masItemMap").get(custSex);
    }

    /**
     * 从缓存中获取指标基础信息 key=masItemCodes
     * 
     * @author zcq
     * @param key
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getMasItemCodeList() {
        return itemDAO.getMasItemCodeList("masItemCodes");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemPojo> queryItem(ItemCondition condition) {
        return itemDAO.queryItem(condition);
    }

}
