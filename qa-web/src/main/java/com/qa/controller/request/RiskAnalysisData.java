
package com.qa.controller.request;

import java.io.Serializable;
import java.util.List;

import com.qa.examitem.pojo.ReportAnswerPojo;
import com.qa.examitem.pojo.ReportItemPojo;
import com.qa.examitem.pojo.ReportPojo;

public class RiskAnalysisData implements Serializable {

    private static final long serialVersionUID = 1L;

    private ReportPojo customer;

    private List<ReportAnswerPojo> questionAnswerList;

    private List<ReportItemPojo> examItemList;

    public ReportPojo getCustomer() {
        return customer;
    }

    public void setCustomer(ReportPojo customer) {
        this.customer = customer;
    }

    public List<ReportAnswerPojo> getQuestionAnswerList() {
        return questionAnswerList;
    }

    public void setQuestionAnswerList(List<ReportAnswerPojo> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }

    public List<ReportItemPojo> getExamItemList() {
        return examItemList;
    }

    public void setExamItemList(List<ReportItemPojo> examItemList) {
        this.examItemList = examItemList;
    }

}
