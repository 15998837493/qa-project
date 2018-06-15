
package com.qa.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.health.utils.times.JodaTimeTools;
import com.qa.controller.pdf.DiseaseRiskPdf;
import com.qa.controller.pdf.DiseaseRiskPojo;
import com.qa.controller.request.QueryReportData;
import com.qa.controller.request.RiskAnalysisData;
import com.qa.controller.response.QuestionReport;
import com.qa.examitem.condition.ReportAnswerCondition;
import com.qa.examitem.condition.ReportCondition;
import com.qa.examitem.condition.ReportItemCondition;
import com.qa.examitem.constants.RiskConstant;
import com.qa.examitem.entity.Report;
import com.qa.examitem.pojo.ReportAnswerPojo;
import com.qa.examitem.pojo.ReportItemPojo;
import com.qa.examitem.pojo.ReportPojo;
import com.qa.examitem.service.ReportAnswerService;
import com.qa.examitem.service.ReportItemService;
import com.qa.examitem.service.ReportResultService;
import com.qa.examitem.service.ReportService;
import com.qa.examitem.service.RiskAnalysisService;
import com.qa.exception.RestfulException;
import com.qa.main.results.ResultCode;
import com.qa.master.service.DiseaseService;
import com.qa.master.service.ItemService;
import com.qa.master.service.QuestionService;
import com.qa.result.WebResult;

/**
 * 分析报告管理
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-12 zcq 初版
 */
@RestController
public class ReportController extends BaseController {

    @Resource
    private RiskAnalysisService riskAnalysisService;

    @Resource
    private ReportService reportService;

    @Resource
    private ReportAnswerService reportAnswerService;

    @Resource
    private ReportItemService reportItemService;

    @Resource
    private ReportResultService reportResultService;

    @Resource
    private DiseaseService diseaseService;

    @Resource
    private ItemService itemService;

    @Resource
    private QuestionService questionService;

    // private String limiteDate = "2019-03-30";

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    /**
     * 危险分层分析报告
     * 
     * @author zcq
     * @param jsonData
     */
    @RequestMapping(value = "/rest/uploadQuestionData", method = RequestMethod.POST)
    public WebResult<String> analysisRisk(@RequestBody RiskAnalysisData data) {
        // int days = JodaTimeTools.getDays(new Date(), JodaTimeTools.toDate(limiteDate));
        // String resultMessage = "";
        // if (days <= 15 && days >= 0) {
        // resultMessage = this.getMessage(ResultCode.ERROR_90001, new String[] {days + ""});
        // }

        long starttime = System.currentTimeMillis();
        WebResult<String> webResult = new WebResult<String>();
        // 获取数据
        List<ReportAnswerPojo> answerList = data.getQuestionAnswerList();
        List<ReportItemPojo> itemList = data.getExamItemList();
        // 初步校验数据
        if (data.getCustomer() == null) {
            throw new RestfulException(ResultCode.ERROR_10001);
        }
        if (CollectionUtils.isEmpty(answerList) && CollectionUtils.isEmpty(itemList)) {
            throw new RestfulException(ResultCode.ERROR_10002);
        }
        // 第一步：记录原始数据
        Report report = TransformerUtils.transformerProperties(Report.class, data.getCustomer());
        validateCustomer(report);// 校验客户数据
        // String reportId = reportService.addReport(report);// TODO:
        String reportId = report.getReportId();

        validateAnswer(answerList);// 校验问卷答案数据
        if (CollectionUtils.isNotEmpty(answerList)) {
            reportAnswerService.deleteReportAnswer(reportId, "cus_report_risk_answer");// TODO:
        }
        reportAnswerService.addReportAnswers(reportId, answerList, "cus_report_risk_answer");

        validateItem(itemList);// 校验指标数据
        if (CollectionUtils.isNotEmpty(itemList)) {
            reportAnswerService.deleteReportAnswer(reportId, "cus_report_risk_item");// TODO:
        }
        reportItemService.addReportItems(reportId, itemList, "cus_report_risk_item");

        // 第二步：分析数据并保存结果
        reportResultService.deleteReportResultByReportId("cus_report_risk_result", reportId);// TODO:
        riskAnalysisService.analysisRisk(reportId);
        // 第三步：生成PDF报告
        ReportCondition condition = new ReportCondition();
        condition.setReportId(reportId);
        getDiseaseRiskReport(condition);

        long processTime = System.currentTimeMillis() - starttime;
        System.out.println("运行时间" + processTime + "ms;");

        webResult.setSuc(reportId);
        // if (StringUtils.isNotBlank(resultMessage)) {
        // webResult.setResultCode(ResultCode.ERROR_90001);
        // webResult.setResultMessage(resultMessage);
        // }
        LOGGER.info(NetJsonUtils.objectToJson(data));
        return webResult;
    }

