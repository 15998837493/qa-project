
package com.qa.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.qa.main.entity.MappedEntity;

/**
 * 问卷问题明细表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-23 zcq 初版
 */
@Entity
@Table(name = "mas_question_problems")
public class QuestionProblem extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 问题编号 */
    private String problemId;

    /** 所属问卷 */
    @UpdateAnnotation
    private String questionId;

    /** 问题内容 */
    @UpdateAnnotation
    private String problemContent;

    /** 问题类型 ：1 单选 2 多选 3 简答 4 是非题 */
    @UpdateAnnotation
    private String problemType;

    /** 问题筛选性别 */
    @UpdateAnnotation
    private String problemSex;

    /** 父问题 */
    @UpdateAnnotation
    private String problemParentId;

    /** 是否为子问题 */
    @UpdateAnnotation
    private Integer problemIsChildren;

    /** 子问题显示条件 */
    @UpdateAnnotation
    private String problemShowRule;

    /** 问题排序 */
    @UpdateAnnotation
    private Integer problemOrder;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "problem_id")
    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    @Column(name = "problem_content")
    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    @Column(name = "problem_type")
    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    @Column(name = "problem_sex")
    public String getProblemSex() {
        return problemSex;
    }

    public void setProblemSex(String problemSex) {
        this.problemSex = problemSex;
    }

    @Column(name = "question_id")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Column(name = "problem_parent_id")
    public String getProblemParentId() {
        return problemParentId;
    }

    public void setProblemParentId(String problemParentId) {
        this.problemParentId = problemParentId;
    }

    @Column(name = "problem_is_children")
    public Integer getProblemIsChildren() {
        return problemIsChildren;
    }

    public void setProblemIsChildren(Integer problemIsChildren) {
        this.problemIsChildren = problemIsChildren;
    }

    @Column(name = "problem_show_rule")
    public String getProblemShowRule() {
        return problemShowRule;
    }

    public void setProblemShowRule(String problemShowRule) {
        this.problemShowRule = problemShowRule;
    }

    @Column(name = "problem_order")
    public Integer getProblemOrder() {
        return problemOrder;
    }

    public void setProblemOrder(Integer problemOrder) {
        this.problemOrder = problemOrder;
    }

}
