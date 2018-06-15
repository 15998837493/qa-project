
package com.qa.master.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.qa.main.enums.Flag;
import com.qa.master.condition.DiseaseItemCondition;
import com.qa.master.pojo.DiseaseItemPojo;

/**
 * 问卷指标DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-7 zcq 初版
 */
@Repository
public class DiseaseItemDAO extends HibernateTemplate {

    /**
     * 条件查询疾病相关的指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<DiseaseItemPojo> queryDiseaseItem(DiseaseItemCondition condition) {
        if (condition == null) {
            condition = new DiseaseItemCondition();
        }
        // 初始化变量-检索条件类
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiseaseItemPojo");

        String querySQL = "SELECT" + DaoUtils.getSQLFields(DiseaseItemPojo.class, "DiseaseItemPojo")
                + "        FROM mas_disease_item AS DiseaseItemPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), DiseaseItemPojo.class);
    }

    /**
     * 根据疾病编码查询指标
     * 
     * @author zcq
     * @param diseaseCode
     * @return
     */
    // @Transactional
    // @Cacheable(value = "diseaseItemCache", key = "#diseaseCode")
    public List<DiseaseItemPojo> queryDiseaseItemByDiseaseCode(String diseaseCode) {
        if (StringUtils.isEmpty(diseaseCode)) {
            return null;
        }
        DiseaseItemCondition condition = new DiseaseItemCondition();
        condition.setDiseaseCode(diseaseCode);
        return this.queryDiseaseItem(condition);
    }

    /**
     * 计算疾病的指标得分
     * 
     * @author zcq
     * @param diseaseCode
     * @param itemCodeList
     * @return
     */
    public Integer calculateDiseaseItemScore(String diseaseCode, List<String> itemCodeList) {
        if (StringUtils.isEmpty(diseaseCode) || CollectionUtils.isEmpty(itemCodeList)) {
            return 0;
        }
        String sql = "SELECT SUM(t.disease_code) FROM ("
                + "        SELECT SUM(disease_score) AS disease_code"
                + "        FROM mas_disease_item"
                + "        WHERE disease_code = :diseaseCode AND score_rule = 'add' AND item_code IN (:itemCodeList) AND flag = :flag"
                + "        UNION ALL"
                + "        SELECT MAX(disease_score) AS disease_code "
                + "        FROM mas_disease_item "
                + "        WHERE disease_code = :diseaseCode AND score_rule = 'bloodpressure' AND item_code IN (:itemCodeList) AND flag = :flag"
                + "        GROUP BY score_relation HAVING COUNT(score_relation) = 2"
                + "        UNION ALL"
                + "        SELECT disease_score AS disease_code "
                + "        FROM mas_disease_item "
                + "        WHERE disease_code = :diseaseCode AND score_rule = 'group' AND item_code IN (:itemCodeList) AND flag = :flag"
                + "        GROUP BY score_relation"
                + "   ) t";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diseaseCode", diseaseCode);
        paramsMap.put("itemCodeList", itemCodeList);
        paramsMap.put("flag", Flag.normal.getValue());
        BigDecimal score = this.SQLQueryFirst(sql, paramsMap);

        return (score == null) ? 0 : score.intValue();
    }

    /**
     * 指标危险因素
     * 
     * @author zcq
     * @param diseaseCode
     * @param itemCodeList
     * @return
     */
    public List<Object[]> calculateDiseaseItemRiskFactor(String diseaseCode, List<String> itemCodeList) {
        if (StringUtils.isEmpty(diseaseCode) || CollectionUtils.isEmpty(itemCodeList)) {
            return null;
        }
        String sql = "SELECT * FROM ("
                + "       SELECT risk_code, risk_factor"
                + "       FROM mas_disease_item"
                + "       WHERE disease_code = :diseaseCode"
                + "           AND risk_rule = 'add'"
                + "           AND item_code IN (:itemCodeList)"
                + "           AND ((risk_factor != '' AND !ISNULL(risk_factor)) OR (risk_code != '' AND !ISNULL(risk_code)))"
                + "           AND flag = :flag"
                + "       UNION ALL"
                + "       SELECT risk_code, risk_factor"
                + "       FROM mas_disease_item"
                + "       WHERE disease_code = :diseaseCode"
                + "           AND risk_rule = 'bloodpressure'"
                + "           AND item_code IN (:itemCodeList)"
                + "           AND flag = :flag"
                + "       GROUP BY risk_factor HAVING COUNT(risk_factor) = 2"
                + "    ) t";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diseaseCode", diseaseCode);
        paramsMap.put("itemCodeList", itemCodeList);
        paramsMap.put("flag", Flag.normal.getValue());

        return this.SQLQuery(sql, paramsMap);
    }

}
