
package com.qa.examitem.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.utils.times.JodaTimeTools;
import com.qa.examitem.condition.ReportAnswerCondition;
import com.qa.examitem.condition.ReportItemCondition;
import com.qa.examitem.constants.RiskConstant;
import com.qa.examitem.dao.ReportAnswerDAO;
import com.qa.examitem.dao.ReportDAO;
import com.qa.examitem.dao.ReportItemDAO;
import com.qa.examitem.dao.ReportResultDAO;
import com.qa.examitem.entity.Report;
import com.qa.examitem.pojo.ReportAnswerPojo;
import com.qa.examitem.pojo.ReportItemPojo;
import com.qa.examitem.pojo.ReportPojo;
import com.qa.examitem.pojo.ReportResultPojo;
import com.qa.main.service.BaseService;
import com.qa.master.dao.AdviceDAO;
import com.qa.master.dao.DiseaseDAO;
import com.qa.master.dao.DiseaseItemDAO;
import com.qa.master.dao.DiseaseOptionDAO;
import com.qa.master.dao.ItemDAO;
import com.qa.master.dao.QuestionDAO;
import com.qa.master.pojo.DiseaseOptionPojo;
import com.qa.master.pojo.DiseasePojo;
import com.qa.master.pojo.ItemPojo;
import com.qa.master.pojo.QuestionProblemOptionPojo;

/**
 * 危险分层分析
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-25 zcq 初版
 */
@Service
public class RiskAnalysisServiceImpl extends BaseService implements RiskAnalysisService {

    @Resource
    private ReportDAO reportDAO;

    @Resource
    private ReportAnswerDAO reportAnswerDAO;

    @Resource
    private ReportItemDAO reportItemDAO;

    @Resource
    private ReportResultDAO reportResultDAO;

    @Resource
    private DiseaseDAO diseaseDAO;

    @Resource
    private DiseaseOptionDAO diseaseOptionDAO;

    @Resource
    private DiseaseItemDAO diseaseItemDAO;

    @Resource
    private ItemDAO itemDAO;

    @Resource
    private AdviceDAO adviceDAO;

