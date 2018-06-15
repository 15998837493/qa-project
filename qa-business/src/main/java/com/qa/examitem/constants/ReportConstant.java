
package com.qa.examitem.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 问卷常量
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-5 zcq 初版
 */
public class ReportConstant {

    /** 问卷编码 */
    public final static class REPORT_QUESTION {

        public final static String risk = "Q001";// 危险分层问卷项目编码
    }

    /** 问卷指标记录表 */
    public final static class REPORT_ITEM_TABLE {

        public static final String risk = "cus_report_risk_item";// 危险分层结果表

    }

    /** 问卷报告名称后缀 */
    public static final Map<String, String> itemReportMap = new HashMap<String, String>();
    static {
        itemReportMap.put("Q001", "risk.pdf");// 危险分层报告后缀名称
    }

}
