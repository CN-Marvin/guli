package com.school.statistics.controller;


import com.school.common.utils.R;
import com.school.statistics.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-08
 */
@RestController
@RequestMapping("/staservice/sta")

public class StatisticsDailyController {
    @Resource
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation("统计某一天的注册人数")
    @GetMapping("getCountRegister/{day}")
    public R getCountRegister(@PathVariable String day){
        statisticsDailyService.getCountRegister(day);
        return R.ok();
    }

    @ApiOperation("展示图表数据")
    @GetMapping("showChart/{type}/{begin}/{end}")
    public R showChart(@PathVariable("type") String type,
                       @PathVariable("begin") String begin,
                       @PathVariable("end") String end){
        Map<String,Object> map = statisticsDailyService.showChart(type,begin,end);
        return R.ok().data(map);
    }

}