    @Resource
    private QuestionDAO questionDAO;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void analysisRisk(String reportId) {
        Map<String, Object> dataMap = new HashMap<String, Object>();// 准备数据
        // 第一步：获取原始数据
        // 客户基本信息
        ReportPojo customer = reportDAO.getTransformerBean(reportId, Report.class, ReportPojo.class);
        if (customer.getCustBirthday() != null) {
            customer.setCustAge(JodaTimeTools.getYears(customer.getCustBirthday(), new Date()));
        }
        if (customer.getCustWeight() != null && customer.getCustHeight() != null) {
            customer.setBmi(RiskConstant.getBmi(customer.getCustWeight(), customer.getCustHeight()));
            String level = "st_light";
            if (StringUtils.isNotBlank(customer.getCustPlevel())) {
                level = customer.getCustPlevel();
            }
            customer.setNormalEnergy(RiskConstant.getNormalEnery(customer.getCustWeight(), customer.getCustHeight(),
                    level, customer.getCustSex()).intValue());
        }
        dataMap.put("answer_customer", customer);

        // 问卷答案信息
        ReportAnswerCondition answerCondition = new ReportAnswerCondition();
        answerCondition.setTableName("cus_report_risk_answer");
        answerCondition.setReportId(reportId);
        List<ReportAnswerPojo> answerList = reportAnswerDAO.queryReportAnswer(answerCondition);
        List<String> optionIdList = new ArrayList<String>();
        Map<String, ReportAnswerPojo> raMap = new HashMap<String, ReportAnswerPojo>();
        if (CollectionUtils.isNotEmpty(answerList)) {
            for (ReportAnswerPojo answerPojo : answerList) {
                String optionId = answerPojo.getProblemOptionId();
                String answerContent = answerPojo.getAnswerContent();
                // 记录所有选项
                optionIdList.add(optionId);
                // 记录填空
                if (StringUtils.isNotBlank(answerContent)) {
                    raMap.put(optionId, answerPojo);
                }
            }
        }
        dataMap.put("answer_option_all", optionIdList);
        dataMap.put("answer_option_input", raMap);

        // 问卷指标信息
        // 指标基础数据
        Map<String, ItemPojo> masItemMap = itemDAO.getMasItemMap("masItemMap").get(customer.getCustSex());
        // 问卷指标数据
        ReportItemCondition reportItemCondition = new ReportItemCondition();
        reportItemCondition.setReportId(reportId);
        reportItemCondition.setTableName("cus_report_risk_item");
        List<ReportItemPojo> itemList = reportItemDAO.queryReportItem(reportItemCondition);
        Map<String, BigDecimal> resultItemMap = new HashMap<String, BigDecimal>();
        if (CollectionUtils.isNotEmpty(itemList)) {
            for (ReportItemPojo itemPojo : itemList) {
                String itemInsName = itemPojo.getItemInsName();
                String itemCode = itemPojo.getItemCode();
                BigDecimal refValueMin = itemPojo.getItemRefValMin();
                BigDecimal refValueMax = itemPojo.getItemRefValMax();
                // 设置名称
                if (StringUtils.isNotBlank(itemInsName)) {
                    masItemMap.get(itemCode).setItemName(itemInsName);
                }
                // 设置参考值
                if (StringUtils.isNotBlank(itemPojo.getItemValue()) && RiskConstant.isNumeric(itemPojo.getItemValue())) {
                    BigDecimal resultValue = BigDecimal.valueOf(Double.valueOf(itemPojo.getItemValue()));
                    if (refValueMin != null && masItemMap.get(itemCode) != null) {
                        masItemMap.get(itemCode).setItemRefValMin(refValueMin);
                    }
                    if (refValueMax != null && masItemMap.get(itemCode) != null) {
                        masItemMap.get(itemCode).setItemRefValMax(refValueMax);
                    }
                    resultItemMap.put(itemCode, resultValue);
                }
            }
        }
        dataMap.put("mas_item", masItemMap);
        dataMap.put("answer_item", resultItemMap);

        // 疾病选项基础数据
        Map<String, Object> masOptionMap = diseaseOptionDAO.getMasOptionMap("diseaseOptionMap");
        dataMap.put("mas_option_choice", masOptionMap.get("mas_option_choice"));
        dataMap.put("mas_option_input", masOptionMap.get("mas_option_input"));

        // 疾病信息
        Map<String, DiseasePojo> masDiseaseMap = diseaseDAO.queryDiseaseAll("mas_disease");
        dataMap.put("mas_disease", masDiseaseMap);

        // 第二步：分析保存结果
        Map<String, ReportResultPojo> resultMap = new HashMap<String, ReportResultPojo>();
        riskAssessment(dataMap, resultMap);// 风险评估
        lifeStyleAnalysis(resultMap);// 生活方式分析（包含：饮食建议 + 运动建议）
        healthSummary(dataMap, resultMap);// 健康汇总

        if (resultMap != null && !CollectionUtils.sizeIsEmpty(resultMap)) {
            for (String key : resultMap.keySet()) {
                ReportResultPojo result = resultMap.get(key);
                result.setReportId(reportId);
                reportResultDAO.addReportResult(result, "cus_report_risk_result", "0");
            }
        }
    }

