package com.school.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.school.guli.config.handler.exceptionhandler.GuliException;
import com.school.order.entity.Order;
import com.school.order.entity.PayLog;
import com.school.order.mapper.PayLogMapper;
import com.school.order.service.OrderService;
import com.school.order.service.PayLogService;
import com.school.order.utils.HttpClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-06
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Resource
    private OrderService orderService;

    @Override
    public Map<String, Object> createNative(String orderNo) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(orderQueryWrapper);

        Map<String, String> map = new HashMap<>(16);
        //1、设置支付参数
        map.put("appid", "wx74862e0dfcf69954");
        map.put("mch_id", "1558950191");
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("body", order.getCourseTitle());
        map.put("out_trade_no", orderNo);
        map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
        map.put("spbill_create_ip", "127.0.0.1");
        map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
        map.put("trade_type", "NATIVE");
        //2、HTTPClient来根据URL访问第三方接口并且传递参数
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //4、封装返回结果集
            Map<String, Object> resMap = new HashMap<>(16);
            resMap.put("out_trade_no", orderNo);
            resMap.put("course_id", order.getCourseId());
            resMap.put("total_fee", order.getTotalFee());
            resMap.put("result_code", resultMap.get("result_code"));
            resMap.put("code_url", resultMap.get("code_url"));
            return resMap;
        } catch (Exception e) {
            throw new GuliException(20001, "error");
        }
    }

    @Override
    public Map<String, String> getPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map<String, String> m = new HashMap<>(16);
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        if(order.getStatus().intValue() == 1) {return;}
        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志
        PayLog payLog = new PayLog();
        //支付订单号
        payLog.setOrderNo(order.getOrderNo());
        payLog.setPayTime(new Date());
        //支付类型
        payLog.setPayType(1);
        //总金额(分)
        payLog.setTotalFee(order.getTotalFee());
        //支付状态
        payLog.setTradeState(map.get("trade_state"));
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        //插入到支付日志表
        baseMapper.insert(payLog);
    }
}
