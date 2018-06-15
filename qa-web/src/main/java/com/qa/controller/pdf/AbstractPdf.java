
package com.qa.controller.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.mnt.health.utils.pdf.PdfUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.health.utils.times.JodaTimeTools;
import com.qa.CacheProjectInfo;
import com.qa.controller.constants.PathConstant;
import com.qa.examitem.constants.ReportConstant;
import com.qa.examitem.entity.Report;
import com.qa.examitem.pojo.ReportPojo;
import com.qa.examitem.service.ReportServiceImpl;
import com.qa.exception.RestfulException;
import com.qa.main.results.ResultCode;

/**
 * PDF抽象类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-4-7 zcq 初版
 */
public abstract class AbstractPdf<T> {

    public Document document;

    public PdfWriter writer;

    public PdfUtils utils;

    public Map<String, String> codeMap = new HashMap<String, String>();

    public static final String PDF_PATH = "resource/report/pdf/";

    public final String absolute_path = this.readProperties().getProperty("resource.absolute.path");

    /**
     * 创建PDF报告（通用）
     * 
     * @author zcq
     * @param reportForm
     * @return
     */
    public String create(ReportForm reportForm) {
        // 获取报告基本信息
        String pdfPath = reportForm.getReportPath();
        String reportId = reportForm.getReportCode();
        String reportItem = reportForm.getReportItem();
        String createDate = reportForm.getCreateDate();
        String insId = reportForm.getInsId();
        // 校验数据
        if (StringUtils.isEmpty(reportId)) {
            throw new NullPointerException("报告编码未设置！");
        } else if (StringUtils.isEmpty(reportItem)) {
            throw new NullPointerException("报告项目未设置！");
        } else if (StringUtils.isEmpty(insId)) {
            insId = "000000";
        }
        // 新生成路径 或 重新生成PDF
        if (StringUtils.isEmpty(pdfPath)) {
            String reportName = reportId + "_" + ReportConstant.itemReportMap.get(reportItem);
            if (StringUtils.isEmpty(createDate)) {
                createDate = JodaTimeTools.toString(new Date(), JodaTimeTools.FORMAT_6);
            }
            reportForm.setReportName(reportName);

            // 生成路径
            String dietReportPath = PathConstant.PDF_PATH + insId + "/" + reportItem + "/"
                    + createDate + "/";// 报告路径
            new File(absolute_path + dietReportPath).mkdirs();
            pdfPath = dietReportPath + reportName;
            reportForm.setReportPath(pdfPath);

            // 创建PDF
            this.createPdf(reportForm);
        } else {
            String path = absolute_path + pdfPath;
            if (!new File(path).exists()) {
                new File(path.substring(0, path.lastIndexOf("/"))).mkdirs();
                // 创建PDF
                this.createPdf(reportForm);
            }
        }

        return reportForm.getReportPath();
    }

    /**
     * 检测项目创建PDF报告（专用）
     * 
     * @author zcq
     * @param reportId
     * @return
     */
    public String create(String reportId) {
        ReportServiceImpl reportService = (ReportServiceImpl) CacheProjectInfo.getInstance()
                .getApplicationContext().getBean("reportServiceImpl");
        // 校验数据
        if (StringUtils.isEmpty(reportId)) {
            throw new NullPointerException("报告编码未设置！");
        }
        // 获取分析报告信息
        ReportPojo reportPojo = reportService.getReport(reportId);
        String reportPdfPath = reportPojo.getReportPdfPath();
        // 设置ReportForm
        ReportForm reportForm = new ReportForm();
        reportForm.setReportCode(reportId);
        reportForm.setReportPath(reportPdfPath);
        reportForm.setInsId(reportService.getInsId());
        // 新生成路径 或 重新生成PDF
        if (StringUtils.isEmpty(reportPdfPath)) {
            String reportName = reportId + "_" + ReportConstant.itemReportMap.get(reportPojo.getQuestionId());
            String createDate = "";
            if (reportPojo.getReportDate() == null) {
                createDate = JodaTimeTools.toString(new Date(), JodaTimeTools.FORMAT_6);
            } else {
                createDate = JodaTimeTools.toString(reportPojo.getReportDate(), JodaTimeTools.FORMAT_6);
            }
            reportForm.setReportName(reportName);

            // 生成路径
            String dietReportPath = PathConstant.PDF_PATH + reportService.getInsId() + "/"
                    + reportPojo.getQuestionId() + "/" + createDate + "/";// 报告路径
            new File(absolute_path + dietReportPath).mkdirs();
            reportPdfPath = dietReportPath + reportName;
            reportForm.setReportPath(reportPdfPath);

            // 创建PDF
            this.createPdf(reportForm);

            // 完善结果主表PDF报告路径
            Report report = new Report();
            report.setReportId(reportId);
            report.setReportPdfPath(reportPdfPath);
            reportService.updateReport(report);
        } else {
            String path = absolute_path + reportPdfPath;
            // TODO if (!new File(path).exists()) {
            new File(path.substring(0, path.lastIndexOf("/"))).mkdirs();
            // 创建PDF
            this.createPdf(reportForm);
            // TODO }
        }

        return reportForm.getReportPath();
    }

