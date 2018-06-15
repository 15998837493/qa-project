
package com.qa;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.qa.examitem.dao.ReportDAO;
import com.qa.examitem.service.ReportAnswerService;
import com.qa.examitem.service.ReportItemService;
import com.qa.examitem.service.ReportService;
import com.qa.examitem.service.RiskAnalysisService;
import com.qa.master.dao.DiseaseOptionDAO;

/**
 * junit单元测试基础类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-10 zcq 初版
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 由此引入Spring-Test框架支持
@ContextConfiguration(locations = {"classpath*:test-application-service.xml"})
// 导入配置文件
@Transactional
// 这个非常关键，如果不加入这个注解配置，事务控制就会完全失效
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
// 这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时指定自动回滚（defaultRollback = true）这样做操作的数据才不会污染数据库！
public abstract class BaseJunit {

    @Resource
    public ReportDAO reportDAO;

    @Resource
    public DiseaseOptionDAO diseaseOptionDAO;

    @Resource
    public RiskAnalysisService riskAnalysisService;

    @Resource
    public ReportService reportService;

    @Resource
    private ReportAnswerService reportAnswerService;

    @Resource
    private ReportItemService reportItemService;

    @Before
    public void setUp() throws Exception {
    }

    /**
     * 读取配置文件
     * 
     * @author zcq
     */
    public Properties readProperties() {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("config-web.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }

}
