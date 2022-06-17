package com.school.edu.client;

import com.school.common.utils.ordervo.CenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.client
 * @author: Marvin-zl
 * @date: 2022/6/6 15:54
 */
@Component
@FeignClient(value = "service-ucenter",fallback = CenterDegradeFeignClient.class)
public interface CenterClient {
    /**
     * 根据id获得用户信息
     * @param id
     * @return
     */
    @GetMapping("/ucenterservice/member/getMemberInfo/{id}")
    CenterMember getMemberInfoById(@PathVariable("id") String id);
}
