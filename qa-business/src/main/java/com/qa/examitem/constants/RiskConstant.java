
package com.qa.examitem.constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.qa.examitem.pojo.ReportAnswerPojo;
import com.qa.examitem.pojo.ReportResultPojo;
import com.qa.master.pojo.DiseaseOptionPojo;
import com.qa.master.pojo.ItemPojo;
import com.qa.master.pojo.QuestionProblemOptionPojo;

/**
 * 危险分层分析
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-7 zcq 初版
 */
public class RiskConstant {

    /**
     * 计算疾病风险等级
     * 
     * @author zcq
     * @param diseaseCode
     * @param score
     * @param itemScore
     * @return
     */
    public static String getDiseaseLevel(String diseaseCode, Double score, Integer itemScore) {
        String resultLevel = "低风险";
        if ("D0001".equals(diseaseCode)) {// 高血压
            if (score >= 0 && score <= 3) {
                resultLevel = "低风险";
            } else if (score >= 4 && score <= 6) {
                resultLevel = "中度风险";
            } else if (score >= 7) {
                resultLevel = "高风险";
            }
            resultLevel = getAddItemLevel(resultLevel, itemScore);
        } else if ("D0002".equals(diseaseCode)) {// 糖尿病
            if (score >= 0 && score <= 4) {
                resultLevel = "低风险";
            } else if (score >= 5 && score <= 8) {
                resultLevel = "中度风险";
            } else if (score >= 9) {
                resultLevel = "高风险";
            }
            resultLevel = getAddItemLevel(resultLevel, itemScore);
        } else if ("D0003".equals(diseaseCode)) {// 骨质疏松
            if (score >= 0 && score <= 4) {
                resultLevel = "低风险";
            } else if (score >= 5 && score <= 8) {
                resultLevel = "中度风险";
            } else if (score >= 9) {
                resultLevel = "高风险";
            }
            resultLevel = getAddItemLevel(resultLevel, itemScore);
        } else if ("D0004".equals(diseaseCode)) {// 肥胖
            if (score < 18.5) {
                resultLevel = "低体重";
            } else if (score >= 18.5 && score < 24) {
                resultLevel = "正常";
            } else if (score >= 24 && score < 28) {
                resultLevel = "超重";
            } else if (score >= 28) {
                resultLevel = "超重";
            } else {
                resultLevel = "无";
            }
            resultLevel = getAddItemLevel(resultLevel, itemScore);
        } else if ("D0005".equals(diseaseCode)) {// 痛风
            if (score == 0) {
                resultLevel = "低风险";
            } else if (score == 1) {
                resultLevel = "中度风险";
            }
            resultLevel = getAddItemLevel(resultLevel, itemScore);
        } else if ("D0006".equals(diseaseCode)) {// 冠心病
            if (score >= 0 && score <= 5) {
                resultLevel = "低风险";
            } else if (score >= 6 && score <= 10) {
                resultLevel = "中度风险";
            } else if (score >= 11) {
                resultLevel = "高风险";
            }
            resultLevel = getAddItemLevel(resultLevel, itemScore);
        } else if ("D0007".equals(diseaseCode)) {// 脑卒中
            if (score >= 0 && score <= 6) {
                resultLevel = "低风险";
            } else if (score >= 7 && score <= 11) {
                resultLevel = "中度风险";
            } else if (score >= 12) {
                resultLevel = "高风险";
            }
            resultLevel = getAddItemLevel(resultLevel, itemScore);
        } else if ("D0008".equals(diseaseCode)) {// 乳腺癌
            if (score >= 0 && score <= 5) {
                resultLevel = "低风险";
            } else if (score >= 6 && score <= 11) {
                resultLevel = "中度风险";
            } else if (score >= 12) {
                resultLevel = "高风险";
            }
            resultLevel = getAddItemLevel(resultLevel, itemScore);
        } else if ("D0009".equals(diseaseCode)) {// 代谢综合征
            if (score >= 0 && score <= 1) {
                resultLevel = "低风险";
            } else if (score == 2) {
                resultLevel = "中度风险";
            } else if (score >= 3) {
                resultLevel = "高风险";
            }
        } else if ("diet_advice".equals(diseaseCode)) {// 饮食评估
            if (score == 1) {
                resultLevel = "低风险";
            } else if (score == 2) {
                resultLevel = "中度风险";
            } else if (score >= 3) {
                resultLevel = "高风险";
            }
        } else if ("sport_advice".equals(diseaseCode) || "drink_advice".equals(diseaseCode)
                || "smoke_advice".equals(diseaseCode) || "sleep_advice".equals(diseaseCode)
                || "press_advice".equals(diseaseCode) || "envir_advice".equals(diseaseCode)) {// 代谢综合征
            if (score == 0) {
                resultLevel = "低风险";
            } else if (score == 1) {
                resultLevel = "中度风险";
            } else if (score >= 2) {
                resultLevel = "高风险";
            }
        }
        return resultLevel;
    }

