
package com.qa.master.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qa.main.service.BaseService;
import com.qa.master.dao.QuestionDAO;
import com.qa.master.pojo.QuestionProblemOptionPojo;
import com.qa.master.pojo.QuestionProblemPojo;

/**
 * 问卷信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-16 zcq 初版
 */
@Service
public class QuestionServiceImpl extends BaseService implements QuestionService {

    @Resource
    private QuestionDAO questionDAO;

    @Override
    @Transactional(readOnly = true)
    public Map<String, List<String>> getMasQuestionInfo() {
        return questionDAO.getMasQuestionList("masQuestionInfo");
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionProblemPojo> queryProblemAndOptionByQuestionId(String questionId) {
        List<QuestionProblemPojo> problemList = questionDAO.queryProblemByQuestionId(questionId);
        if (CollectionUtils.isNotEmpty(problemList)) {
            for (QuestionProblemPojo problem : problemList) {
                List<QuestionProblemOptionPojo> optionList = questionDAO.queryOptionByProblemId(problem.getProblemId());
                problem.setOptionList(optionList);
            }
        }
        return problemList;
    }

}