    /**
     * 疾病风险评估
     * 
     * @author zcq
     * @param dataMap
     * @param resultMap
     */
    @SuppressWarnings("unchecked")
    private void riskAssessment(Map<String, Object> dataMap, Map<String, ReportResultPojo> resultMap) {
        // 客户信息
        ReportPojo customer = (ReportPojo) dataMap.get("answer_customer");
        String custSex = customer.getCustSex();
        BigDecimal custWaistline = customer.getCustWaistline();
        Double bmi = customer.getBmi();
        Integer energy = customer.getNormalEnergy();

        // 设置推荐热量
        ReportResultPojo energyResult = new ReportResultPojo();
        energyResult.setItemCode("normal_energy");
        energyResult.setItemName("推荐热量");
        energyResult.setItemValue((energy == null) ? "" : energy.toString());
        resultMap.put("normal_energy", energyResult);

        dataMap.put("risk_factor", new ArrayList<String>());
        Double optionScore = 0.0;
        // ------------------------- 高血压 --------------------------
        // 高血压--设置评估（问卷答案得分、问卷指标得分、风险等级、风险因素）
        analysisDiseaseRisk("D0001", optionScore, dataMap, resultMap);

        // ------------------------- 糖尿病 --------------------------
        optionScore = 0.0;
        dataMap.put("risk_factor", new ArrayList<String>());
        // 糖尿病--设置评估--基础信息（BMI）
        if (bmi != null) {
            optionScore += RiskConstant.getBmiScore(bmi);
            if (bmi >= 24) {
                String tangRisk = RiskConstant.getBmiRiskFactor(bmi);
                if (StringUtils.isNotBlank(tangRisk)) {
                    ((List<String>) dataMap.get("risk_factor")).add(tangRisk);
                }
            }
        }
        // 糖尿病--设置评估（问卷答案得分、问卷指标得分、风险等级、风险因素）
        analysisDiseaseRisk("D0002", optionScore, dataMap, resultMap);

        // ------------------------- 骨质疏松 --------------------------
        optionScore = 0.0;
        // 骨质疏松--设置评估--基础信息（年龄、性别）
        if (customer.getCustAge() != null && customer.getCustAge() >= 50) {
            optionScore += 1;
            ((List<String>) dataMap.get("risk_factor")).add("年龄（≥50岁）");
        }
        if (StringUtils.isNotBlank(custSex) && "female".equals(custSex)) {
            optionScore += 1;
            ((List<String>) dataMap.get("risk_factor")).add("性别（女）");
        }
        if (bmi != null) {
            optionScore += RiskConstant.getBmiScore(bmi);
            if (bmi < 18.5) {
                String tangRisk = RiskConstant.getBmiRiskFactor(bmi);
                if (StringUtils.isNotBlank(tangRisk)) {
                    ((List<String>) dataMap.get("risk_factor")).add(tangRisk);
                }
            }
        }
        // 骨质疏松--设置评估（问卷答案得分、问卷指标得分、风险等级、风险因素）
        analysisDiseaseRisk("D0003", optionScore, dataMap, resultMap);

        // ------------------------- 肥胖 --------------------------
        optionScore = 0.0;
        // 肥胖--设置评估--基础信息（BMI）
        if (bmi != null) {
            optionScore += bmi.doubleValue();
        }
        // 肥胖--设置评估（问卷答案得分、问卷指标得分、风险等级、风险因素）
        analysisDiseaseRisk("D0004", optionScore, dataMap, resultMap);

        // ------------------------- 痛风 --------------------------
        optionScore = 0.0;
        // 痛风--设置评估（问卷答案得分、问卷指标得分、风险等级、风险因素）
        analysisDiseaseRisk("D0005", optionScore, dataMap, resultMap);

        // ------------------------- 冠心病 --------------------------
        optionScore = 0.0;
        // 冠心病--设置评估--基础信息（年龄、性别、BMI）
        if (customer.getCustAge() != null && customer.getCustAge() >= 45) {
            optionScore += 1;
            ((List<String>) dataMap.get("risk_factor")).add("年龄（≥45）");
        }
        if (StringUtils.isNotBlank(custSex) && "male".equals(custSex)) {
            optionScore += 1;
            ((List<String>) dataMap.get("risk_factor")).add("性别（男）");
        }
        if (bmi != null) {
            optionScore += RiskConstant.getBmiScore(bmi);
            if (bmi >= 24) {
                String tangRisk = RiskConstant.getBmiRiskFactor(bmi);
                if (StringUtils.isNotBlank(tangRisk)) {
                    ((List<String>) dataMap.get("risk_factor")).add(tangRisk);
                }
            }
        }
        // 冠心病--设置评估（问卷答案得分、问卷指标得分、风险等级、风险因素）
        analysisDiseaseRisk("D0006", optionScore, dataMap, resultMap);

        // ------------------------- 脑卒中 --------------------------
        optionScore = 0.0;
        // 脑卒中--设置评估--基础信息（年龄、性别、BMI）
        if (customer.getCustAge() != null && customer.getCustAge() >= 45) {
            optionScore += 1;
            ((List<String>) dataMap.get("risk_factor")).add("年龄（≥45）");
        }
        if (StringUtils.isNotBlank(custSex) && "male".equals(custSex)) {
            optionScore += 1;
            ((List<String>) dataMap.get("risk_factor")).add("性别（男）");
        }
        if (bmi != null) {
            optionScore += RiskConstant.getBmiScore(bmi);
            if (bmi >= 24) {
                String tangRisk = RiskConstant.getBmiRiskFactor(bmi);
                if (StringUtils.isNotBlank(tangRisk)) {
                    ((List<String>) dataMap.get("risk_factor")).add(tangRisk);
                }
            }
        }
        // 脑卒中--设置评估（问卷答案得分、问卷指标得分、风险等级、风险因素）
        analysisDiseaseRisk("D0007", optionScore, dataMap, resultMap);

        // ------------------------- 乳腺癌 --------------------------
        optionScore = 0.0;
        // 肥胖--设置评估（问卷答案得分、问卷指标得分、风险等级、风险因素）
        analysisDiseaseRisk("D0008", optionScore, dataMap, resultMap);

        // ------------------------- 代谢综合征 --------------------------
        optionScore = 0.0;
        // 代谢综合征--设置评估--基础信息（腰围）
        if (custWaistline != null && StringUtils.isNotBlank(custSex)) {
            String waistlineFactor = RiskConstant.getWaistlineRiskFactor(custSex, custWaistline.doubleValue());
            if (StringUtils.isNotBlank(waistlineFactor)) {
                ((List<String>) dataMap.get("risk_factor")).add(waistlineFactor);
            }
        }
        // 代谢综合征--设置评估（问卷答案得分、问卷指标得分、风险等级、风险因素）
        analysisDiseaseRisk("D0009", optionScore, dataMap, resultMap);

        // ------------------------- 饮食评估 --------------------------
        optionScore = 0.0;
        analysisDiseaseRisk("diet_advice", optionScore, dataMap, resultMap);

        // ------------------------- 运动评估 --------------------------
        optionScore = 0.0;
        analysisDiseaseRisk("sport_advice", optionScore, dataMap, resultMap);

        // ------------------------- 饮酒评估 --------------------------
        optionScore = 0.0;
        analysisDiseaseRisk("drink_advice", optionScore, dataMap, resultMap);

        // ------------------------- 吸烟评估 --------------------------
        optionScore = 0.0;
        analysisDiseaseRisk("smoke_advice", optionScore, dataMap, resultMap);

        // ------------------------- 睡眠评估 --------------------------
        optionScore = 0.0;
        analysisDiseaseRisk("sleep_advice", optionScore, dataMap, resultMap);

        // ------------------------- 压力评估 --------------------------
        optionScore = 0.0;
        analysisDiseaseRisk("press_advice", optionScore, dataMap, resultMap);

        // ------------------------- 环境评估 --------------------------
        optionScore = 0.0;
        analysisDiseaseRisk("envir_advice", optionScore, dataMap, resultMap);

    }

