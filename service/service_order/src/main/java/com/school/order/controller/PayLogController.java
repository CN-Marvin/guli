package com.school.order.controller;


import com.school.common.utils.R;
import com.school.order.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-06
 */
@RestController
@RequestMapping("/orderservice/paylog")
public class PayLogController {

    @Resource
    private PayLogService payService;

    @ApiOperation("生成二维码")
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map<String, Object> map = payService.createNative(orderNo);
        return R.ok().data(map);
    }

    @ApiOperation("查询支付状态")
    @GetMapping("/getPayStatus/{orderNo}")
    public R getPayStatus(@PathVariable("orderNo") String orderNo){
        Map<String,String> map = payService.getPayStatus(orderNo);
        if(map == null){
            return R.error().message("支付失败");
        }
        if("SUCCESS".equals(map.get("trade_state"))){
            payService.updateOrderStatus(map);
            return R.ok();
        }
        return R.ok().code(25000).message("支付中");
    }
}

