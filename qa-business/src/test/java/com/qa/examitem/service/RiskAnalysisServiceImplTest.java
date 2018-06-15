
package com.qa.examitem.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.qa.BaseJunit;

public class RiskAnalysisServiceImplTest extends BaseJunit {

    @Test
    public void testAnalysisRisk() throws Exception {
        riskAnalysisService.analysisRisk("123456");
        assertNotNull("数据分析完成！", 2);
    }

}
