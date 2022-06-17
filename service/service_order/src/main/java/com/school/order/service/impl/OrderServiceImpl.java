package com.school.order.service.impl;

import com.school.common.utils.ordervo.CenterMember;
import com.school.common.utils.ordervo.CourseOrderVo;
import com.school.order.client.CenterClient;
import com.school.order.client.CourseClient;
import com.school.order.entity.Order;
import com.school.order.mapper.OrderMapper;
import com.school.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private CenterClient centerClient;

    @Resource
    private CourseClient courseClient;
    @Override
    public String createOrder(String courseId, String memberId) {
        Order order = new Order();

        CenterMember member = centerClient.getMemberInfoById(memberId);
        CourseOrderVo course = courseClient.getCourseInfoOrder(courseId);
        order.setOrderNo(UUID.randomUUID().toString().substring(19));
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherName(course.getTeacherName());
        order.setMemberId(memberId);
        order.setNickname(member.getNickname());
        order.setMobile(member.getMobile());
        order.setTotalFee(course.getPrice());
        order.setPayType(1);
        order.setStatus(0);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
