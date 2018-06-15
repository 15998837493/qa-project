
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
import com.qa.examitem.condition.ReportResultCondition;
import com.qa.examitem.pojo.ReportResultPojo;

/**
 * 分析结果
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-21 zcq 初版
 */
@Repository
public class ReportResultDAO extends HibernateTemplate {

    /**
     * 条件检索分析结果
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<ReportResultPojo> queryReportResult(ReportResultCondition condition) {
        if (condition == null || StringUtils.isEmpty(condition.getTableName())) {
            return null;
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ReportResultPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(ReportResultPojo.class, "ReportResultPojo")
                + "   FROM " + condition.getTableName() + " AS ReportResultPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ReportResultPojo.class);
    }

    /**
     * 保存分析结果
     * 
     * @author zcq
     * @param reportResultPojo
     * @param tableName
     * @param insId
     * @return
     */
    public String addReportResult(ReportResultPojo reportResultPojo, String tableName, String insId) {
        if (reportResultPojo == null || StringUtils.isEmpty(reportResultPojo.getReportId())) {
            throw new NullPointerException("传入数据为空 或 报告编码为空！");
        }
        String id = UUID.randomUUID().toString();
        reportResultPojo.setId(id);
        String sql = getInsertSQL(reportResultPojo, tableName, insId);
        this.executeSQL(sql);
        return id;
    }

    /**
     * 删除原来的分析结果
     * 
     * @author zcq
     * @param tableName
     * @param reportId
     */
    public void deleteReportResultByReportId(String tableName, String reportId) {
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
     * @param reportResultPojo
     * @param tableName
     * @param insId
     * @return
     */
    private String getInsertSQL(ReportResultPojo reportResultPojo, String tableName, String insId) {
        String insertSQL = "";
        String colums = "";
        String values = "";
        Field[] fields = ReportResultPojo.class.getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            if (field.isAnnotationPresent(QueryFieldAnnotation.class)) {
                try {
                    String fieldName = field.getName();
                    Object obj = field.get(reportResultPojo);
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
