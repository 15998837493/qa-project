
package com.qa.controller.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;

import com.mnt.health.utils.pdf.ReportForm;

public class PdfTest {

    /**
     * PDF测试
     * 
     * @author zcq
     * @param args
     */
    public static void main(String[] args) {
        DiseaseRiskPdf pdf = new DiseaseRiskPdf();

        ReportForm reportForm = new ReportForm();
        reportForm.setReportPath("framinghamPdf.pdf");
        try {
            pdf.createPdf(reportForm);
            System.out.println("生成PDF成功！");
            Desktop.getDesktop().open(new File("D:/apache-tomcat-7.0.6/webapps/qa-web/framinghamPdf.pdf"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
