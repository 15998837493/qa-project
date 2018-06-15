
package com.qa.master.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;

import com.qa.master.condition.DiseaseCondition;
import com.qa.master.pojo.DiseasePojo;

/**
 * 疾病信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-16 zcq 初版
 */
@Validated
public interface DiseaseService {

    /**
     * 条件检索疾病信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<DiseasePojo> queryDisease(DiseaseCondition condition);

    /**
     * 条件检索疾病信息封装到Map中
     * 
     * @author zcq
     * @param condition
     * @return
     */
    Map<String, DiseasePojo> queryDiseaseMap(DiseaseCondition condition);

}
