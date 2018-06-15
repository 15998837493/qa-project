
package com.qa.master.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.qa.main.enums.Flag;
import com.qa.master.condition.ItemCondition;
import com.qa.master.pojo.ItemPojo;

/**
 * 指标DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-7 zcq 初版
 */
@Repository
public class ItemDAO extends HibernateTemplate {

    /**
     * 条件查询指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<ItemPojo> queryItem(ItemCondition condition) {
        if (condition == null) {
            condition = new ItemCondition();
        }
        // 初始化变量-检索条件类
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ItemPojo");

        String querySQL = "SELECT" + DaoUtils.getSQLFields(ItemPojo.class, "ItemPojo")
                + "        FROM mas_item AS ItemPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), ItemPojo.class);
    }

    /**
     * 从缓存中获取指标基础信息 key=masItemCodes
     * 
     * @author zcq
     * @param key
     * @return
     */
    // @Transactional
    // @Cacheable(value = "itemCodeCache", key = "#key")
    public List<String> getMasItemCodeList(String key) {
        String sql = "SELECT item_code FROM mas_item AS ItemPojo WHERE flag = :flag";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flag", Flag.normal.getValue());
        return this.SQLQuery(sql, params);
    }

    /**
     * 从缓存中获取指标基础信息 key=masItemMap
     * 
     * @author zcq
     * @param key
     * @return
     */
    // @Transactional
    // @Cacheable(value = "itemCache", key = "#key")
    public Map<String, Map<String, ItemPojo>> getMasItemMap(String key) {
        Map<String, Map<String, ItemPojo>> masItemMap = new HashMap<String, Map<String, ItemPojo>>();
        Map<String, ItemPojo> maleItemMap = new LinkedHashMap<String, ItemPojo>();
        Map<String, ItemPojo> femaleItemMap = new LinkedHashMap<String, ItemPojo>();
        // 指标基础数据
        List<ItemPojo> masItemList = queryItem(null);
        if (CollectionUtils.isNotEmpty(masItemList)) {
            for (ItemPojo itemPojo : masItemList) {
                String itemSex = itemPojo.getItemSex();
                String itemCode = itemPojo.getItemCode();
                if ("all".equals(itemSex)) {
                    maleItemMap.put(itemCode, itemPojo);
                    femaleItemMap.put(itemCode, itemPojo);
                } else if ("male".equals(itemSex)) {
                    maleItemMap.put(itemCode, itemPojo);
                } else if ("female".equals(itemSex)) {
                    femaleItemMap.put(itemCode, itemPojo);
                }
            }
        }
        masItemMap.put("male", maleItemMap);
        masItemMap.put("female", femaleItemMap);
        return masItemMap;
    }
}
