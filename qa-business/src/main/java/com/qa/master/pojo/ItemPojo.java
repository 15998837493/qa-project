
package com.qa.master.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 问卷指标
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-7 zcq 初版
 */
public class ItemPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 指标编号 */
    @QueryFieldAnnotation
    private String itemCode;

    /** 指标名称 */
    @QueryFieldAnnotation
    private String itemName;

    /** 指标名称（机构） */
    @QueryFieldAnnotation
    private String itemInsName;

    /** 性别 */
    @QueryFieldAnnotation
    private String itemSex;

    /** 参考值（文本） */
    @QueryFieldAnnotation
    private String itemRefStr;

    /** 参考值（下限） */
    @QueryFieldAnnotation
    private BigDecimal itemRefValMin;

    /** 参考值（上限） */
    @QueryFieldAnnotation
    private BigDecimal itemRefValMax;

    /** 比较 */
    @QueryFieldAnnotation
    private String itemCompare;

    /** 指标单位 */
    @QueryFieldAnnotation
    private String itemUnit;

    /** 指标值类型 */
    @QueryFieldAnnotation
    private String itemValueType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemInsName() {
        return itemInsName;
    }

    public void setItemInsName(String itemInsName) {
        this.itemInsName = itemInsName;
    }

    public String getItemSex() {
        return itemSex;
    }

    public void setItemSex(String itemSex) {
        this.itemSex = itemSex;
    }

    public String getItemRefStr() {
        return itemRefStr;
    }

    public void setItemRefStr(String itemRefStr) {
        this.itemRefStr = itemRefStr;
    }

    public BigDecimal getItemRefValMin() {
        return itemRefValMin;
    }

    public void setItemRefValMin(BigDecimal itemRefValMin) {
        this.itemRefValMin = itemRefValMin;
    }

    public BigDecimal getItemRefValMax() {
        return itemRefValMax;
    }

    public void setItemRefValMax(BigDecimal itemRefValMax) {
        this.itemRefValMax = itemRefValMax;
    }

    public String getItemCompare() {
        return itemCompare;
    }

    public void setItemCompare(String itemCompare) {
        this.itemCompare = itemCompare;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemValueType() {
        return itemValueType;
    }

    public void setItemValueType(String itemValueType) {
        this.itemValueType = itemValueType;
    }

}