    /**
     * 指标加级
     * 
     * @author zcq
     * @param level
     * @param score
     */
    public static String getAddItemLevel(String level, Integer score) {
        if ("低风险".equals(level)) {
            if (score == 1) {
                level = "中度风险";
            } else if (score > 1) {
                level = "高风险";
            }
        } else if ("中度风险".equals(level)) {
            if (score >= 1) {
                level = "高风险";
            }
        }
        return level;
    }

    /**
     * 分析指标是否异常
     * 
     * @author zcq
     * @param finalItemMap
     * @param resultMap
     * @param compareMap
     * @return
     */
    public static List<String> compareExamItem(Map<String, ItemPojo> finalItemMap, Map<String, BigDecimal> resultMap) {
        List<String> codeList = new ArrayList<String>();
        if (resultMap != null && !CollectionUtils.sizeIsEmpty(resultMap)
                && finalItemMap != null && !CollectionUtils.sizeIsEmpty(finalItemMap)) {
            for (String itemCode : resultMap.keySet()) {
                String compare = finalItemMap.get(itemCode).getItemCompare();
                BigDecimal refValueMin = finalItemMap.get(itemCode).getItemRefValMin();
                BigDecimal refValueMax = finalItemMap.get(itemCode).getItemRefValMax();
                BigDecimal resultValue = resultMap.get(itemCode);
                if (">".equals(compare) && resultValue.compareTo(refValueMax) == 1) {
                    codeList.add(itemCode);
                } else if ("<".equals(compare) && resultValue.compareTo(refValueMin) == -1) {
                    codeList.add(itemCode);
                } else if ("<||>".equals(compare)
                        && (resultValue.compareTo(refValueMin) == -1 || resultValue.compareTo(refValueMax) == 1)) {
                    codeList.add(itemCode);
                } else if (">=".equals(compare)
                        && (resultValue.compareTo(refValueMax) == 1 || resultValue.compareTo(refValueMax) == 0)) {
                    codeList.add(itemCode);
                } else if ("<=".equals(compare)
                        && (resultValue.compareTo(refValueMin) == -1 || resultValue.compareTo(refValueMin) == 0)) {
                    codeList.add(itemCode);
                }
            }
        }
        return codeList;
    }

