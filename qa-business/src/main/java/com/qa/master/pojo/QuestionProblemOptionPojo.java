
package com.qa.master.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 问题选项
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-10 zcq 初版
 */
public class QuestionProblemOptionPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 选项编号 */
    @QueryFieldAnnotation
    private String problemOptionId;

    /** 所属问题 */
    @QueryFieldAnnotation
    private String problemId;

    /** 选项类型1=填空，2=选项 */
    @QueryFieldAnnotation
    private String optionType;

    /** 选项内容 */
    @QueryFieldAnnotation
    private String optionContent;

    /** 选项验证 */
    @QueryFieldAnnotation
    private String optionValidate;

    /** 适合性别 */
    @QueryFieldAnnotation
    private String optionSex;

    public String getProblemOptionId() {
        return problemOptionId;
    }

    public void setProblemOptionId(String problemOptionId) {
        this.problemOptionId = problemOptionId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getOptionValidate() {
        return optionValidate;
    }

    public void setOptionValidate(String optionValidate) {
        this.optionValidate = optionValidate;
    }

    public String getOptionSex() {
        return optionSex;
    }

    public void setOptionSex(String optionSex) {
        this.optionSex = optionSex;
    }

}
