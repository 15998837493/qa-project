
package com.qa.controller.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.health.utils.times.JodaTimeTools;
import com.qa.examitem.constants.RiskConstant;
import com.qa.examitem.pojo.ReportPojo;
import com.qa.examitem.pojo.ReportResultPojo;
import com.qa.master.pojo.DiseasePojo;
import com.qa.master.pojo.ItemPojo;

/**
 * 危险分层--PDF
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-13 zcq 初版
 */
@Component
public class DiseaseRiskPdf extends AbstractPdf<DiseaseRiskPojo> {

    private String imagPath = absolute_path + "common/images/";

    /**
     * 设置PDF内容
     * 
     * @author zcq
     * @param diseaseRisk
     * @throws DocumentException
     */
    @Override
    public void handler(DiseaseRiskPojo diseaseRisk) throws DocumentException {
        document.setMargins(30f, 30f, 30f, 30f);// 设置PDF边距
        // 设置危险分层综述
        setDiseaseRiskSummary(diseaseRisk);
        // 设置疾病评估
        setDiseaseRiskAssess(diseaseRisk.getDiseaseMap().get("D0001"), diseaseRisk.getResultMap());
        setDiseaseRiskAssess(diseaseRisk.getDiseaseMap().get("D0002"), diseaseRisk.getResultMap());
        setDiseaseRiskAssess(diseaseRisk.getDiseaseMap().get("D0003"), diseaseRisk.getResultMap());
        setDiseaseRiskAssess(diseaseRisk.getDiseaseMap().get("D0004"), diseaseRisk.getResultMap());
        setDiseaseRiskAssess(diseaseRisk.getDiseaseMap().get("D0005"), diseaseRisk.getResultMap());
        setDiseaseRiskAssess(diseaseRisk.getDiseaseMap().get("D0006"), diseaseRisk.getResultMap());
        setDiseaseRiskAssess(diseaseRisk.getDiseaseMap().get("D0007"), diseaseRisk.getResultMap());
        if ("female".equals(diseaseRisk.getCustomer().getCustSex())) {
            setDiseaseRiskAssess(diseaseRisk.getDiseaseMap().get("D0008"), diseaseRisk.getResultMap());
        }
        setDiseaseRiskAssess(diseaseRisk.getDiseaseMap().get("D0009"), diseaseRisk.getResultMap());
        // 设置生活方式分析
        setLifeStyleAnalysis(diseaseRisk.getResultMap());
        // 设置膳食建议
        setDietAdvice(diseaseRisk.getResultMap());
        // 设置运动建议
        setSportAdvice(diseaseRisk.getSportAdviceList(), diseaseRisk.getResultMap());
    }

    // **************************************************设置报告明细********************************************************