    /**
     * 分析问卷填空题是否异常
     * 
     * @author zcq
     * @param masInputMap
     * @param answerInputMap
     * @return
     */
    public static List<String> inputCompare(Map<String, DiseaseOptionPojo> masInputMap,
            Map<String, ReportAnswerPojo> answerInputMap) {
        List<String> codeList = new ArrayList<String>();
        if (masInputMap != null && !CollectionUtils.sizeIsEmpty(masInputMap)
                && answerInputMap != null && !CollectionUtils.sizeIsEmpty(answerInputMap)) {

            for (String optionId : answerInputMap.keySet()) {
                if (masInputMap.containsKey(optionId)) {
                    ReportAnswerPojo answerPojo = answerInputMap.get(optionId);
                    String answerContent = answerPojo.getAnswerContent();

                    DiseaseOptionPojo optionPojo = masInputMap.get(optionId);
                    String compare = optionPojo.getRefCompare();
                    // String refString = optionPojo.getRefString();//TODO:暂时不考虑非数值类型
                    BigDecimal refValMin = optionPojo.getRefValMin();
                    BigDecimal refValMax = optionPojo.getRefValMax();

                    if (StringUtils.isNotEmpty(answerContent) && RiskConstant.isNumeric(answerContent)) {
                        BigDecimal resultValue = BigDecimal.valueOf(Double.valueOf(answerContent));
                        if (">".equals(compare) && resultValue.compareTo(refValMax) == 1) {
                            codeList.add(optionId);
                        } else if (">=".equals(compare)
                                && (resultValue.compareTo(refValMax) == 1 || resultValue.compareTo(refValMax) == 0)) {
                            codeList.add(optionId);
                        } else if ("<".equals(compare) && resultValue.compareTo(refValMin) == -1) {
                            codeList.add(optionId);
                        } else if ("<=".equals(compare)
                                && (resultValue.compareTo(refValMin) == -1 || resultValue.compareTo(refValMin) == 0)) {
                            codeList.add(optionId);
                        } else if ("==".equals(compare) && resultValue.compareTo(refValMax) == 0) {
                            codeList.add(optionId);
                        } else if ("<||>=".equals(compare) && (resultValue.compareTo(refValMin) == -1 ||
                                (resultValue.compareTo(refValMax) == 1 || resultValue.compareTo(refValMax) == 0))) {
                            codeList.add(optionId);
                        }
                    }
                }
            }
        }
        return codeList;
    }

    /**
     * 计算BMI得分
     * 
     * @author zcq
     * @param bmi
     * @return
     */
    public static int getBmiScore(double bmi) {
        int score = 0;
        if (bmi < 18.5) {
            score = 1;
        } else if (bmi >= 24 && bmi < 28) {
            score = 1;
        } else if (bmi >= 28) {
            score = 2;
        }
        return score;
    }

    /**
     * 计算BMI风险因素
     * 
     * @author zcq
     * @param bmi
     * @return
     */
    public static String getBmiRiskFactor(double bmi) {
        String factor = "";
        if (bmi < 18.5) {
            factor = "低体重";
        } else if (bmi >= 24 && bmi < 28) {
            factor = "超重";
        } else if (bmi >= 28) {
            factor = "肥胖";
        }
        return factor;
    }

    /**
     * 计算腹型肥胖
     * 
     * @author zcq
     * @param sex
     * @param waistline
     * @return
     */
    public static String getWaistlineRiskFactor(String sex, double waistline) {
        String factor = "";
        if ("male".equals(sex) && waistline >= 90) {
            factor = "腹型肥胖";
        } else if ("female".equals(sex) && waistline >= 85) {
            factor = "腹型肥胖";
        }
        return factor;
    }

    /**
     * 设置结果
     * 
     * @author zcq
     * @param item
     * @param value
     * @param resultMap
     */
    public static void setReportResult(ItemPojo item, BigDecimal value, Map<String, ReportResultPojo> resultMap) {
        ReportResultPojo result = new ReportResultPojo();
        String itemCompare = "———";
        String itemValue = "———";
        String itemCode = item.getItemCode();
        String compare = item.getItemCompare();
        BigDecimal refValueMin = item.getItemRefValMin();
        BigDecimal refValueMax = item.getItemRefValMax();
        if (value != null) {
            itemValue = value.toString();
            if ("<||>".equals(compare) && value.compareTo(refValueMax) == 1) {
                itemCompare = "↑";
            } else if ("<||>".equals(compare) && value.compareTo(refValueMin) == -1) {
                itemCompare = "↓";
            } else if (">=".equals(compare)
                    && (value.compareTo(refValueMax) == 1 || value.compareTo(refValueMax) == 0)) {
                itemCompare = "↑";
            } else if ("<=".equals(compare)
                    && (value.compareTo(refValueMin) == -1 || value.compareTo(refValueMin) == 0)) {
                itemCompare = "↓";
            }
        }
        if (!"———".equals(itemValue) && (!"———".equals(itemCompare)
                || "PE001".equals(itemCode)
                || "PE002".equals(itemCode)
                || "PE003".equals(itemCode)
                || "PE004".equals(itemCode))) {

            result.setItemCode(itemCode);
            result.setItemName(item.getItemName());
            result.setItemValue(itemValue);
            result.setItemCompare(itemCompare);
            result.setItemUnit(item.getItemUnit());
            result.setItemRefValue(refValueMin + "~" + refValueMax);
            resultMap.put(itemCode, result);
        }
    }

