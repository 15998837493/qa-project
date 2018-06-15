
package com.qa.master.pojo;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 问卷问题明细表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-23 zcq 初版
 */
public class QuestionProblemPojo implements Serializable {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 问题编号 */
    @QueryFieldAnnotation
    private String problemId;

    /** 所属问卷 */
    @QueryFieldAnnotation
    private String questionId;

    /** 问题内容 */
    @QueryFieldAnnotation
    private String problemContent;

    /** 问题类型 ：1 单选 2 多选 3 简答 4 是非题 */
    @QueryFieldAnnotation
    private String problemType;

    /** 问题筛选性别 */
    @QueryFieldAnnotation
    private String problemSex;

    /** 父问题 */
    @QueryFieldAnnotation
    private String problemParentId;

    /** 是否为子问题 */
    @QueryFieldAnnotation
    private Integer problemIsChildren;

    /** 子问题显示条件 */
    @QueryFieldAnnotation
    private String problemShowRule;

    /** 问题排序 */
    @QueryFieldAnnotation
    private Integer problemOrder;

    /** 问题选项 */
    private List<QuestionProblemOptionPojo> optionList;

    /** 子问题 */
    private List<QuestionProblemPojo> sonProblemList;

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getProblemSex() {
        return problemSex;
    }

    public void setProblemSex(String problemSex) {
        this.problemSex = problemSex;
    }

    public String getProblemParentId() {
        return problemParentId;
    }

    public void setProblemParentId(String problemParentId) {
        this.problemParentId = problemParentId;
    }

    public Integer getProblemIsChildren() {
        return problemIsChildren;
    }

    public void setProblemIsChildren(Integer problemIsChildren) {
        this.problemIsChildren = problemIsChildren;
    }

    public String getProblemShowRule() {
        return problemShowRule;
    }

    public void setProblemShowRule(String problemShowRule) {
        this.problemShowRule = problemShowRule;
    }

    public Integer getProblemOrder() {
        return problemOrder;
    }

    public void setProblemOrder(Integer problemOrder) {
        this.problemOrder = problemOrder;
    }

    public List<QuestionProblemOptionPojo> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuestionProblemOptionPojo> optionList) {
        this.optionList = optionList;
    }

    public List<QuestionProblemPojo> getSonProblemList() {
        return sonProblemList;
    }

    public void setSonProblemList(List<QuestionProblemPojo> sonProblemList) {
        this.sonProblemList = sonProblemList;
    }

}
