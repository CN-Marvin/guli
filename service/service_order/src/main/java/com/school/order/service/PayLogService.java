package com.school.order.service;

import com.school.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-06
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生产二维码
     * @param orderNo
     * @return
     */
    Map<String, Object> createNative(String orderNo);

    /**
     * 根据订单号查询订单状态
     * @param orderNo
     * @return
     */
    Map<String, String> getPayStatus(String orderNo);

    /**
     * 更新订单支付状态
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);
}
