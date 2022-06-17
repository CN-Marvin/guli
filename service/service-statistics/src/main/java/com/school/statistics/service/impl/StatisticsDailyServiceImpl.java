package com.school.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.common.utils.R;
import com.school.statistics.client.CenterClient;
import com.school.statistics.entity.StatisticsDaily;
import com.school.statistics.mapper.StatisticsDailyMapper;
import com.school.statistics.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-08
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Resource
    private CenterClient centerClient;

    @Override
    public void getCountRegister(String day) {
        QueryWrapper<StatisticsDaily> statisticsDailyQueryWrapper = new QueryWrapper<>();
        statisticsDailyQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(statisticsDailyQueryWrapper);


        R r = centerClient.countRegister(day);
        Integer countRegister = (Integer) r.getData().get("count");

        StatisticsDaily sta = new StatisticsDaily();

        sta.setRegisterNum(countRegister);
        sta.setDateCalculated(day);
        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setVideoViewNum(RandomUtils.nextInt(100, 200));
        sta.setCourseNum(RandomUtils.nextInt(100, 200));
        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> showChart(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> statisticsDailyQueryWrapper = new QueryWrapper<>();
        statisticsDailyQueryWrapper.select(type, "date_calculated").between("date_calculated", begin, end);
        List<StatisticsDaily> dailyList = baseMapper.selectList(statisticsDailyQueryWrapper);
        dailyList.forEach(System.out::println);

        Map<String, Object> map = new HashMap<>(2);
        List<Integer> numList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        map.put("numList", numList);
        map.put("dateList",dateList);
        for (StatisticsDaily sta : dailyList) {
            dateList.add(sta.getDateCalculated());
            switch (type) {
                case "register_num":
                    numList.add(sta.getRegisterNum());
                    break;
                case "login_num":
                    numList.add(sta.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(sta.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(sta.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
