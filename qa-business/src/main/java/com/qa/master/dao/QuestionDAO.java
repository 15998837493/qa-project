
package com.qa.master.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.qa.main.enums.Flag;
import com.qa.master.pojo.QuestionProblemOptionPojo;
import com.qa.master.pojo.QuestionProblemPojo;

/**
 * 问卷DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-10 zcq 初版
 */
@Repository
public class QuestionDAO extends HibernateTemplate {

    /**
     * 查询问题选项
     * 
     * @author zcq
     * @param problemIdList
     * @return
     */
    public List<QuestionProblemOptionPojo> queryOptionByProblemId(String problemId) {
        if (StringUtils.isBlank(problemId)) {
            return null;
        }

        String sql = "SELECT" + DaoUtils.getSQLFields(QuestionProblemOptionPojo.class, "OptionPojo")
                + "   FROM mas_question_problem_option AS OptionPojo"
                + "   WHERE problem_id = :problemId AND flag = :flag";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("problemId", problemId);
        paramsMap.put("flag", Flag.normal.getValue());

        return this.SQLQueryAlias(sql, paramsMap, QuestionProblemOptionPojo.class);
    }

    /**
     * 从缓存中获取问卷基础信息 key=masQuestionInfo
     * 
     * @author zcq
     * @param key
     * @return
     */
    // @Transactional
    // @Cacheable(value = "masQuestionCache", key = "#key")
    public Map<String, List<String>> getMasQuestionList(String key) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flag", Flag.normal.getValue());

        List<String> problemIdList = this.SQLQuery("SELECT problem_id FROM mas_question_problem WHERE flag = :flag", params);
        map.put("problemIdList", problemIdList);

        List<String> optionIdList = this.SQLQuery("SELECT problem_option_id FROM mas_question_problem_option WHERE flag = :flag",
                params);
        map.put("optionIdList", optionIdList);

        List<String> inputIdList = this.SQLQuery("SELECT option_id"
                + " FROM mas_disease_option "
                + " WHERE flag = :flag AND ref_compare != 'like' AND score_rule = 'compare'", params);
        map.put("inputIdList", inputIdList);

        return map;
    }

    /**
     * 查询问卷问题
     * 
     * @author zcq
     * @param questionId
     * @return
     */
    public List<QuestionProblemPojo> queryProblemByQuestionId(String questionId) {
        if (StringUtils.isBlank(questionId)) {
            return null;
        }
        String sql = "SELECT" + DaoUtils.getSQLFields(QuestionProblemPojo.class, "QuestionProblemPojo")
                + "   FROM mas_question_problem AS QuestionProblemPojo"
                + "   WHERE question_id = :questionId AND flag = :flag";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("questionId", questionId);
        paramsMap.put("flag", Flag.normal.getValue());

        return this.SQLQueryAlias(sql, paramsMap, QuestionProblemPojo.class);
    }

}
