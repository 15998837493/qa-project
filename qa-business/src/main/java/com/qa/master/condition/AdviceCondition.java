
package com.qa.master.condition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 生活方式建议查询条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-9 zcq 初版
 */
public class AdviceCondition implements Serializable {

    private static final long serialVersionUID = -6319783025844304652L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String adviceType;

    /** 子类 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String adviceSubclass;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
