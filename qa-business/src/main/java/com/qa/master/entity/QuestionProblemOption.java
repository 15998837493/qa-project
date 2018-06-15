
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
 * 问题选项表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-23 zcq 初版
 */
@Entity
@Table(name = "mas_question_problem_options")
public class QuestionProblemOption extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 选项编号 */
    private String problemOptionId;

    /** 所属问题 */
    @UpdateAnnotation
    private String problemId;

    /** 选项类型1=填空，2=选项 */
    @UpdateAnnotation
    private String optionType;

    /** 选项内容 */
    @UpdateAnnotation
    private String optionContent;

    /** 选项验证 */
    @UpdateAnnotation
    private String optionValidate;

    /** 适合性别 */
    @UpdateAnnotation
    private String optionSex;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "problem_option_id")
    public String getProblemOptionId() {
        return problemOptionId;
    }

    public void setProblemOptionId(String problemOptionId) {
        this.problemOptionId = problemOptionId;
    }

    @Column(name = "option_content")
    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    @Column(name = "option_type")
    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    @Column(name = "option_validate")
    public String getOptionValidate() {
        return optionValidate;
    }

    public void setOptionValidate(String optionValidate) {
        this.optionValidate = optionValidate;
    }

    @Column(name = "option_sex")
    public String getOptionSex() {
        return optionSex;
    }

    public void setOptionSex(String optionSex) {
        this.optionSex = optionSex;
    }

    @Column(name = "problem_id")
    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

}
