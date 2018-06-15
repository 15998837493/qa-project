
package com.qa.master.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;

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
@Validated
public interface QuestionService {

    /**
     * 从缓存中获取问题基础信息 key=masProblemIds
     * 
     * @author zcq
     * @param key
     * @return
     */
    Map<String, List<String>> getMasQuestionInfo();

    /**
     * 查询问卷问题及答案
     * 
     * @author zcq
     * @param questionId
     * @return
     */
    List<QuestionProblemPojo> queryProblemAndOptionByQuestionId(String questionId);

}
