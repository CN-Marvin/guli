package com.school.order.client;

import com.school.common.utils.ordervo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 功能描述：
 *
 * @Package: com.school.order.client
 * @author: Marvin-zl
 * @date: 2022/6/6 22:11
 */
@Component
@FeignClient("service-edu")
public interface CourseClient {
    /**
     * 根据课程id查询
     * @param id
     * @return
     */
    @GetMapping("/eduService/courseFront/getCourseInfoOrder/{id}")
    CourseOrderVo getCourseInfoOrder(@PathVariable("id") String id);
}