    /**
     * 校验客户信息
     * 
     * @author zcq
     * @param report
     * @return
     */
    private boolean validateCustomer(Report report) {
        report.setQuestionId("Q001");// 设置问卷编码

        if (StringUtils.isEmpty(report.getCustCode())) {
            throw new RestfulException(ResultCode.ERROR_20001);
        } else if (report.getCustCode().length() > 64) {
            throw new RestfulException(ResultCode.ERROR_20002);
        }

        if (StringUtils.isNotEmpty(report.getCustName()) && report.getCustName().length() > 80) {
            throw new RestfulException(ResultCode.ERROR_20003);
        }

        if (StringUtils.isEmpty(report.getCustSex())) {
            throw new RestfulException(ResultCode.ERROR_20004);
        } else if (!"male".equals(report.getCustSex()) && !"female".equals(report.getCustSex())) {
            throw new RestfulException(ResultCode.ERROR_20005);
        }

        if (report.getCustBirthday() == null) {
            throw new RestfulException(ResultCode.ERROR_20006);
        } else if (JodaTimeTools.compareDate(report.getCustBirthday(), new Date()) >= 0) {
            throw new RestfulException(ResultCode.ERROR_20007);
        }

        if (report.getCustHeight() == null || report.getCustHeight().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RestfulException(ResultCode.ERROR_20008);
        } else if (report.getCustHeight() != null && report.getCustHeight().compareTo(BigDecimal.valueOf(1000)) >= 0) {
            throw new RestfulException(ResultCode.ERROR_20009);
        }

        if (report.getCustWeight() == null || report.getCustWeight().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RestfulException(ResultCode.ERROR_20010);
        } else if (report.getCustWeight() != null && report.getCustWeight().compareTo(BigDecimal.valueOf(1000)) >= 0) {
            throw new RestfulException(ResultCode.ERROR_20011);
        }

        if (report.getCustWaistline() == null || report.getCustWaistline().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RestfulException(ResultCode.ERROR_20012);
        } else if (report.getCustWaistline() != null && report.getCustWaistline().compareTo(BigDecimal.valueOf(1000)) >= 0) {
            throw new RestfulException(ResultCode.ERROR_20013);
        }

        if (StringUtils.isEmpty(report.getCustPlevel())) {
            throw new RestfulException(ResultCode.ERROR_20014);
        } else if (!"st_light".equals(report.getCustPlevel()) && !"st_medium".equals(report.getCustPlevel())
                && !"st_weight".equals(report.getCustPlevel())) {
            throw new RestfulException(ResultCode.ERROR_20015);
        }

        // TODO:
        // if (StringUtils.isNotEmpty(report.getCustIcard())
        // && (report.getCustIcard().length() > 30 || !Pattern.matches("(^\\d{18}$)|(^\\d{15}$)",
        // report.getCustIcard()))) {
        // throw new RestfulException(ResultCode.ERROR_20016);
        // }

        return true;
    }

