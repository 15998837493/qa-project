
package com.qa.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnt.health.utils.times.JodaTimeTools;

/**
 * 定时任务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-11 zcq 初版
 */
public class QuartzCenterJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzCenterJob.class);

    // 获取代码表缓存定时任务 服务启动10秒后第一次执行任务，之后每隔12小时执行一次

    // 定时任务工厂
    private SchedulerFactory sf = new StdSchedulerFactory();

    private Scheduler sched = null;

    // 触发器后缀名
    final String triggerEndName = "Trigger";

    // 代码表缓存定时任务组
    final String codeJobGroup = "code";

    Calendar startTime = JodaTimeTools.getCalendarAfterSecond(10);

    // 代码表缓存任务触发时间 【服务启动10秒后第一次执行任务，之后每隔12小时执行一次】
    final String code_trigger_time = startTime.get(Calendar.SECOND) + " " + startTime.get(Calendar.MINUTE) + " "
            + startTime.get(Calendar.HOUR) + "/12 * * ?";

    Calendar testTime = JodaTimeTools.getCalendarAfterSecond(30);

    public boolean start() {
        boolean bResult = false;
        try {
            // 启动链路定时任务
            sched = sf.getScheduler();
            JobDetail codejob = new JobDetail(CodeTimerJob.class.getSimpleName(), codeJobGroup, CodeTimerJob.class);
            CronTrigger codetrigger = new CronTrigger(CodeTimerJob.class.getSimpleName() + triggerEndName,
                    codeJobGroup + triggerEndName, CodeTimerJob.class.getSimpleName(),
                    codeJobGroup, code_trigger_time);
            sched.addJob(codejob, true);
            Date codeDate = sched.scheduleJob(codetrigger);
            /*
             * JobDetail job = new JobDetail(HXTokenTimerJob.class.getSimpleName(), hxJobGroup, HXTokenTimerJob.class);
             * CronTrigger trigger = new CronTrigger(HXTokenTimerJob.class.getSimpleName() + triggerEndName,
             * hxJobGroup + triggerEndName, HXTokenTimerJob.class.getSimpleName(),
             * hxJobGroup,
             * hxtoken_trigger_time);
             * sched.addJob(job, true);
             * Date hxDate = sched.scheduleJob(trigger);
             */

            // 启动
            sched.start();
            bResult = sched.isStarted();
            if (bResult) {
                LOGGER.info("启动-码表定时任务启动成功,启动时间" + JodaTimeTools.toString(codeDate, JodaTimeTools.FORMAT_2));
            } else {
                LOGGER.error("启动-定时任务失败!");
            }
        } catch (Exception e) {
            LOGGER.error("启动-定时任务失败,出现异常!", e);
        }
        return bResult;
    }

    public boolean shutdown() {
        try {
            if (sched != null && sched.isStarted()) {
                sched.shutdown();
                return sched.isShutdown();
            }
        } catch (SchedulerException e) {
            LOGGER.error("停止-定时任务失败,出现异常!", e);
        }
        return false;
    }

    public boolean isStart() {
        if (sched != null) {
            try {
                return sched.isStarted();
            } catch (SchedulerException e) {
                LOGGER.error("定时任务状态-取定时任务状态失败,出现异常!", e);
            }
        }
        return false;
    }

}
