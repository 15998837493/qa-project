
package com.qa.master.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.qa.main.entity.MappedEntity;

/**
 * 指标信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-28 zcq 初版
 */
@Entity
@Table(name = "mas_item")
public class Item extends MappedEntity {

    private static final long serialVersionUID = 6374057429745991593L;

    /** 主键 */
    private String id;

    /** 指标编号 */
    @UpdateAnnotation
    private String itemCode;

    /** 指标名称 */
    @UpdateAnnotation
    private String itemName;

    /** 指标名称（机构） */
    @UpdateAnnotation
    private String itemInsName;

    /** 性别 */
    @UpdateAnnotation
    private String itemSex;

    /** 参考值（文本） */
    @UpdateAnnotation
    private String itemRefStr;

    /** 参考值（下限） */
    @UpdateAnnotation
    private BigDecimal itemRefValMin;

    /** 参考值（上限） */
    @UpdateAnnotation
    private BigDecimal itemRefValMax;

    /** 比较 */
    @UpdateAnnotation
    private String itemCompare;

    /** 指标单位 */
    @UpdateAnnotation
    private String itemUnit;

    /** 指标值类型 */
    @UpdateAnnotation
    private String itemValueType;

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

    @Column(name = "item_code")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(name = "item_ins_name")
    public String getItemInsName() {
        return itemInsName;
    }

    public void setItemInsName(String itemInsName) {
        this.itemInsName = itemInsName;
    }

    @Column(name = "item_sex")
    public String getItemSex() {
        return itemSex;
    }

    public void setItemSex(String itemSex) {
        this.itemSex = itemSex;
    }

    @Column(name = "item_ref_str")
    public String getItemRefStr() {
        return itemRefStr;
    }

    public void setItemRefStr(String itemRefStr) {
        this.itemRefStr = itemRefStr;
    }

    @Column(name = "item_ref_val_min")
    public BigDecimal getItemRefValMin() {
        return itemRefValMin;
    }

    public void setItemRefValMin(BigDecimal itemRefValMin) {
        this.itemRefValMin = itemRefValMin;
    }

    @Column(name = "item_ref_val_max")
    public BigDecimal getItemRefValMax() {
        return itemRefValMax;
    }

    public void setItemRefValMax(BigDecimal itemRefValMax) {
        this.itemRefValMax = itemRefValMax;
    }

    @Column(name = "item_compare")
    public String getItemCompare() {
        return itemCompare;
    }

    public void setItemCompare(String itemCompare) {
        this.itemCompare = itemCompare;
    }

    @Column(name = "item_unit")
    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    @Column(name = "item_value_type")
    public String getItemValueType() {
        return itemValueType;
    }

    public void setItemValueType(String itemValueType) {
        this.itemValueType = itemValueType;
    }

}