    /**
     * 校验问卷答案
     * 
     * @author zcq
     * @param answerList
     * @return
     */
    private boolean validateAnswer(List<ReportAnswerPojo> answerList) {
        Map<String, List<String>> map = questionService.getMasQuestionInfo();
        List<String> problemIdList = map.get("problemIdList");
        List<String> optionIdList = map.get("optionIdList");
        List<String> inputIdList = map.get("inputIdList");

        if (CollectionUtils.isNotEmpty(answerList) && CollectionUtils.isNotEmpty(problemIdList)
                && CollectionUtils.isNotEmpty(optionIdList)) {
            for (ReportAnswerPojo answerPojo : answerList) {
                answerPojo.setQuestionId("Q001");// 设置问卷编码

                if (StringUtils.isEmpty(answerPojo.getProblemId())) {
                    throw new RestfulException(ResultCode.ERROR_30001);
                } else if (!problemIdList.contains(answerPojo.getProblemId())) {
                    throw new RestfulException(ResultCode.ERROR_30002, new String[] {answerPojo.getProblemId()});
                }

                if (StringUtils.isEmpty(answerPojo.getProblemOptionId())) {
                    throw new RestfulException(ResultCode.ERROR_30003);
                } else if (!optionIdList.contains(answerPojo.getProblemOptionId())) {
                    throw new RestfulException(ResultCode.ERROR_30004, new String[] {answerPojo.getProblemOptionId()});
                }

                if (inputIdList.contains(answerPojo.getProblemOptionId())) {
                    if (StringUtils.isEmpty(answerPojo.getAnswerContent())) {
                        throw new RestfulException(ResultCode.ERROR_30005, new String[] {answerPojo.getProblemOptionId()});
                    } else if (!RiskConstant.isNumeric(answerPojo.getAnswerContent())) {
                        throw new RestfulException(ResultCode.ERROR_30006,
                                new String[] {answerPojo.getProblemOptionId(), answerPojo.getAnswerContent()});
                    }
                }
            }
        }
        return true;
    }

    /**
     * 校验指标
     * 
     * @author zcq
     * @param itemList
     * @return
     */
    private boolean validateItem(List<ReportItemPojo> itemList) {
        List<String> itemCodeList = itemService.getMasItemCodeList();
        if (CollectionUtils.isNotEmpty(itemCodeList) && CollectionUtils.isNotEmpty(itemList)) {
            for (ReportItemPojo itemPojo : itemList) {
                String itemCode = itemPojo.getItemCode();

                if (StringUtils.isEmpty(itemCode)) {
                    throw new RestfulException(ResultCode.ERROR_40001);
                } else if (!itemCodeList.contains(itemCode)) {
                    throw new RestfulException(ResultCode.ERROR_40002, new String[] {itemCode});
                }

                if (StringUtils.isEmpty(itemPojo.getItemValue())) {
                    throw new RestfulException(ResultCode.ERROR_40003, new String[] {itemCode});
                } else if (!RiskConstant.isNumeric(itemPojo.getItemValue())) {
                    throw new RestfulException(ResultCode.ERROR_40004, new String[] {itemCode, itemPojo.getItemValue()});
                } else if (Double.valueOf(itemPojo.getItemValue()) >= 100000000) {
                    throw new RestfulException(ResultCode.ERROR_40005, new String[] {itemCode, itemPojo.getItemValue()});
                }

                if ((itemPojo.getItemRefValMin() != null && itemPojo.getItemRefValMax() == null) ||
                        (itemPojo.getItemRefValMin() == null && itemPojo.getItemRefValMax() != null)) {
                    throw new RestfulException(ResultCode.ERROR_40006, new String[] {itemCode});
                }

                if (itemPojo.getItemRefValMin() != null
                        && (itemPojo.getItemRefValMin().compareTo(BigDecimal.valueOf(100000000)) == 1
                        || itemPojo.getItemRefValMin().compareTo(BigDecimal.valueOf(100000000)) == 0)) {
                    throw new RestfulException(ResultCode.ERROR_40007, new String[] {itemCode,
                            itemPojo.getItemRefValMin().toString()});
                }

                if (itemPojo.getItemRefValMax() != null
                        && (itemPojo.getItemRefValMax().compareTo(BigDecimal.valueOf(100000000)) == 1
                        || itemPojo.getItemRefValMax().compareTo(BigDecimal.valueOf(100000000)) == 0)) {
                    throw new RestfulException(ResultCode.ERROR_40008, new String[] {itemCode,
                            itemPojo.getItemRefValMax().toString()});
                }

                if (itemPojo.getItemRefValMin() != null && itemPojo.getItemRefValMax() != null
                        && itemPojo.getItemRefValMin().compareTo(itemPojo.getItemRefValMax()) == 1) {
                    throw new RestfulException(ResultCode.ERROR_40009, new String[] {itemCode});
                }

                if (StringUtils.isNotEmpty(itemPojo.getItemName()) && itemPojo.getItemName().length() > 100) {
                    throw new RestfulException(ResultCode.ERROR_40010, new String[] {itemCode, itemPojo.getItemName()});
                }

                if (StringUtils.isNotEmpty(itemPojo.getItemInsName()) && itemPojo.getItemInsName().length() > 100) {
                    throw new RestfulException(ResultCode.ERROR_40011, new String[] {itemCode, itemPojo.getItemInsName()});
                }

                if (StringUtils.isNotEmpty(itemPojo.getItemRefStr()) && itemPojo.getItemRefStr().length() > 200) {
                    throw new RestfulException(ResultCode.ERROR_40012, new String[] {itemCode, itemPojo.getItemRefStr()});
                }

                if (StringUtils.isNotEmpty(itemPojo.getItemUnit()) && itemPojo.getItemUnit().length() > 30) {
                    throw new RestfulException(ResultCode.ERROR_40013, new String[] {itemCode, itemPojo.getItemUnit()});
                }

                if (StringUtils.isNotEmpty(itemPojo.getItemValueType()) && itemPojo.getItemValueType().length() > 30) {
                    throw new RestfulException(ResultCode.ERROR_40014, new String[] {itemCode, itemPojo.getItemValueType()});
                }

                if (StringUtils.isNotEmpty(itemPojo.getItemResult()) && itemPojo.getItemResult().length() > 30) {
                    throw new RestfulException(ResultCode.ERROR_40015, new String[] {itemCode, itemPojo.getItemResult()});
                }
            }
        }
        return true;
    }