    /**
     * 设置疾病史
     * 
     * @author zcq
     * @param historyMap
     * @param inputMap
     * @param optionIdList
     * @return
     */
    public static List<String> setDiseaseHistory(Map<String, String> historyMap,
            Map<String, ReportAnswerPojo> inputMap, List<String> optionIdList) {
        List<String> resultList = new ArrayList<String>();
        String otherHistory = "";
        if (historyMap != null && !CollectionUtils.sizeIsEmpty(historyMap)) {
            for (String optionId : historyMap.keySet()) {
                if (optionIdList.contains(optionId)) {
                    if (("Q001P001001A999".equals(optionId) || "Q001P002001A999".equals(optionId)
                            || "Q001P002001001A999".equals(optionId) || "Q001P003001A999".equals(optionId))
                            && inputMap.get(optionId) != null
                            && StringUtils.isNotEmpty(inputMap.get(optionId).getAnswerContent())) {
                        otherHistory += "；" + inputMap.get(optionId).getAnswerContent();
                    } else {
                        resultList.add(historyMap.get(optionId));
                    }
                }
            }
            if (StringUtils.isNotEmpty(otherHistory)) {
                resultList.add(otherHistory.replaceFirst("；", ""));
            }
        }
        return resultList;
    }

    /**
     * 设置问题选项
     * 
     * @author zcq
     * @param optionList
     * @param historyMap
     */
    public static void setHistoryProblemOptionMap(List<QuestionProblemOptionPojo> optionList,
            Map<String, String> historyMap) {
        if (CollectionUtils.isNotEmpty(optionList)) {
            for (QuestionProblemOptionPojo optionPojo : optionList) {
                historyMap.put(optionPojo.getProblemOptionId(), optionPojo.getOptionContent());
            }
        }
    }