    /**
     * 创建PDF报告
     * 
     * @author zcq
     * @param reportForm
     */
    public void createPdf(ReportForm reportForm) {
        FileOutputStream out = null;
        try {
            // 第一步：数据准备
            T t = this.beforeCreatePdf(reportForm);

            // 第二步：初始化 document 和 write
            utils = PdfUtils.getIns();// 获取工具类实例
            document = new Document();// 创建PDF
            document.setMargins(utils.marginLeft, utils.marginRight, utils.marginTop, utils.marginBottom);// 设置PDF边距
            if (reportForm == null) {
                throw new DocumentException("输入参数reportForm是空的");
            }
            out = new FileOutputStream(absolute_path + reportForm.getReportPath());// 文件流
            writer = PdfWriter.getInstance(document, out);// 创建模板
            writer.setPageEvent(new PdfPageEventHelper(){// 设置页眉与页脚

                public void onEndPage(PdfWriter writer, Document document) {
                    PdfContentByte cb = writer.getDirectContent();
                    cb.saveState();
                    cb.beginText();
                    // BaseFont bf = null;
                    // try {
                    // bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                    cb.setFontAndSize(utils.createFont(), 10);
                    // // Header
                    // float x = document.top(-20);
                    // // 左
                    // cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "H-Left", document.left(), x, 0);
                    // // 中
                    // cb.showTextAligned(PdfContentByte.ALIGN_CENTER, writer.getPageNumber() + " page",
                    // (document.right() + document.left()) / 2, x, 0);
                    // // 右
                    // cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "H-Right", document.right(), x, 0);

                    // Footer
                    float y = document.bottom(-17f);
                    // // 左
                    // cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "F-Left", document.left(), y, 0);
                    // 中
                    if (writer.getPageNumber() == 1) {
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "【本报告非医疗诊断文书，任何医疗行为请遵医嘱。】",
                                (document.right() + document.left()) / 2, y + 17f, 0);
                    }
                    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "第 " + writer.getPageNumber() + " 页",
                            (document.right() + document.left()) / 2, y, 0);
                    // // 右
                    // cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "F-right", document.right(), y, 0);

                    cb.endText();
                    cb.restoreState();
                }
            });
            document.open();// 打开

            // 第三步：PDF数据处理
            this.handler(t);

            // 第四步：PDF创建完，后续关联数据处理
            this.afterCreatePdf(reportForm);

        } catch (FileNotFoundException e) {
            throw new RestfulException(ResultCode.ERROR_60001);
        } catch (DocumentException | RestfulException e) {
            // throw new RestfulException(ResultCode.ERROR_60002);
            throw new RestfulException(ResultCode.ERROR_80000, "没有");
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                reportForm.setReportPath("");
                if (e.getMessage().indexOf("The document has no pages") > -1) {
                    System.out.println("生成PDF报告时没有检索到内容！");
                    throw new RestfulException(ResultCode.ERROR_60003);
                } else {
                    throw new RestfulException(ResultCode.ERROR_90000);
                }
            }
        }
    }

    /**
     * 生成PDF
     * 
     * @author zcq
     * @param pdfData
     * @throws DocumentException
     */
    public abstract void handler(T pdfData) throws DocumentException;

    /**
     * 数据准备
     * 
     * @author zcq
     * @param reportForm
     * @return
     */
    public T beforeCreatePdf(ReportForm reportForm) {
        return null;
    }

    /**
     * 后续关联数据处理
     * 
     * @author zcq
     * @param reportForm
     */
    public void afterCreatePdf(ReportForm reportForm) {

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