    /**
     * 条件检索报告
     * 
     * @author zcq
     * @param data
     * @return
     */
    @RequestMapping(value = "/rest/queryReportData", method = RequestMethod.POST)
    public WebResult<List<QuestionReport>> queryReport(@RequestBody QueryReportData data) {
        // int days = JodaTimeTools.getDays(new Date(), JodaTimeTools.toDate(limiteDate));
        // String resultMessage = "";
        // if (days <= 15 && days >= 0) {
        // resultMessage = this.getMessage(ResultCode.ERROR_90001, new String[] {days + ""});
        // }

        // 校验检索条件
        validateItem(data);
        WebResult<List<QuestionReport>> webResult = new WebResult<List<QuestionReport>>();
        ReportCondition condition = new ReportCondition();
        condition.setCustCodeList(data.getCustCodeList());
        condition.setReportDate(data.getReportDate());
        List<ReportPojo> reportList = reportService.queryReport(condition);
        List<QuestionReport> list = new ArrayList<QuestionReport>();

        String relative_path = this.readProperties().getProperty("resource.report.path");
        if (CollectionUtils.isNotEmpty(reportList)) {
            for (ReportPojo report : reportList) {
                QuestionReport result = new QuestionReport();
                result.setCustCode(report.getCustCode());
                result.setReportDate(JodaTimeTools.toString(report.getReportDate(), JodaTimeTools.FORMAT_6));
                result.setReportId(report.getReportId());
                result.setReportPdfPath(relative_path + report.getReportPdfPath());
                list.add(result);
            }
        }
        webResult.setSuc(list);
        // if (StringUtils.isNotBlank(resultMessage)) {
        // webResult.setResultCode(ResultCode.ERROR_90001);
        // webResult.setResultMessage(resultMessage);
        // }
        return webResult;
    }

