package com.guxingyuan.quatz.utils;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.utils.Key;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: zhaoaolin
 * @Date: 2024/1/5 11:00
 */
public class QuartzHelper {


    private final Scheduler scheduler;

    public QuartzHelper(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 创建间隔任务, 立即执行
     *
     * @param jobClass
     * @param jobName
     * @param jobGroup
     * @param intervalValue
     * @param timeUnit
     * @param jobDataMap
     * @throws SchedulerException
     */
    public void addIntervalJob(Class<? extends Job> jobClass, String jobName, String jobGroup, int intervalValue, TimeUnit timeUnit, JobDataMap jobDataMap) throws SchedulerException {
        addIntervalJob(jobClass, jobName, jobGroup, intervalValue, timeUnit, null, jobDataMap);
    }

    /**
     * 创建间隔任务, 指定时间开始执行
     *
     * @param jobClass
     * @param jobName
     * @param jobGroup
     * @param intervalValue
     * @param timeUnit
     * @param startTime
     * @param jobDataMap
     * @throws SchedulerException
     */
    public void addIntervalJob(Class<? extends Job> jobClass, String jobName, String jobGroup, int intervalValue, TimeUnit timeUnit, Date startTime, JobDataMap jobDataMap) throws SchedulerException {
        JobBuilder jobBuilder = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup);
        if (jobDataMap != null) {
            jobBuilder.usingJobData(jobDataMap);
        }
        JobDetail jobDetail = jobBuilder.build();

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .repeatForever()   // 指定无限期重复
//                .withRepeatCount(10) // 重复次数 triggerRepeatCount+1次
                .withMisfireHandlingInstructionFireNow();  //  如果错过执行时间，立即执行

        if (TimeUnit.SECONDS == timeUnit) {
            scheduleBuilder.withIntervalInSeconds(intervalValue);
        } else if (TimeUnit.MINUTES == timeUnit) {
            scheduleBuilder.withIntervalInMinutes(intervalValue);
        } else if (TimeUnit.HOURS == timeUnit) {
            scheduleBuilder.withIntervalInHours(intervalValue);
        } else if (TimeUnit.DAYS == timeUnit) {
            scheduleBuilder.withIntervalInHours(intervalValue * 24);
        } else {
            throw new RuntimeException("Unknown TimeUnit Type");
        }

        TriggerBuilder<SimpleTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(scheduleBuilder);

        if (startTime == null) {
            triggerBuilder.startNow();
        } else {
            triggerBuilder.startAt(startTime);
        }

        scheduler.scheduleJob(jobDetail, triggerBuilder.build());
    }

    /**
     * 创建Cron任务
     *
     * @param jobClass
     * @param jobName
     * @param jobGroup
     * @param cron
     * @throws SchedulerException
     */
    public void addCronJob(Class<? extends Job> jobClass, String jobName, String jobGroup, String cron, JobDataMap jobDataMap) throws SchedulerException {
        addCronJob(jobClass, jobName, jobGroup, cron, null, jobDataMap);
    }

    /**
     * 创建Cron任务, 指定时间开始按照Cron执行
     *
     * @param jobClass
     * @param jobName
     * @param jobGroup
     * @param cron
     * @param startTime
     * @throws SchedulerException
     */
    public void addCronJob(Class<? extends Job> jobClass, String jobName, String jobGroup, String cron, Date startTime, JobDataMap jobDataMap) throws SchedulerException {
        JobBuilder jobBuilder = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup);
        if (jobDataMap != null) {
            jobBuilder.usingJobData(jobDataMap);
        }
        JobDetail jobDetail = jobBuilder.build();

        // 触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup);

        if (null != startTime) {
            triggerBuilder.startAt(startTime);
        } else {
            triggerBuilder.startNow();
        }

        // 触发器时间设定
        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
        // 创建Trigger对象
        CronTrigger trigger = (CronTrigger) triggerBuilder.build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 中断任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public void interrupt(String jobName, String jobGroup) throws UnableToInterruptJobException {
        scheduler.interrupt(JobKey.jobKey(jobName, jobGroup));
    }

    /**
     * 移除任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void removeJob(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
    }

    /**
     * 组内所有任务名
     *
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public List<String> listJob(String jobGroup) throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroup));
        return jobKeys.stream().map(Key::getName).collect(Collectors.toList());
    }

    /**
     * 获取任务参数
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public JobDataMap getJobDataMap(String jobName, String jobGroup) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroup));
        return jobDetail.getJobDataMap();
    }

    /**
     * 获取任务触发器
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public List<? extends Trigger> getJobTrigger(String jobName, String jobGroup) throws SchedulerException {
        return scheduler.getTriggersOfJob(JobKey.jobKey(jobName, jobGroup));
    }

    /**
     * 立即执行一次
     *
     * @param jobName
     * @throws SchedulerException
     */
    public void executeNow(String jobName, String jobGroup) throws SchedulerException {
        scheduler.triggerJob(JobKey.jobKey(jobName, jobGroup));
    }

    /**
     * 校验任务是否存在
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public boolean exist(String jobName, String jobGroup) throws SchedulerException {
        return scheduler.checkExists(JobKey.jobKey(jobName, jobGroup));
    }

}