    /**
     * 计算疾病风险分层
     * 
     * @author zcq
     * @param diseaseCode
     * @param optionScore
     * @param masMap
     * @param answerMap
     * @param resultList
     */
    @SuppressWarnings("unchecked")
    private void analysisDiseaseRisk(String diseaseCode, Double optionScore, Map<String, Object> dataMap,
            Map<String, ReportResultPojo> resultMap) {
        // --------------------------------- 问卷数据 -----------------------------------------
        // 所有问卷选项
        List<String> optionIdList = (List<String>) dataMap.get("answer_option_all");
        // 填空题答案
        Map<String, ReportAnswerPojo> answerInputMap = (Map<String, ReportAnswerPojo>) dataMap
                .get("answer_option_input");
        // 问卷指标答案
        Map<String, BigDecimal> resultItemMap = (Map<String, BigDecimal>) dataMap.get("answer_item");

        // --------------------------------- 基础数据 -----------------------------------------
        // 基础数据--选项
        Map<String, List<String>> choiceMap = (Map<String, List<String>>) dataMap.get("mas_option_choice");
        // 基础数据--填空
        Map<String, List<DiseaseOptionPojo>> inputMap = (Map<String, List<DiseaseOptionPojo>>) dataMap
                .get("mas_option_input");
        // 基础数据--指标
        Map<String, ItemPojo> masItemMap = (Map<String, ItemPojo>) dataMap.get("mas_item");
        // 基础数据--疾病信息
        Map<String, DiseasePojo> masDiseaseMap = (Map<String, DiseasePojo>) dataMap.get("mas_disease");

        // --------------------------------- 危险因素 -----------------------------------------
        // 危险因素--基础信息
        List<String> riskFactorList = (List<String>) dataMap.get("risk_factor");
        if (riskFactorList == null) {
            riskFactorList = new ArrayList<String>();
        }

        // --------------------------------- 数据分析 -----------------------------------------
        // 疾病--设置评估结果--问卷答案
        // 基础数据--选择题部分
        List<String> diseaseOptionList = choiceMap.get(diseaseCode);
        // 基础数据--填空题部分
        List<DiseaseOptionPojo> inputList = inputMap.get(diseaseCode);
        if (CollectionUtils.isNotEmpty(inputList) && answerInputMap != null
                && !CollectionUtils.sizeIsEmpty(answerInputMap)) {
            Map<String, DiseaseOptionPojo> masInputMap = new HashMap<String, DiseaseOptionPojo>();
            for (DiseaseOptionPojo opt : inputList) {
                masInputMap.put(opt.getOptionId(), opt);
            }
            List<String> inputCodeList = RiskConstant.inputCompare(masInputMap, answerInputMap);
            diseaseOptionList.addAll(inputCodeList);
        }
        diseaseOptionList.retainAll(optionIdList);
        if (CollectionUtils.isNotEmpty(diseaseOptionList)) {
            optionScore += diseaseOptionDAO.calculateDiseaseScore(diseaseCode, diseaseOptionList);
        }
        // 疾病--设置评估结果--问卷指标
        int itemScore = 0;
        List<String> itemCodeList = RiskConstant.compareExamItem(masItemMap, resultItemMap);
        itemScore += diseaseItemDAO.calculateDiseaseItemScore(diseaseCode, itemCodeList);

        // 分析数据并记录结果
        String riskLevel = "";
        ReportResultPojo result = new ReportResultPojo();
        result.setItemCode(diseaseCode);// 记录项目编码
        result.setItemScore(optionScore.intValue());// 记录项目得分
        if (masDiseaseMap.get(diseaseCode) != null
                && StringUtils.isNotBlank(masDiseaseMap.get(diseaseCode).getDiseaseName())) {
            result.setItemName(masDiseaseMap.get(diseaseCode).getDiseaseName());
        }
        if (!"D0009".equals(diseaseCode)) {
            // 疾病--设置评估结果--风险等级
            riskLevel = RiskConstant.getDiseaseLevel(diseaseCode, optionScore, itemScore);
            // 疾病--设置风险因素（问卷答案）
            if (CollectionUtils.isNotEmpty(diseaseOptionList)) {
                riskFactorList.addAll(diseaseOptionDAO.calculateDiseaseRiskFactor(diseaseCode, diseaseOptionList));
            }
        } else {
            // 疾病--设置风险因素--特殊处理（代谢综合征）
            if ("D0009".equals(diseaseCode)) {
                List<Object[]> itemFactorList = diseaseItemDAO
                        .calculateDiseaseItemRiskFactor(diseaseCode, itemCodeList);
                if (CollectionUtils.isNotEmpty(itemFactorList)) {
                    for (Object[] obj : itemFactorList) {
                        String code = obj[0].toString();
                        String factor = obj[1].toString();
                        if (StringUtils.isNotBlank(factor) && !riskFactorList.contains(factor)) {
                            riskFactorList.add(factor);
                        } else if (StringUtils.isNotBlank(code) && masItemMap.get(code) != null
                                && !riskFactorList.contains(masItemMap.get(code).getItemName())) {
                            riskFactorList.add(masItemMap.get(code).getItemName());
                        }
                    }
                }
                // 疾病--设置评估结果--风险等级
                riskLevel = RiskConstant.getDiseaseLevel(diseaseCode, Double.valueOf(riskFactorList.size()), 0);
            }
        }
        result.setItemResult(riskLevel);// 记录风险等级
        result.setItemValue(StringUtils.join(riskFactorList, "、"));// 记录风险因素

        resultMap.put(diseaseCode, result);

        // 清空
        dataMap.put("risk_factor", new ArrayList<String>());
    }

