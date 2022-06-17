package com.school.statistics.client;

import com.school.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 功能描述：
 *
 * @Package: com.school.statistics.client
 * @author: Marvin-zl
 * @date: 2022/6/8 14:47
 */
@Component
@FeignClient(value = "service-ucenter",fallback = CenterFallBackClient.class)
public interface CenterClient {
    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    @GetMapping("/ucenterservice/member/countRegister/{day}")
    R countRegister(@PathVariable("day") String day);
}
