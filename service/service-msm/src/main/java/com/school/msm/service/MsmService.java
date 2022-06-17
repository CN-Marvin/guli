package com.school.msm.service;

import java.util.Map;

/**
 * 功能描述：
 *
 * @Package: com.school.msm.service
 * @author: Marvin-zl
 * @date: 2022/6/1 15:53
 */
public interface MsmService {
    /**
     * 发送短信
     * @param param
     * @param phone
     * @return
     */
    boolean send(Map<String, Object> param, String phone);
}
