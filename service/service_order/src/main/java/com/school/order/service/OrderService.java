package com.school.order.service;

import com.school.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-06
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     * @param courseId
     * @param memberId
     * @return 订单号
     */
    String createOrder(String courseId, String memberId);
}
