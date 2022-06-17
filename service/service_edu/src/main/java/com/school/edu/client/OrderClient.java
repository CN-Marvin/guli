package com.school.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.client
 * @author: Marvin-zl
 * @date: 2022/6/7 23:06
 */
@Component
@FeignClient("service-order")
public interface OrderClient {
    /**
     * 根据课程id和用户id查询订单状态
     * @param courseId 课程id
     * @param memberId 用户id
     * @return 是否购买课程
     */
    @GetMapping("/orderservice/order/getOrderStatus/{courseId}/{memberId}")
    boolean getOrderStatus(@PathVariable("courseId") String courseId,
                           @PathVariable("memberId") String memberId);
}