    /**
     * 校验检索条件
     * 
     * @author zcq
     * @param data
     * @return
     */
    private boolean validateItem(QueryReportData data) {
        List<String> codeList = data.getCustCodeList();
        String reportDate = data.getReportDate();
        if (CollectionUtils.isEmpty(codeList) && StringUtils.isBlank(reportDate)) {
            throw new RestfulException(ResultCode.ERROR_50001);
        }
        if (StringUtils.isNotBlank(reportDate)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                format.setLenient(false);
                format.parse(reportDate);
            } catch (ParseException e) {
                throw new RestfulException(ResultCode.ERROR_50002, new String[] {reportDate});
            }
        }
        return true;
    }

    /**
     * 生成PDF报告
     * 
     * @author zcq
     * @param reportId
     * @return
     */
    @RequestMapping(value = "/rest/getReport", method = RequestMethod.POST)
    public String getDiseaseRiskReport(@RequestBody ReportCondition condition) {
        if (condition == null || StringUtils.isEmpty(condition.getReportId())) {
            throw new RestfulException(ResultCode.ERROR_80000);
        }
        String relative_path = this.readProperties().getProperty("resource.report.path");

        // TODO:
        if (MapUtils.isEmpty(reportResultService.queryReportResultMap("cus_report_risk_result", condition.getReportId()))) {
            throw new RestfulException(ResultCode.ERROR_10002);
        }

        // 第一步：实例化对象
        DiseaseRiskPdf riskPdf = new DiseaseRiskPdf(){

            @Override
            public DiseaseRiskPojo beforeCreatePdf(ReportForm form) {
                DiseaseRiskPojo riskPojo = new DiseaseRiskPojo();
                // 获取分析结果信息
                String reportId = form.getReportCode();
                riskPojo.setCustomer(reportService.getReport(reportId));
                riskPojo.setDiseaseMap(diseaseService.queryDiseaseMap(null));
                riskPojo.setItemMap(itemService.getMasItemMap(riskPojo.getCustomer().getCustSex()));
                riskPojo.setResultMap(reportResultService.queryReportResultMap("cus_report_risk_result", reportId));
                int custAge = JodaTimeTools.getYears(riskPojo.getCustomer().getCustBirthday(), new Date());
                if (custAge <= 18) {
                    riskPojo.setSportAdviceList(RiskConstant.sportAdviceMap.get("1"));
                } else if (custAge > 18 && custAge <= 60) {
                    riskPojo.setSportAdviceList(RiskConstant.sportAdviceMap.get("2"));
                } else {
                    riskPojo.setSportAdviceList(RiskConstant.sportAdviceMap.get("3"));
                }

                return riskPojo;
            }

            @Override
            public void afterCreatePdf(ReportForm reportForm) {
                Report report = new Report();
                report.setReportId(reportForm.getReportCode());
                report.setReportPdfPath(reportForm.getReportPath());
                reportService.updateReport(report);
            }
        };
        // 第二步：创建PDF
        String dietPdfpath = riskPdf.create(condition.getReportId());

        return relative_path + dietPdfpath;
    }

    /**
     * 生成测试数据
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = "/getTestData", method = RequestMethod.POST)
    public WebResult<RiskAnalysisData> getTestData() {
        WebResult<RiskAnalysisData> webResult = new WebResult<RiskAnalysisData>();
        RiskAnalysisData data = new RiskAnalysisData();

        String reportId = "402880e860fd87060160fd8e80e4000a";
        data.setCustomer(reportService.getReport(reportId));

        ReportAnswerCondition condition1 = new ReportAnswerCondition();
        condition1.setReportId(reportId);
        condition1.setTableName("cus_report_risk_answer");
        data.setQuestionAnswerList(reportAnswerService.queryReportAnswer(condition1));

        ReportItemCondition condition2 = new ReportItemCondition();
        condition2.setReportId(reportId);
        condition2.setTableName("cus_report_risk_item");
        data.setExamItemList(reportItemService.queryReportItem(condition2));

        return webResult.setSuc(data);
    }

}
