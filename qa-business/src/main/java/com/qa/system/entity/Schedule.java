
package com.qa.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.qa.main.entity.MappedEntity;

/**
 * 功能菜单表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "mas_pregnancy_course_schedule")
public class Schedule extends MappedEntity {

    private static final long serialVersionUID = -2554714064303106751L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @NotBlank
    private String scheduleId;

    /** 周 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String scheduleWeek;

    /** 上/下午 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String scheduleNoon;

    /** 课程允许最大人数 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private Integer scheduleMaxPerson;

    /** 课程内容 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String scheduleContent;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "schedule_id")
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Column(name = "schedule_week")
    public String getScheduleWeek() {
        return scheduleWeek;
    }

    public void setScheduleWeek(String scheduleWeek) {
        this.scheduleWeek = scheduleWeek;
    }

    @Column(name = "schedule_noon")
    public String getScheduleNoon() {
        return scheduleNoon;
    }

    public void setScheduleNoon(String scheduleNoon) {
        this.scheduleNoon = scheduleNoon;
    }

    @Column(name = "schedule_content")
    public String getScheduleContent() {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }

    @Column(name = "schedule_max_person")
    public Integer getScheduleMaxPerson() {
        return scheduleMaxPerson;
    }

    public void setScheduleMaxPerson(Integer scheduleMaxPerson) {
        this.scheduleMaxPerson = scheduleMaxPerson;
    }

}