    /**
     * 校验数据
     * 
     * @author zcq
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^-?(([1-9]\\d*([.]\\d+)?)|(0[.]\\d+))|0$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 计算BMI--保留1位小数（不进行舍入）
     * 
     * @author zcq
     * @param custWeight
     * @param custHeight
     * @return
     */
    public static double getBmi(BigDecimal custWeight, BigDecimal custHeight) {
        return custWeight.divide((custHeight.multiply(custHeight)).divide(BigDecimal.valueOf(10000)), 1,
                BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 图片
     */
    public static Map<String, String> imageMap = new HashMap<String, String>();
    static {

        imageMap.put("disease-低风险", "disease-low.jpg");
        imageMap.put("disease-中度风险", "disease-middle.jpg");
        imageMap.put("disease-高风险", "disease-high.jpg");

        imageMap.put("bmi-低体重", "bmi-underweight.jpg");
        imageMap.put("bmi-超重", "bmi-overweight.jpg");
        imageMap.put("bmi-肥胖", "bmi-fat.jpg");
        imageMap.put("bmi-正常", "bmi-normal.jpg");

        imageMap.put("result-低风险", "result-low.jpg");
        imageMap.put("result-中度风险", "result-middle.jpg");
        imageMap.put("result-高风险", "result-high.jpg");
        imageMap.put("result-低体重", "result-underweight.jpg");
        imageMap.put("result-超重", "result-overweight.jpg");
        imageMap.put("result-肥胖", "result-fat.jpg");
        imageMap.put("result-正常", "result-normal.jpg");
    }

    /**
     * 运动建议
     */
    public static Map<String, List<String[]>> sportAdviceMap = new HashMap<String, List<String[]>>();
    static {
        List<String[]> list1 = new ArrayList<String[]>();
        list1.add(new String[] {"耐力运动", "跑步、骑车、游泳、登山、划船、滑冰、滑雪、舞蹈、体操、球类等。", "每周5-7次；每天运动40-60分钟"});
        list1.add(new String[] {"肌力运动", "增加胸肌，腹肌，腰背肌和四肢等肌肉的力量和体积。", "每周2-4次；每次30-60分钟"});
        list1.add(new String[] {"运动技能学习", "球类(篮球、足球等)，体操，田径，舞蹈，游泳等。", "随时"});
        sportAdviceMap.put("1", list1);

        List<String[]> list2 = new ArrayList<String[]>();
        list2.add(new String[] {"耐力运动", "步行、慢跑、骑车、游泳、登山、舞蹈、球类等运动以及气功和太极拳等。", "每周3-7次"});
        list2.add(new String[] {"肌力运动", "保持或增加腹肌，腰背肌和四肢肌肉的肌力。如弓步压腿、高抬腿以及各种韵律操等", "每周2-3次；每次20-30分钟"});
        list2.add(new String[] {"生活方式运动", "通过生活中各种身体活动增加总身体活动水平。如爬楼梯，家务劳动等。", "随时"});
        sportAdviceMap.put("2", list1);

        List<String[]> list3 = new ArrayList<String[]>();
        list3.add(new String[] {"有氧耐力运动", "园艺，旅游，步行、慢跑、跳舞、骑车、游泳和太极拳等。", "每周3-5次；每次10-60分钟"});
        list3.add(new String[] {"肌肉/耐力运动", "建议采用弹力橡皮带编排的体操，进行腰背肌，腹肌，臀肌和四肢肌肉的练习。", "每周2次"});
        list3.add(new String[] {"灵活性和协调性运动", "家务劳动、舞蹈、太极拳、广播操、韵律操等。", "随时"});
        sportAdviceMap.put("3", list1);
    }

    /**
     * 计算推荐热量
     * 
     * @author zcq
     * @param weight
     * @param height
     * @param level
     * @param sex
     * @return
     */
    public static Double getNormalEnery(BigDecimal weight, BigDecimal height, String level, String sex) {
        double bmi = getBmi(weight, height);
        double h = height.doubleValue();
        double norWkCal = 0;
        // 1.得到标准体重对应kcal数
        if (bmi < 16) {
            norWkCal = 40;
        } else if (bmi < 17) {
            norWkCal = 35;
        } else if (bmi < 18) {
            norWkCal = 34;
        } else if (bmi < 19) {
            norWkCal = 33;
        } else if (bmi < 20) {
            norWkCal = 32;
        } else if (bmi < 21) {
            norWkCal = 31;
        } else if (bmi < 22) {
            norWkCal = 30;
        } else if (bmi < 23) {
            norWkCal = 29;
        } else if (bmi < 24) {
            norWkCal = 28;
        } else if (bmi < 25) {
            norWkCal = 27;
        } else if (bmi < 26) {
            norWkCal = 26;
        } else if (bmi < 27) {
            norWkCal = 25;
        } else if (bmi < 28) {
            norWkCal = 24;
        } else if (bmi < 29) {
            norWkCal = 23;
        } else if (bmi < 30) {
            norWkCal = 22;
        } else {
            norWkCal = 20;
        }
        // 2.得到理想体重
        double dreamWeight = 0;
        if (h < 150) {
            dreamWeight = h - 100;
        } else {
            if ("male".equals(sex)) {
                dreamWeight = h - 105;
            } else {
                dreamWeight = (h - 100) * 0.85;
            }
        }

        // 3.根据体力等级获取推荐热量
        double engerTotal = dreamWeight * norWkCal;
        double recomond = 0;
        if ("st_weight".equals(level)) {
            recomond = engerTotal + 8 * dreamWeight;
        } else if ("st_medium".equals(level)) {
            recomond = engerTotal + 5 * dreamWeight;
        } else {
            recomond = engerTotal - 5 * dreamWeight;
        }
        return recomond;
    }

    public static void main(String[] args) {
        // compareExamItem(null, null, null);
        // System.out.println(isNumeric("34"));
        // System.out.println(getBmi(BigDecimal.valueOf(78), BigDecimal.valueOf(175)));
        // System.out.println(JodaTimeTools.getYears(JodaTimeTools.toDate("1989-12-20"), new Date()));
        // List<String> list = new ArrayList<String>();
        // System.out.println(StringUtils.join(list, "、"));
    }

}
