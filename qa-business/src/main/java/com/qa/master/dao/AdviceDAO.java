
package com.qa.master.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.qa.master.condition.AdviceCondition;
import com.qa.master.pojo.AdvicePojo;

/**
 * 生活方式建议DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-9 zcq 初版
 */
@Repository
public class AdviceDAO extends HibernateTemplate {

    /**
     * 条件检索生活方式建议
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<AdvicePojo> queryAdvice(AdviceCondition condition) {
        if (condition == null) {
            condition = new AdviceCondition();
        }
        // 初始化变量-检索条件类
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "AdvicePojo");

        String querySQL = "SELECT" + DaoUtils.getSQLFields(AdvicePojo.class, "AdvicePojo")
                + "        FROM mas_advice AS AdvicePojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), AdvicePojo.class);
    }

    /**
     * 从缓存中获取生活方式建议 key=lifeAdviceMap
     * 
     * @author zcq
     * @param key
     * @return
     */
    // @Transactional
    // @Cacheable(value = "lifeAdviceCache", key = "#key")
    public Map<String, String> getLifeAdviceMap(String key) {
        Map<String, String> lifeAdviceMap = new HashMap<String, String>();
        // 生活方式建议
        List<AdvicePojo> adviceList = queryAdvice(null);
        if (CollectionUtils.isNotEmpty(adviceList)) {
            for (AdvicePojo advice : adviceList) {
                String type = advice.getAdviceType() + "_" + advice.getAdviceSubclass();
                if (!lifeAdviceMap.containsKey(type)) {
                    lifeAdviceMap.put(type, advice.getAdviceContent());
                }
            }
        }
        return lifeAdviceMap;
    }

}