    private void setDiseaseRiskSummary(DiseaseRiskPojo diseaseRisk) throws DocumentException {
        document.newPage();
        // 设置报告头
        addContentTableHead0(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0));
        // 信息栏
        addContentTable0(diseaseRisk.getCustomer());// 添加内容
        // 慢病风险
        addTitleTable("慢病风险", utils.createTable(1, Element.ALIGN_CENTER, 100f, 15f, 0));// 添加标题
        addContentTable1(diseaseRisk.getCustomer().getCustSex(), diseaseRisk.getResultMap());
        // 健康史
        addTitleTable("健康史", utils.createTable(1, Element.ALIGN_LEFT, 49f, 5f, 0));// 添加标题
        addContentTable3(diseaseRisk.getResultMap());
        // 生活方式
        addTitleTable("生活方式", utils.createTable(1, Element.ALIGN_RIGHT, 49f, 5f, 0));// 添加标题
        addContentTable4(diseaseRisk.getResultMap());
        // 重要指标
        addContentTable2(diseaseRisk.getItemMap(), diseaseRisk.getResultMap());
    }

    /**
     * 设置疾病风险评估
     * 
     * @author zcq
     * @param diseaseRisk
     * @throws DocumentException
     */
    private void setDiseaseRiskAssess(DiseasePojo disease, Map<String, ReportResultPojo> resultMap)
            throws DocumentException {
        document.newPage();
        // 设置报告头
        addContentTableHead0(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0));
        // 疾病风险评估
        Paragraph title = utils.createParagraphTitle(disease.getDiseaseName(), 15, utils.darkGrayColor, 0, 25f, 30f);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // 名词解释
        addTitleTable("名词解释", utils.createTable(1, Element.ALIGN_CENTER, 100f, 10f, 0));// 添加标题
        document.add(utils.createParagraph("     " + disease.getDiseaseDescription(), 20, 11, utils.darkGrayColor, 0,
                0, 20f));
        // 流行病学
        addTitleTable("流行病学", utils.createTable(1, Element.ALIGN_CENTER, 100f, 10f, 0));// 添加标题
        document.add(utils.createParagraph("     " + disease.getDiseaseEpidemiology(), 20, 11, utils.darkGrayColor, 0,
                0, 20f));
        // 评估结果
        String level = resultMap.get(disease.getDiseaseCode()).getItemResult();
        addTitleTable("评估结果", utils.createTable(1, Element.ALIGN_LEFT, 49f, 20f, 0));// 添加标题
        PdfPTable table = utils.createTable(1, Element.ALIGN_LEFT, 40f, -168f, 0);
        table.addCell(imageCell(130f, RiskConstant.imageMap.get("result-" + level)));
        document.add(table);
        // 风险因素
        addTitleTable("风险因素", utils.createTable(1, Element.ALIGN_RIGHT, 49f, 10f, 0));// 添加标题
        String riskFactor = resultMap.get(disease.getDiseaseCode()).getItemValue();
        if (StringUtils.isEmpty(riskFactor)) {
            riskFactor = "无";
        }
        Paragraph pLeft = new Paragraph();
        Paragraph pRight = new Paragraph();
        if (StringUtils.isNotEmpty(riskFactor)) {
            String[] riskFactors = riskFactor.split("、");
            table = utils.createTable(2, Element.ALIGN_RIGHT, 49f, 15f, 0);
            for (int i = 0; i < riskFactors.length; i++) {
                Phrase phrase1 = new Phrase(1f, "● ", new Font(utils.createFont(), 18, Font.BOLD, blueGreenColor));
                Phrase phrase2 = new Phrase(1f, riskFactors[i] + "\n",
                        new Font(utils.createFont(), 11, Font.NORMAL, utils.darkGrayColor));
                if (i < 8) {
                    pLeft.add(phrase1);
                    pLeft.add(phrase2);
                } else {
                    pRight.add(phrase1);
                    pRight.add(phrase2);
                }
            }
        }
        PdfPCell cell = new PdfPCell(pLeft);
        cell.setFixedHeight(160f);
        cell.setBorderWidth(0);
        table.addCell(cell);
        cell = new PdfPCell(pRight);
        cell.setBorderWidth(0);
        table.addCell(cell);
        cell.setFixedHeight(160f);
        document.add(table);

        // 预防要点
        addTitleTable("预防要点", utils.createTable(1, Element.ALIGN_CENTER, 100f, 0, 0));// 添加标题
        String points = disease.getDiseasePrevention();
        if (StringUtils.isNotEmpty(points)) {
            document.add(utils.createParagraph(points, 25, 11, utils.darkGrayColor, 0, 0, 0));
        }
    }

    private void setLifeStyleAnalysis(Map<String, ReportResultPojo> resultMap) throws DocumentException {
        document.newPage();
        // 设置报告头
        addContentTableHead0(utils.createTable(1, Element.ALIGN_CENTER, 100f, 20f, 0));
        // 生活方式分析
        Paragraph title = utils.createParagraphTitle("生活方式分析", 15, utils.darkGrayColor, 0, 30f, 25f);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(utils.createParagraph(
                "     医学研究证实，个人行为和生活方式会预示并影响着健康趋势和寿命。根据评估结果，对您的生活方式信息进行汇总分析，产生如下建议。请仔细阅读此段内容，积极改进，控制健康风险。"
                , 20, 11, utils.darkGrayColor, 0, 0, 20f));

        PdfPTable table = utils.createTable(1, Element.ALIGN_LEFT, 20f, 0, 0);
        PdfPCell cell = utils.baseCell("优质生活方式法则", new Font(utils.createFont(), 11, Font.BOLD, blueGreenColor));
        cell.setBackgroundColor(lestBlueGreenColor);
        cell.setBorderColor(blueGreenColor);
        cell.setFixedHeight(20f);// 设置行高为自适应
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);
        document.add(table);

        document.add(utils.createParagraph("合理膳食、适量运动、戒烟限酒、心理平衡、睡眠充足、环境良好。", 20, 11,
                utils.darkGrayColor, 0, 0, 20f));

        // 分析建议
        addTitleTable("分析建议", utils.createTable(1, Element.ALIGN_CENTER, 100f, 10f, 0));// 添加标题
        float[] tableWidth = new float[] {0.06f, 0.04f, 0.1f, 0.8f};
        table = utils.createTable(tableWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        String[] lifeStyle = new String[] {"饮食", "运动", "饮酒", "吸烟", "睡眠", "压力", "环境"};
        String[] lifeImag = new String[] {"life-diet.jpg", "life-sport.jpg", "life-drink.jpg", "life-smoke.jpg",
                "life-sleep.jpg", "life-press.jpg", "life-envir.jpg"};
        String[] lifeCodes = new String[] {"diet_advice", "sport_advice", "drink_advice", "smoke_advice",
                "sleep_advice", "press_advice", "envir_advice"};
        for (int i = 0; i < lifeStyle.length; i++) {
            cell = contentCell("", 2);
            cell.setBorderWidthRight(0);
            table.addCell(cell);

            cell = contentCell("", 2);
            Image img = null;
            try {
                img = Image.getInstance(imagPath + lifeImag[i]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cell.setImage(img);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthRight(0);
            table.addCell(cell);

            cell = contentCell(lifeStyle[i], 2);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setLeading(0, 1f);
            cell.setBorderWidthLeft(0);
            table.addCell(cell);

            cell = contentCell(resultMap.get(lifeCodes[i]).getItemAdvice(), 0);
            cell.setFixedHeight(0);
            cell.setLeading(1f, 1.5f);
            cell.setPaddingBottom(12f);// 设置下边距
            cell.setPaddingLeft(10f);// 设置左边距
            cell.setPaddingRight(10f);// 设置右边距
            table.addCell(cell);
        }
        document.add(table);
    }

    private void setDietAdvice(Map<String, ReportResultPojo> resultMap) throws DocumentException {
        document.newPage();
        // 设置报告头
        addContentTableHead0(utils.createTable(1, Element.ALIGN_CENTER, 100f, 30f, 0));
        // 膳食建议
        Paragraph title = utils.createParagraphTitle("膳食建议", 15, utils.darkGrayColor, 0, 25f, 30);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // 膳食原则
        addTitleTable("膳食原则", utils.createTable(1, Element.ALIGN_CENTER, 100f, 15f, 0));// 添加标题
        document.add(utils.createParagraph("（1）食物多样，谷类为主", 11, utils.darkGrayColor, 0, 10f, 20f));
        document.add(utils.createParagraph("（2）吃动平衡，健康体重", 11, utils.darkGrayColor, 0, 0, 20f));
        document.add(utils.createParagraph("（3）多吃蔬果、奶类、大豆", 11, utils.darkGrayColor, 0, 0, 20f));

        document.add(utils.createParagraph("（4）适量吃鱼、禽、蛋、瘦肉", 11, utils.darkGrayColor, 300f, -60f, 20f));
        document.add(utils.createParagraph("（5）少盐少油，控糖限酒", 11, utils.darkGrayColor, 300f, 0, 20f));
        document.add(utils.createParagraph("（6）杜绝浪费，兴新食尚", 11, utils.darkGrayColor, 300f, 0, 20f));

        // 饮食评级
        addTitleTable("饮食评级", utils.createTable(1, Element.ALIGN_CENTER, 100f, 20f, 0));// 添加标题

        float[] tableWidth = new float[] {0.23f, 0.77f};
        PdfPTable table = utils.createTable(tableWidth, Element.ALIGN_CENTER, 100, 20f, 0);
        Paragraph paragraph = new Paragraph();
        Phrase phrase1 = new Phrase(1f, "饮食评级：", new Font(utils.createFont(), 11, Font.NORMAL, utils.darkGrayColor));
        String dietLevel = resultMap.get("diet_advice").getItemResult();
        Phrase phrase2 = new Phrase(1f, dietLevel, new Font(utils.createFont(), 11, Font.NORMAL,
                getAssessColor(dietLevel)));
        paragraph.add(phrase1);
        paragraph.add(phrase2);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setFixedHeight(80f);
        cell.setBorderWidth(0);
        table.addCell(cell);
        cell = imageCell(80f, RiskConstant.imageMap.get("disease-" + dietLevel));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);
        document.add(table);

        // 不良饮食习惯
        addTitleTable("不良饮食习惯", utils.createTable(1, Element.ALIGN_CENTER, 100f, 25f, 0));// 添加标题
        String badDietHabit = resultMap.get("diet_advice").getItemValue();
        if (StringUtils.isEmpty(badDietHabit)) {
            badDietHabit = "无";
        }
        document.add(utils.createParagraph(badDietHabit, 11, utils.darkGrayColor, 0, 0, 20f));

        // 营养摄入参考
        addTitleTable("营养摄入参考", utils.createTable(1, Element.ALIGN_CENTER, 100f, 20f, 0));// 添加标题

        table = utils.createTable(1, Element.ALIGN_LEFT, 25f, -75f, 0);
        paragraph = new Paragraph();
        phrase1 = new Phrase(1f, "平均每天推荐摄入量：\n\n", new Font(utils.createFont(), 11, Font.NORMAL, utils.darkGrayColor));
        phrase2 = new Phrase(1f, resultMap.get("normal_energy").getItemValue() + " kcal", new Font(utils.createFont(),
                15, Font.BOLD, blueGreenColor));
        paragraph.add(phrase1);
        paragraph.add(phrase2);
        cell = new PdfPCell(paragraph);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(75f);
        cell.setBorderWidth(0);
        table.addCell(cell);
        document.add(table);

        table = utils.createTable(3, Element.ALIGN_RIGHT, 75f, 25f, 0);
        cell = contentCell("产能营养素占比\n（推荐）", 2);
        cell.setRowspan(3);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = contentCell("碳水化合物（CHO）", 0);
        table.addCell(cell);
        cell = contentCell("50~65%", 1);
        table.addCell(cell);

        cell = contentCell("脂肪（FAT）", 0);
        table.addCell(cell);
        cell = contentCell("20~30%", 1);
        table.addCell(cell);

        cell = contentCell("蛋白质（PRO）", 0);
        table.addCell(cell);
        cell = contentCell("10~20%", 1);
        table.addCell(cell);

        document.add(table);

        // 膳食模型参考
        addTitleTable("膳食模型参考", utils.createTable(1, Element.ALIGN_CENTER, 100f, 20f, 0));// 添加标题

        table = utils.createTable(1, Element.ALIGN_LEFT, 35f, -185f, 0);
        cell = imageCell(185f, "dietary.png");
        cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        table.addCell(cell);
        document.add(table);

        table = utils.createTable(4, Element.ALIGN_RIGHT, 65f, 20f, 0);
        String[] names = new String[] {"盐", "蔬菜类", "油", "水果类", "奶及奶制品", "谷薯类", "大豆及坚果类", "全谷物和杂豆", "畜禽肉", "薯类", "水产品",
                "蔬菜类"};
        String[] values = new String[] {"<6g", "300~500g", "25~30g", "200~350g", "300g", "250~400g", "25~35g",
                "50~150g", "40~75g", "50~100g", "40~75g", "40~50g"};
        for (int i = 0; i < names.length; i++) {
            cell = contentCell(names[i], 2);
            table.addCell(cell);
            cell = contentCell(values[i], 1);
            table.addCell(cell);
        }

        cell = contentCell("水", 2);
        table.addCell(cell);
        cell = contentCell("1500~1700ml", 1);
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setPaddingLeft(15f);
        table.addCell(cell);

        document.add(table);
    }

    private void setSportAdvice(List<String[]> sportAdviceList, Map<String, ReportResultPojo> resultMap)
            throws DocumentException {
        document.newPage();
        // 设置报告头
        addContentTableHead0(utils.createTable(1, Element.ALIGN_CENTER, 100f, 30f, 0));
        // 运动建议
        Paragraph title = utils.createParagraphTitle("运动建议", 15, utils.darkGrayColor, 0, 30f, 25f);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(utils.createParagraph(
                "     科学合理的运动，可以改变人们不良生活方式，有效的帮助人们排出体内因过多摄入的脂肪与高能量而产生的不良物质，"
                        + "改善提高身体各器官的机能，对大脑功能与情绪调整也起到非常重要的作用。同时，对于慢性疾病的有效防控与辅助康复有着重要的意义。", 20, 11,
                utils.darkGrayColor, 0, 0, 20f));

        // 运动评级
        addTitleTable("运动评级", utils.createTable(1, Element.ALIGN_CENTER, 100f, 20f, 0));// 添加标题

        float[] tableWidth = new float[] {0.23f, 0.77f};
        PdfPTable table = utils.createTable(tableWidth, Element.ALIGN_CENTER, 100, 20f, 0);
        Paragraph paragraph = new Paragraph();
        Phrase phrase1 = new Phrase(1f, "运动评级：", new Font(utils.createFont(), 11, Font.NORMAL, utils.darkGrayColor));
        String sportLevel = resultMap.get("sport_advice").getItemResult();
        Phrase phrase2 = new Phrase(1f, sportLevel, new Font(utils.createFont(), 11, Font.NORMAL,
                getAssessColor(sportLevel)));
        paragraph.add(phrase1);
        paragraph.add(phrase2);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setFixedHeight(80f);
        cell.setBorderWidth(0);
        table.addCell(cell);
        cell = imageCell(80f, RiskConstant.imageMap.get("disease-" + sportLevel));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);
        document.add(table);

        // 运动建议
        addTitleTable("运动建议", utils.createTable(1, Element.ALIGN_CENTER, 100f, 15f, 0));// 添加标题

        tableWidth = new float[] {0.2f, 0.5f, 0.3f};
        table = utils.createTable(tableWidth, Element.ALIGN_RIGHT, 100f, 25f, 0);

        cell = titleCell("运动分类");
        table.addCell(cell);
        cell = titleCell("适宜项目");
        table.addCell(cell);
        cell = titleCell("频率/时间");
        table.addCell(cell);

        if (CollectionUtils.isNotEmpty(sportAdviceList)) {
            for (String[] arr : sportAdviceList) {
                cell = contentCell(arr[0], 0);
                cell.setFixedHeight(0);
                table.addCell(cell);
                cell = contentCell(arr[1], 0);
                cell.setFixedHeight(0);
                table.addCell(cell);
                cell = contentCell(arr[2], 0);
                cell.setFixedHeight(0);
                table.addCell(cell);
            }
        }
        document.add(table);

        // 注意事项
        table = utils.createTable(1, Element.ALIGN_CENTER, 100f, 5f, 0);
        cell = utils.baseCell("  ||  注意事项", new Font(utils.createFont(), 11, Font.BOLD, utils.whiteColor));
        cell.setBackgroundColor(yellowColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(yellowColor);
        table.addCell(cell);
        document.add(table);

        String[] names = new String[] {
                "（1）选择空气清新的环境",
                "（2）进行适度的热身运动",
                "（3）酒后禁止运动",
                "（4）不要空腹及饭后立即运动，餐后1小时运动为宜",
                "（5）运动后适量补充水分",
                "（6）运动需要循序渐进并持之以恒的原则，不宜突然加大运动量"};
        for (int i = 0; i < names.length; i++) {
            document.add(utils.createParagraph(names[i], 24, 11, utils.darkGrayColor, 0, 0, 0));
        }

    }

    /**
     * 设置表头
     * 
     * @author zcq
     * @param table
     * @throws DocumentException
     */
    private void addContentTableHead0(PdfPTable table) throws DocumentException {
        PdfPCell cell = utils.baseCell("", utils.reportFont);
        cell.setBackgroundColor(blueGreenColor);
        cell.setBorderColor(blueGreenColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(10f);
        table.addCell(cell);
        table.setSpacingAfter(15f);
        document.add(table);
    }

    /**
     * 设置个人信息
     * 
     * @author zcq
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    private void addContentTable0(ReportPojo customer) throws DocumentException {
        document.add(utils.createParagraphTitle("健康管理报告", 18, utils.darkGrayColor, 16, 25, 0));
        document.add(utils.createParagraphTitle("HEALTH MANAGEMENT REPORT", 7, blueGreenColor, 15, 17, -21));
        float nameLeft = 200f;
        float cemargin = 25f;
        float itemmargin = 90f;

        float linemargin = 93f;
        float liney1 = 777f;
        float liney2 = liney1 - 30f;

        document.add(utils.createParagraph("姓名", 11, utils.darkGrayColor, nameLeft - 10f, 0, 0));
        document.add(utils.createParagraph("Name", 11, utils.deepGrayColor, nameLeft + cemargin - 10f, 0, 0));
        document.add(utils.createParagraph(customer.getCustName(), 10, utils.darkBrownColor, nameLeft + 10f, 15f, -15f));

        utils.drawLine(writer, nameLeft + 1f * linemargin, liney1, nameLeft + 1 * linemargin, liney2, 1f,
                utils.lightGrayColor);

        document.add(utils.createParagraph("性别", 11, utils.darkGrayColor, nameLeft + 1 * itemmargin, 0, 0));
        document.add(utils.createParagraph("Sex", 11, utils.deepGrayColor, nameLeft + 1 * itemmargin + cemargin, 0, 0));
        document.add(utils.createParagraph(("male".equals(customer.getCustSex()) ? "男" : "女"), 10,
                utils.darkBrownColor, nameLeft + 1 * itemmargin + 20f, 15f, -15f));

        utils.drawLine(writer, nameLeft + 2f * linemargin, liney1, nameLeft + 2 * linemargin, liney2, 1f,
                utils.lightGrayColor);

        document.add(utils.createParagraph("年龄", 11, utils.darkGrayColor, nameLeft + 2 * itemmargin, 0, 0));
        document.add(utils.createParagraph("Age", 11, utils.deepGrayColor, nameLeft + 2 * itemmargin + cemargin, 0, 0));
        if (customer.getCustBirthday() != null) {
            document.add(utils.createParagraph(JodaTimeTools.getYears(customer.getCustBirthday(), new Date()) + "", 10,
                    utils.darkBrownColor, nameLeft + 2 * itemmargin + 20f, 15f, -15f));
        }

        utils.drawLine(writer, nameLeft + 3f * linemargin, liney1, nameLeft + 3 * linemargin, liney2, 1f,
                utils.lightGrayColor);

        document.add(utils.createParagraph("日期", 11, utils.darkGrayColor, nameLeft + 3 * itemmargin, 0, 0));
        document.add(utils.createParagraph("Date", 11, utils.deepGrayColor, nameLeft + 3 * itemmargin + cemargin, 0, 0));
        document.add(utils.createParagraph(JodaTimeTools.toString(customer.getReportDate(), JodaTimeTools.FORMAT_6),
                10, utils.darkBrownColor, nameLeft + 3 * itemmargin, 15f, 30f));
    }

    /**
     * 设置慢病风险
     * 
     * @author zcq
     * @param margin
     * @throws DocumentException
     */
    private void addContentTable1(String custSex, Map<String, ReportResultPojo> resultMap) throws DocumentException {
        float iamgeSize = 60f;
        if ("female".equals(custSex)) {
            PdfPTable table = utils.createTable(5, Element.ALIGN_CENTER, 100f, 10f, 0);
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0001").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0002").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0003").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("bmi-" + resultMap.get("D0004").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0005").getItemResult())));

            table.addCell(contentCell(resultMap.get("D0001").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0002").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0003").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0004").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0005").getItemName(), 3));

            document.add(table);

            table = utils.createTable(4, Element.ALIGN_CENTER, 80f, 15f, 0);

            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0006").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0007").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0008").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0009").getItemResult())));

            table.addCell(contentCell(resultMap.get("D0006").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0007").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0008").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0009").getItemName(), 3));

            document.add(table);
        } else {
            PdfPTable table = utils.createTable(4, Element.ALIGN_CENTER, 100f, 10f, 0);
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0001").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0002").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0003").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("bmi-" + resultMap.get("D0004").getItemResult())));

            table.addCell(contentCell(resultMap.get("D0001").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0002").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0003").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0004").getItemName(), 3));

            document.add(table);

            table = utils.createTable(4, Element.ALIGN_CENTER, 100f, 15f, 0);

            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0005").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0006").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0007").getItemResult())));
            table.addCell(imageCell(iamgeSize,
                    RiskConstant.imageMap.get("disease-" + resultMap.get("D0009").getItemResult())));

            table.addCell(contentCell(resultMap.get("D0005").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0006").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0007").getItemName(), 3));
            table.addCell(contentCell(resultMap.get("D0009").getItemName(), 3));

            document.add(table);
        }

    }

    /**
     * 重要指标
     * 
     * @author zcq
     * @throws DocumentException
     */
    private void addContentTable2(Map<String, ItemPojo> masItemMap, Map<String, ReportResultPojo> itemResultMap)
            throws DocumentException {
        PdfPTable table = utils.createTable(5, Element.ALIGN_CENTER, 100f, 20f, 0);
        table.addCell(titleCell("指标名称"));
        table.addCell(titleCell("指标结果"));
        table.addCell(titleCell("参考范围"));
        table.addCell(titleCell("单位"));
        table.addCell(titleCell("趋势"));

        int count = 0;
        for (String itemCode : masItemMap.keySet()) {
            ReportResultPojo p1 = itemResultMap.get(itemCode);
            if (p1 != null) {
                table.addCell(contentCell(p1.getItemName(), 0));
                table.addCell(contentCell(p1.getItemValue(), 1));
                table.addCell(contentCell(p1.getItemRefValue(), 2));
                table.addCell(contentCell(p1.getItemUnit(), 1));
                table.addCell(contentCell(p1.getItemCompare(), 1));
                count++;
            }
        }
        if (count > 0) {
            addTitleTable("重要指标", utils.createTable(1, Element.ALIGN_CENTER, 100f, 10f, 0));// 添加标题
            document.add(table);
        }
    }

    /**
     * 健康史
     * 
     * @author zcq
     * @throws DocumentException
     */
    private void addContentTable3(Map<String, ReportResultPojo> resultMap) throws DocumentException {
        String[] historyImages = new String[] {"history-disease.png", "history-family.png", "history-medicine.png"};
        String[] historyCodes = new String[] {"disease_history", "family_history", "medicine_history"};
        for (int i = 0; i < historyCodes.length; i++) {
            PdfPTable table = utils.createTable(new float[] {0.1f, 0.9f}, Element.ALIGN_LEFT, 49f, 3f, 0);
            table.addCell(imageCell(25f, historyImages[i]));
            PdfPCell cell = utils.baseCell(resultMap.get(historyCodes[i]).getItemName(), new Font(utils.createFont(),
                    12, Font.BOLD, blueGreenColor));
            cell.setBackgroundColor(utils.whiteColor);
            cell.setBorderWidth(0);
            cell.setFixedHeight(20f);// 设置行高为自适应
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            table.addCell(cell);
            document.add(table);

            table = utils.createTable(1, Element.ALIGN_LEFT, 49f, 5f, 0);
            String diseaseNames = resultMap.get(historyCodes[i]).getItemValue();
            if (StringUtils.isNotEmpty(diseaseNames) && diseaseNames.length() > 46) {
                diseaseNames = diseaseNames.substring(0, 43) + "……";
            }
            cell = contentCell(diseaseNames, 0);
            cell.setBorderColor(blueGreenColor);
            cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
            cell.setFixedHeight(42f);
            table.addCell(cell);
            document.add(table);
        }
        document.add(utils.createParagraph("", 1, utils.whiteColor, 0, -248f, 0));
    }

    /**
     * 生活方式
     * 
     * @author zcq
     * @throws DocumentException
     */
    private void addContentTable4(Map<String, ReportResultPojo> resultMap) throws DocumentException {
        float left1 = 340f;
        float linemargin = 57f;

        float high = 444f;
        float highmargin = 31f;

        String[] lifeStyle = new String[] {"饮食", "运动", "饮酒", "吸烟", "睡眠", "压力", "环境"};
        String[] lifeCodes = new String[] {"diet_advice", "sport_advice", "drink_advice", "smoke_advice",
                "sleep_advice", "press_advice", "envir_advice"};

        for (int i = 0; i < lifeStyle.length; i++) {
            String level = resultMap.get(lifeCodes[i]).getItemResult();
            BaseColor[] colors = getLifeColor(level);
            if (i == 0) {
                document.add(utils.createParagraph("饮食", 11, utils.darkGrayColor, 280f, 30f, 0));
                utils.drawLine(writer, left1, high, left1 + linemargin, high, 7f, colors[0]);
                utils.drawLine(writer, left1 + linemargin, high, left1 + 2 * linemargin, high, 7f, colors[1]);
                utils.drawLine(writer, left1 + 2 * linemargin, high, left1 + 3 * linemargin, high, 7f, colors[2]);
                document.add(utils.createParagraph(level, 11, utils.darkGrayColor, 490f, 0, 31f));
            } else {
                document.add(utils.createParagraph(lifeStyle[i], 11, utils.darkGrayColor, 280f, 0, 0));
                utils.drawLine(writer, left1, high - i * highmargin, left1 + linemargin, high - i * highmargin, 7f,
                        colors[0]);
                utils.drawLine(writer, left1 + linemargin, high - i * highmargin, left1 + 2 * linemargin,
                        high - i * highmargin, 7f, colors[1]);
                utils.drawLine(writer, left1 + 2 * linemargin, high - i * highmargin, left1 + 3 * linemargin, high - i
                        * highmargin, 7f, colors[2]);
                document.add(utils.createParagraph(level, 11, utils.darkGrayColor, 490f, 0, 31f));
            }
        }
        document.add(utils.createParagraph("", 11, utils.darkGrayColor, 490f, -6f, 0));
    }

    /**
     * 获取风险等级颜色
     * 
     * @author zcq
     * @param level
     * @return
     */
    private BaseColor[] getLifeColor(String level) {
        BaseColor[] colors = new BaseColor[] {utils.grayWhiteColor, utils.grayWhiteColor, utils.grayWhiteColor};
        if ("低风险".equals(level)) {
            colors[0] = utils.greenColor;
        } else if ("中度风险".equals(level)) {
            colors[1] = utils.blueColor;
        } else if ("高风险".equals(level)) {
            colors[2] = yellowColor;
        }
        return colors;
    }

    /**
     * 获取评级颜色
     * 
     * @author zcq
     * @param level
     * @return
     */
    private BaseColor getAssessColor(String level) {
        if ("低风险".equals(level) || "low".equals(level)) {
            return utils.greenColor;
        } else if ("中度风险".equals(level) || "middle".equals(level)) {
            return utils.blueColor;
        } else if ("高风险".equals(level) || "high".equals(level)) {
            return yellowColor;
        }
        return utils.greenColor;
    }

    // ****************************************************工具方法*****************************************************

    private void addTitleTable(String titleName, PdfPTable titleTable) throws DocumentException {
        PdfPCell cell;
        cell = utils.baseCell("  ||  " + titleName, new Font(utils.createFont(), 11, Font.BOLD, utils.whiteColor));
        cell.setBackgroundColor(blueGreenColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(blueGreenColor);
        titleTable.addCell(cell);
        document.add(titleTable);
    }

    /** 蓝绿色 */
    private BaseColor blueGreenColor = new BaseColor(56, 186, 184);

    /** 浅蓝绿色 */
    private BaseColor lightBlueGreenColor = new BaseColor(175, 224, 224);

    /** 浅蓝绿色 */
    private BaseColor lestBlueGreenColor = new BaseColor(236, 248, 248);

    /** 土黄色 */
    private BaseColor yellowColor = new BaseColor(242, 152, 0);

    /** 一级字体12号，报告名称 */

    private PdfPCell titleCell(String content) {
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 11, Font.NORMAL, utils.darkGrayColor));
        cell.setBackgroundColor(lightBlueGreenColor);
        cell.setBorderColor(blueGreenColor);
        cell.setFixedHeight(25f);// 设置行高为自适应
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell contentCell(String content, int align) {
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 11, Font.NORMAL, utils.darkGrayColor));
        cell.setBorderColor(blueGreenColor);
        if (align == 0) {
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        } else if (align == 1) {
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        } else if (align == 2) {
            cell.setBackgroundColor(lestBlueGreenColor);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        } else if (align == 3) {
            cell.setBorderWidth(0);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }
        cell.setFixedHeight(25f);// 设置行高为自适应
        cell.setLeading(1, 1.5f);// 设置行间距
        cell.setPaddingBottom(7f);// 设置下边距
        // cell.setPaddingLeft(10f);// 设置左边距
        // cell.setPaddingRight(10f);// 设置右边距
        return cell;
    }

    private PdfPCell imageCell(float fixedHeight, String imageName) {
        PdfPCell cell = new PdfPCell();
        Image img = null;
        try {
            img = Image.getInstance(imagPath + imageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cell.setImage(img);
        cell.setFixedHeight(fixedHeight);
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

}
