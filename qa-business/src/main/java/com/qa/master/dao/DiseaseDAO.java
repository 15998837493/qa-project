
package com.qa.master.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.qa.master.condition.DiseaseCondition;
import com.qa.master.pojo.DiseasePojo;

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
public class DiseaseDAO extends HibernateTemplate {

    /**
     * 条件检索疾病信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<DiseasePojo> queryDisease(DiseaseCondition condition) {
        if (condition == null) {
            condition = new DiseaseCondition();
        }
        // 初始化变量-检索条件类
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiseasePojo");

        String querySQL = "SELECT" + DaoUtils.getSQLFields(DiseasePojo.class, "DiseasePojo")
                + "        FROM mas_disease AS DiseasePojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), DiseasePojo.class);
    }

    /**
     * 从缓存中获取疾病信息
     * 
     * @author zcq
     * @param key
     * @return
     */
    // @Transactional
    // @CachePut(value = "diseaseCache", key = "#key")
    public Map<String, DiseasePojo> queryDiseaseAll(String key) {
        Map<String, DiseasePojo> map = new HashMap<String, DiseasePojo>();
        List<DiseasePojo> list = queryDisease(null);
        if (CollectionUtils.isNotEmpty(list)) {
            for (DiseasePojo disease : list) {
                map.put(disease.getDiseaseCode(), disease);
            }
        }
        return map;
    }

}
