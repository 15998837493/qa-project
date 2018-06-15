
package com.qa.master.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.qa.main.enums.Flag;
import com.qa.master.pojo.DiseaseOptionPojo;

/**
 * 疾病评估得分、危险因素算法
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-6 zcq 初版
 */
@Repository
public class DiseaseOptionDAO extends HibernateTemplate {

    /**
     * 检索疾病相关的答案选项
     * 
     * @author zcq
     * @param diseaseCode
     * @return
     */
    public List<DiseaseOptionPojo> queryDiseaseOptionAll() {
        String sql = "SELECT " + DaoUtils.getSQLFields(DiseaseOptionPojo.class, "DiseaseOptionPojo")
                + "   FROM mas_disease_option AS DiseaseOptionPojo"
                + "   WHERE flag = :flag";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("flag", Flag.normal.getValue());

        return this.SQLQueryAlias(sql, paramsMap, DiseaseOptionPojo.class);
    }

    /**
     * 从缓存中获取选项基础数据 key=diseaseOptionMap
     * 
     * @author zcq
     * @return
     */
    // @Transactional
    // @Cacheable(value = "diseaseOptionCache", key = "#key")
    public Map<String, Object> getMasOptionMap(String key) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 疾病选项基础数据
        List<DiseaseOptionPojo> masOptionList = queryDiseaseOptionAll();
        // 按疾病拆分
        Map<String, List<String>> diseaseChoiceOptionMap = new HashMap<String, List<String>>();// 选项
        Map<String, List<DiseaseOptionPojo>> diseaseInputOptionMap = new HashMap<String, List<DiseaseOptionPojo>>();// 填空
        if (CollectionUtils.isNotEmpty(masOptionList)) {
            for (DiseaseOptionPojo opt : masOptionList) {
                String diseaseCode = opt.getDiseaseCode();
                String optionId = opt.getOptionId();
                String scoreRule = opt.getScoreRule();
                // 选项
                if (!diseaseChoiceOptionMap.containsKey(diseaseCode)) {
                    diseaseChoiceOptionMap.put(diseaseCode, new ArrayList<String>());
                }
                // 填空
                if (!diseaseInputOptionMap.containsKey(diseaseCode)) {
                    diseaseInputOptionMap.put(diseaseCode, new ArrayList<DiseaseOptionPojo>());
                }
                if ("compare".equals(scoreRule)) {
                    diseaseInputOptionMap.get(diseaseCode).add(opt);// 填空
                } else {
                    diseaseChoiceOptionMap.get(diseaseCode).add(optionId);// 选项
                }
            }
        }
        dataMap.put("mas_option_choice", diseaseChoiceOptionMap);
        dataMap.put("mas_option_input", diseaseInputOptionMap);

        return dataMap;
    }

    /**
     * 计算疾病的问卷得分
     * 
     * @author zcq
     * @param diseaseCode
     * @param optionIdList
     * @return
     */
    public Integer calculateDiseaseScore(String diseaseCode, List<String> optionIdList) {
        if (StringUtils.isEmpty(diseaseCode) || CollectionUtils.isEmpty(optionIdList)) {
            return 0;
        }
        String sql = "SELECT SUM(t.disease_code) FROM ("
                + "        SELECT SUM(disease_score) AS disease_code"
                + "        FROM mas_disease_option"
                + "        WHERE disease_code = :diseaseCode AND score_rule = 'add' AND option_id IN (:optionIdList) AND flag = :flag"
                + "        UNION ALL"
                + "        SELECT MIN(disease_score) AS disease_code"
                + "        FROM mas_disease_option"
                + "        WHERE disease_code = :diseaseCode AND score_rule = 'min' AND option_id IN (:optionIdList) AND flag = :flag GROUP BY risk_factor"
                + "        UNION ALL"
                + "        SELECT MAX(disease_score) AS disease_code "
                + "        FROM mas_disease_option "
                + "        WHERE disease_code = :diseaseCode AND score_rule = 'max' AND option_id IN (:optionIdList) AND flag = :flag GROUP BY risk_factor"
                + "        UNION ALL"
                + "        SELECT disease_score AS disease_code "
                + "        FROM mas_disease_option "
                + "        WHERE disease_code = :diseaseCode AND score_rule = 'group' AND option_id IN (:optionIdList) AND flag = :flag GROUP BY score_relation"
                + "        UNION ALL"
                + "        SELECT MAX(disease_score) AS disease_code "
                + "        FROM mas_disease_option "
                + "        WHERE disease_code = :diseaseCode AND score_rule = 'group_max' AND option_id IN (:optionIdList) AND flag = :flag GROUP BY score_relation"
                + "   ) t";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diseaseCode", diseaseCode);
        paramsMap.put("optionIdList", optionIdList);
        paramsMap.put("flag", Flag.normal.getValue());
        BigDecimal score = this.SQLQueryFirst(sql, paramsMap);

        return (score == null) ? 0 : score.intValue();
    }

    /**
     * 计算危险因素
     * 
     * @author zcq
     * @param diseaseCode
     * @param optionIdList
     * @return
     */
    public List<String> calculateDiseaseRiskFactor(String diseaseCode, List<String> optionIdList) {
        if (StringUtils.isEmpty(diseaseCode) || CollectionUtils.isEmpty(optionIdList)) {
            return null;
        }
        String sql = "SELECT risk_factor"
                + "   FROM mas_disease_option"
                + "   WHERE disease_code = :diseaseCode AND option_id IN (:optionIdList) AND flag = :flag"
                + "   GROUP BY risk_factor";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diseaseCode", diseaseCode);
        paramsMap.put("optionIdList", optionIdList);
        paramsMap.put("flag", Flag.normal.getValue());

        return this.SQLQuery(sql, paramsMap);
    }

}
