
package com.qa.master.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qa.main.service.BaseService;
import com.qa.master.condition.DiseaseCondition;
import com.qa.master.dao.DiseaseDAO;
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
@Service
public class DiseaseServiceImpl extends BaseService implements DiseaseService {

    @Resource
    private DiseaseDAO diseaseDAO;

    @Override
    @Transactional(readOnly = true)
    public List<DiseasePojo> queryDisease(DiseaseCondition condition) {
        return diseaseDAO.queryDisease(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, DiseasePojo> queryDiseaseMap(DiseaseCondition condition) {
        return diseaseDAO.queryDiseaseAll("mas_disease");
    }

}
