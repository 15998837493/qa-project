
package com.qa.controller.request;

import java.io.Serializable;
import java.util.List;

public class QueryReportData implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> custCodeList;

    private String reportDate;

    public List<String> getCustCodeList() {
        return custCodeList;
    }

    public void setCustCodeList(List<String> custCodeList) {
        this.custCodeList = custCodeList;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

}
