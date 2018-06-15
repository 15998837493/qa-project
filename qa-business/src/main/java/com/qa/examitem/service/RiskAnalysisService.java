
package com.qa.examitem.service;

import org.springframework.validation.annotation.Validated;

/**
 * 危险分层分析
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-25 zcq 初版
 */
@Validated
public interface RiskAnalysisService {

    /**
     * 危险分层分析
     * 
     * @author zcq
     * @param reportId
     */
    void analysisRisk(String reportId);

}