    /**
     * 生活方式评估
     * 
     * @author zcq
     * @param resultMap
     */
    private void lifeStyleAnalysis(Map<String, ReportResultPojo> resultMap) {
        // 生活方式建议
        Map<String, String> lifeAdviceMap = adviceDAO.getLifeAdviceMap("lifeAdviceMap");
        if (lifeAdviceMap != null && !CollectionUtils.sizeIsEmpty(lifeAdviceMap)) {
            // 设置饮食建议
            if (resultMap.get("diet_advice") != null) {
                String dietAdvice = lifeAdviceMap.get("diet_advice_" + resultMap.get("diet_advice").getItemScore());
                resultMap.get("diet_advice").setItemAdvice(dietAdvice);
            }
            // 设置运动建议
            if (resultMap.get("sport_advice") != null) {
                String sportAdvice = lifeAdviceMap.get("sport_advice_" + resultMap.get("sport_advice").getItemScore());
                resultMap.get("sport_advice").setItemAdvice(sportAdvice);
            }
            // 设置饮酒建议
            if (resultMap.get("drink_advice") != null) {
                String drinkAdvice = lifeAdviceMap.get("drink_advice_" + resultMap.get("drink_advice").getItemScore());
                resultMap.get("drink_advice").setItemAdvice(drinkAdvice);
            }
            // 设置吸烟建议
            if (resultMap.get("smoke_advice") != null) {
                String smokeAdvice = lifeAdviceMap.get("smoke_advice_" + resultMap.get("smoke_advice").getItemScore());
                resultMap.get("smoke_advice").setItemAdvice(smokeAdvice);
            }
            // 设置睡眠建议
            if (resultMap.get("sleep_advice") != null) {
                String sleepAdvice = lifeAdviceMap.get("sleep_advice_" + resultMap.get("sleep_advice").getItemScore());
                resultMap.get("sleep_advice").setItemAdvice(sleepAdvice);
            }
            // 设置压力建议
            if (resultMap.get("press_advice") != null) {
                String pressAdvice = lifeAdviceMap.get("press_advice_" + resultMap.get("press_advice").getItemScore());
                resultMap.get("press_advice").setItemAdvice(pressAdvice);
            }
            // 设置环境建议
            if (resultMap.get("envir_advice") != null) {
                Integer envirScore = resultMap.get("envir_advice").getItemScore();
                if (envirScore >= 2) {
                    String envirAdvice = lifeAdviceMap.get("envir_advice_2");
                    resultMap.get("envir_advice").setItemAdvice(envirAdvice);
                } else {
                    String envirAdvice = lifeAdviceMap.get("envir_advice_" + envirScore);
                    resultMap.get("envir_advice").setItemAdvice(envirAdvice);
                }
            }
        }
    }

