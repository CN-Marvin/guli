package com.school.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.school.common.utils.JwtUtils;
import com.school.common.utils.R;
import com.school.order.entity.Order;
import com.school.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-06
 */
@Api(tags = "支付管理")
@RestController
@RequestMapping("/orderservice/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation("生产订单")
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId")String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return R.error().code(28004).message("请登录");
        }
        String orderNo = orderService.createOrder(courseId,memberId);
        return R.ok().data("orderId",orderNo);
    }

    @ApiOperation("根据订单号获得订单号")
    @GetMapping("getOrder/{orderId}")
    public R getOrder(@PathVariable("orderId")String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    @ApiOperation("根据课程id和用户id查询订单状态")
    @GetMapping("/getOrderStatus/{courseId}/{memberId}")
    public boolean getOrderStatus(@PathVariable String courseId,
                                  @PathVariable String memberId){
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("course_id", courseId);
        orderQueryWrapper.eq("member_id",memberId);
        orderQueryWrapper.eq("status", 1);
        int count = orderService.count(orderQueryWrapper);
        if (count > 0){
            return true;
        }else{
            return false;
        }
    }
}

