package com.school.edu.client;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.client
 * @author: Marvin-zl
 * @date: 2022/6/8 0:06
 */
public class OrderDegradeFeignClient implements OrderClient{
    @Override
    public boolean getOrderStatus(String courseId, String memberId) {
        return false;
    }
}
