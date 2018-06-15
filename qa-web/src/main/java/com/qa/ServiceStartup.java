
package com.qa;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 接口服务启动
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-29 zcq 初版
 */
public class ServiceStartup extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceStartup.class);

    /**
     * 通过web方式，系统初始化，启动相应的服务
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 加载顺序不能改变
        CacheProjectInfo projectInfo = CacheProjectInfo.getInstance();

        // SPRING 信息保存
        WebApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(servletConfig.getServletContext());
        projectInfo.setApplicationContext(applicationContext);// 设置SPRING属性
        LOGGER.info("加载Spring配置成功.");

        // 定时任务启动
        // QuartzCenterJob centerJob = new QuartzCenterJob();
        // centerJob.start();
        // projectInfo.setQuartzCenterJob(centerJob);
        // LOGGER.info("启动-定时任务加载成功！");

//        try {
//            // 设置基础数据缓存
//            AdviceDAO adviceDAO = (AdviceDAO) projectInfo.getApplicationContext().getBean("adviceDAO");
//            adviceDAO.getLifeAdviceMap("lifeAdviceMap");
//            ItemDAO itemDAO = (ItemDAO) projectInfo.getApplicationContext().getBean("itemDAO");
//            itemDAO.getMasItemMap("masItemMap");
//            itemDAO.getMasItemCodeList("masItemCodes");
//            DiseaseItemDAO diseaseItemDAO = (DiseaseItemDAO)
//                    projectInfo.getApplicationContext().getBean("diseaseItemDAO");
//            String[] diseaseCodes = new String[] {"D0001", "D0002", "D0003", "D0004", "D0005", "D0006", "D0007",
//                    "D0008", "D0009"};
//            for (String code : diseaseCodes) {
//                diseaseItemDAO.queryDiseaseItemByDiseaseCode(code);
//            }
//            DiseaseOptionDAO diseaseOptionDAO = (DiseaseOptionDAO) projectInfo.getApplicationContext().getBean(
//                    "diseaseOptionDAO");
//            diseaseOptionDAO.getMasOptionMap("diseaseOptionMap");
//
//            DiseaseDAO diseaseDAO = (DiseaseDAO) projectInfo.getApplicationContext().getBean("diseaseDAO");
//            diseaseDAO.queryDiseaseAll("mas_disease");
//
//            QuestionDAO questionDAO = (QuestionDAO) projectInfo.getApplicationContext().getBean("questionDAO");
//            questionDAO.getMasQuestionList("masQuestionInfo");
//        } catch (Exception e) {
//        }
//        LOGGER.info("启动-基础数据加载成功！");
    }
}
