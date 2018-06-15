
package com.qa.examitem.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 问卷答案记录表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-24 zcq 初版
 */
public class ReportAnswerPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 答案编号 */
    @QueryFieldAnnotation
    private String id;

    /** 问卷记录编号 */
    @QueryFieldAnnotation
    private String reportId;

    /** 问卷编号 */
    @QueryFieldAnnotation
    private String questionId;

    /** 问题编号 */
    @QueryFieldAnnotation
    private String problemId;

    /** 选项编号 */
    @QueryFieldAnnotation
    private String problemOptionId;

    /** 填写内容 */
    @QueryFieldAnnotation
    private String answerContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getProblemOptionId() {
        return problemOptionId;
    }

    public void setProblemOptionId(String problemOptionId) {
        this.problemOptionId = problemOptionId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

}