    /**
     * 健康报告汇总
     * 
     * @author zcq
     * @param dataMap
     * @param resultMap
     */
    @SuppressWarnings("unchecked")
    private void healthSummary(Map<String, Object> dataMap, Map<String, ReportResultPojo> resultMap) {
        // 设置重要指标
        Map<String, ItemPojo> masItemMap = (Map<String, ItemPojo>) dataMap.get("mas_item");
        Map<String, BigDecimal> resultItemMap = (Map<String, BigDecimal>) dataMap.get("answer_item");

        if (resultItemMap != null && !CollectionUtils.sizeIsEmpty(resultItemMap)) {
            for (String key : resultItemMap.keySet()) {
                RiskConstant.setReportResult(masItemMap.get(key), resultItemMap.get(key), resultMap);
            }
        }

        // 设置健康史
        Map<String, ReportAnswerPojo> inputMap = (Map<String, ReportAnswerPojo>) dataMap.get("answer_option_input");
        List<String> optionIdList = (List<String>) dataMap.get("answer_option_all");

        // 既往病史
        Map<String, String> diseaseHistoryMap = new HashMap<String, String>();
        List<QuestionProblemOptionPojo> diseaseOptionList = questionDAO.queryOptionByProblemId("Q001P001001");
        RiskConstant.setHistoryProblemOptionMap(diseaseOptionList, diseaseHistoryMap);
        List<String> diseaseHistoryList = RiskConstant.setDiseaseHistory(diseaseHistoryMap, inputMap, optionIdList);

        ReportResultPojo diseaseResult = new ReportResultPojo();
        diseaseResult.setItemCode("disease_history");
        diseaseResult.setItemName("既往病史");
        diseaseResult.setItemValue("无");
        if (StringUtils.isNotBlank(StringUtils.join(diseaseHistoryList, "、"))) {
            diseaseResult.setItemValue(StringUtils.join(diseaseHistoryList, "、"));
        }
        resultMap.put("disease_history", diseaseResult);

        // 家族史
        Map<String, String> familyHistoryMap = new HashMap<String, String>();
        List<QuestionProblemOptionPojo> familyOptionList = questionDAO.queryOptionByProblemId("Q001P002001");
        RiskConstant.setHistoryProblemOptionMap(familyOptionList, familyHistoryMap);
        List<QuestionProblemOptionPojo> cancerOptionList = questionDAO.queryOptionByProblemId("Q001P002001001");
        RiskConstant.setHistoryProblemOptionMap(cancerOptionList, familyHistoryMap);
        List<String> familyHistoryList = RiskConstant.setDiseaseHistory(familyHistoryMap, inputMap, optionIdList);

        ReportResultPojo familyResult = new ReportResultPojo();
        familyResult.setItemCode("family_history");
        familyResult.setItemName("家族史");
        familyResult.setItemValue("无");
        if (StringUtils.isNotBlank(StringUtils.join(familyHistoryList, "、"))) {
            familyResult.setItemValue(StringUtils.join(familyHistoryList, "、"));
        }
        resultMap.put("family_history", familyResult);

        // 用药史
        Map<String, String> medicineHistoryMap = new HashMap<String, String>();
        List<QuestionProblemOptionPojo> medicineOptionList = questionDAO.queryOptionByProblemId("Q001P003001");
        RiskConstant.setHistoryProblemOptionMap(medicineOptionList, medicineHistoryMap);
        List<String> medicineHistoryList = RiskConstant.setDiseaseHistory(medicineHistoryMap, inputMap, optionIdList);

        ReportResultPojo medicineResult = new ReportResultPojo();
        medicineResult.setItemCode("medicine_history");
        medicineResult.setItemName("用药史");
        medicineResult.setItemValue("无");
        if (StringUtils.isNotBlank(StringUtils.join(medicineHistoryList, "、"))) {
            medicineResult.setItemValue(StringUtils.join(medicineHistoryList, "、"));
        }
        resultMap.put("medicine_history", medicineResult);
    }
}
