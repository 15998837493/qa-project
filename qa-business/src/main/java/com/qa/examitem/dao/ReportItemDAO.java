
package com.qa.examitem.dao;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.health.utils.times.JodaTimeTools;
import com.qa.examitem.condition.ReportItemCondition;
import com.qa.examitem.pojo.ReportItemPojo;

/**
 * 报告指标DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-29 zcq 初版
 */
@Repository
public class ReportItemDAO extends HibernateTemplate {

    /**
     * 条件检索检测项目指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<ReportItemPojo> queryReportItem(ReportItemCondition condition) {
        if (condition == null || StringUtils.isEmpty(condition.getTableName())) {
            return null;
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ReportItemPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(ReportItemPojo.class, "ReportItemPojo")
                + "   FROM " + condition.getTableName() + " AS ReportItemPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ReportItemPojo.class);
    }

    /**
     * 保存检测项目指标
     * 
     * @author zcq
     * @param reportItemPojo
     * @param tableName
     * @param insId
     * @return
     */
    public String addReportItem(ReportItemPojo reportItemPojo, String tableName, String insId) {
        String id = UUID.randomUUID().toString();
        reportItemPojo.setId(id);
        String sql = getInsertSQL(reportItemPojo, tableName, insId);
        this.executeSQL(sql);
        return id;
    }

    /**
     * 删除原来的指标数据
     * 
     * @author zcq
     * @param tableName
     * @param reportId
     */
    public void deleteReportItem(String tableName, String reportId) {
        if (StringUtils.isNotEmpty(tableName) && StringUtils.isNotEmpty(reportId)) {
            String sql = "DELETE FROM " + tableName + " WHERE report_id=:reportId";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("reportId", reportId);
            this.executeSQL(sql, paramMap);
        }
    }

    // *****************************************自定义工具方法******************************************
    /**
     * 获取添加SQL语句
     * 
     * @author zcq
     * @param reportItemPojo
     * @param tableName
     * @param insId
     * @return
     */
    private String getInsertSQL(ReportItemPojo reportItemPojo, String tableName, String insId) {
        String insertSQL = "";
        String colums = "";
        String values = "";
        Field[] fields = ReportItemPojo.class.getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            if (field.isAnnotationPresent(QueryFieldAnnotation.class)) {
                try {
                    String fieldName = field.getName();
                    Object obj = field.get(reportItemPojo);
                    String value = null;
                    if (obj != null) {
                        // 如果是日期类型，格式化为数据库兼容的值
                        if (obj instanceof Date) {
                            value = "'" + JodaTimeTools.toString((Date) obj, JodaTimeTools.FORMAT_2) + "'";
                        } else {
                            value = "'" + obj.toString() + "'";
                        }
                    }
                    colums += DaoUtils.nameFieldToColumn(fieldName) + ",";
                    values += value + ",";
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        colums += "create_user_id,update_user_id,flag,create_ins_id";
        values += "'0','0','1','" + insId + "'";
        insertSQL = "INSERT INTO " + tableName + "(" + colums + ") VALUES (" + values + ")";
        return insertSQL;
    }

}
