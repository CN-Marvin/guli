package com.school.statistics.service;

import com.school.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-08
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 统计某一天的注册人数
     * @param day
     */
    void getCountRegister(String day);

    /**
     * 展示图表的顺序
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> showChart(String type, String begin, String end);
}
