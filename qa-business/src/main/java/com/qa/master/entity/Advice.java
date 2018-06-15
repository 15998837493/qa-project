
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
 * 建议
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-6 zcq 初版
 */
@Entity
@Table(name = "mas_advice")
public class Advice extends MappedEntity {

    private static final long serialVersionUID = 6374057429745991593L;

    /** 主键 */
    @UpdateAnnotation
    private String id;

    /** 类型 */
    @UpdateAnnotation
    private String adviceType;

    /** 子类 */
    @UpdateAnnotation
    private String adviceSubclass;

    /** 标题 */
    @UpdateAnnotation
    private String adviceTitle;

    /** 内容 */
    @UpdateAnnotation
    private String adviceContent;

    /** 排序 */
    @UpdateAnnotation
    private Integer adviceOrder;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "advice_type")
    public String getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(String adviceType) {
        this.adviceType = adviceType;
    }

    @Column(name = "advice_subclass")
    public String getAdviceSubclass() {
        return adviceSubclass;
    }

    public void setAdviceSubclass(String adviceSubclass) {
        this.adviceSubclass = adviceSubclass;
    }

    @Column(name = "advice_title")
    public String getAdviceTitle() {
        return adviceTitle;
    }

    public void setAdviceTitle(String adviceTitle) {
        this.adviceTitle = adviceTitle;
    }

    @Column(name = "advice_content")
    public String getAdviceContent() {
        return adviceContent;
    }

    public void setAdviceContent(String adviceContent) {
        this.adviceContent = adviceContent;
    }

    @Column(name = "advice_order")
    public Integer getAdviceOrder() {
        return adviceOrder;
    }

    public void setAdviceOrder(Integer adviceOrder) {
        this.adviceOrder = adviceOrder;
    }

}
