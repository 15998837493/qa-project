
package com.qa.master.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 生活方式建议
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-9 zcq 初版
 */
public class AdvicePojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 类型 */
    @QueryFieldAnnotation
    private String adviceType;

    /** 子类 */
    @QueryFieldAnnotation
    private String adviceSubclass;

    /** 标题 */
    @QueryFieldAnnotation
    private String adviceTitle;

    /** 内容 */
    @QueryFieldAnnotation
    private String adviceContent;

    /** 排序 */
    @QueryFieldAnnotation
    private Integer adviceOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(String adviceType) {
        this.adviceType = adviceType;
    }

    public String getAdviceSubclass() {
        return adviceSubclass;
    }

    public void setAdviceSubclass(String adviceSubclass) {
        this.adviceSubclass = adviceSubclass;
    }

    public String getAdviceTitle() {
        return adviceTitle;
    }

    public void setAdviceTitle(String adviceTitle) {
        this.adviceTitle = adviceTitle;
    }

    public String getAdviceContent() {
        return adviceContent;
    }

    public void setAdviceContent(String adviceContent) {
        this.adviceContent = adviceContent;
    }

    public Integer getAdviceOrder() {
        return adviceOrder;
    }

    public void setAdviceOrder(Integer adviceOrder) {
        this.adviceOrder = adviceOrder;
    }

}
