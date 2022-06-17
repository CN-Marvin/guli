package com.school.statistics.schedule;

import com.school.statistics.service.StatisticsDailyService;
import com.school.statistics.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 功能描述：
 *
 * @Package: com.school.statistics.schedule
 * @author: Marvin-zl
 * @date: 2022/6/8 16:04
 */
@Component
public class ScheduledTask {

    @Resource
    private StatisticsDailyService statisticsDailyService;


    /**
     * 每天1点，把前一天数据统计出来
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //前一天的数据
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.getCountRegister(day);

    }
}
