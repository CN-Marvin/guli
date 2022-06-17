package com.school.order.client;

import com.school.common.utils.ordervo.CenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 功能描述：
 *
 * @Package: com.school.order.client
 * @author: Marvin-zl
 * @date: 2022/6/6 22:48
 */
@Component
@FeignClient("service-ucenter")
public interface CenterClient {
    /**
     * 根据用户id获得用户信息
     * @param id
     * @return
     */
    @GetMapping("/ucenterservice/member/getMemberInfo/{id}")
    CenterMember getMemberInfoById(@PathVariable("id") String id);
}
