
package com.qa.examitem.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.qa.BaseJunit;
import com.qa.examitem.entity.Report;

public class ReportServiceImplTest extends BaseJunit {

    @Test
    public void testQueryReport() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetReport() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddReport() throws Exception {
        Report report = new Report();
        report.setCustCode("963852");
        report.setCustName("测试姓名2");
        report.setCustBirthday(new Date());
        report.setCustSex("female");
        report.setQuestionId("Q001");
        report.setReportDate(new Date());
        report.setCustHeight(BigDecimal.valueOf(175.0));
        report.setCustWeight(BigDecimal.valueOf(77.0));
        report.setCreateInsId("0");
        String reporId = reportService.addReport(report);
        assertNotNull("返回报告编码为空", reporId);
    }

    @Test
    public void testUpdateReport() {
        fail("Not yet implemented");
    }

}
