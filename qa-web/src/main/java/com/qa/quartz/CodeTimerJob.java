
package com.qa.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import com.qa.CacheProjectInfo;

/**
 * 码表信息缓存
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-11 zcq 初版
 */
public class CodeTimerJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeTimerJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("定时任务--开始执行---基础数据缓存");
        // 方法执行开始时间
        long starttime = System.currentTimeMillis();
        WebApplicationContext applicationContext = CacheProjectInfo.getInstance().getApplicationContext();
        if (applicationContext == null) {
            throw new JobExecutionException("spring 容器未初始化");
        }

        // AdviceDAO adviceDAO = (AdviceDAO) applicationContext.getBean("adviceDAO");
        // adviceDAO.getLifeAdviceMap("lifeAdviceMap");
        // ItemDAO itemDAO = (ItemDAO) applicationContext.getBean("itemDAO");
        // itemDAO.getMasItemMap("masItemMap");
        // DiseaseItemDAO diseaseItemDAO = (DiseaseItemDAO) applicationContext.getBean("diseaseItemDAO");
        // String[] diseaseCodes = new String[] {"D0001", "D0002", "D0003", "D0004", "D0005", "D0006", "D0007", "D0008",
        // "D0009", "D0001"};
        // for (String code : diseaseCodes) {
        // diseaseItemDAO.queryDiseaseItemByDiseaseCode(code);
        // }
        // DiseaseOptionDAO diseaseOptionDAO = (DiseaseOptionDAO) applicationContext.getBean("diseaseOptionDAO");
        // diseaseOptionDAO.getMasOptionMap("diseaseOptionMap");

        // 方法执行时间
        long processTime = System.currentTimeMillis() - starttime;
        LOGGER.info("定时任务--执行完成---基础数据缓存----运行时间" + processTime + "ms;");
    }
}
