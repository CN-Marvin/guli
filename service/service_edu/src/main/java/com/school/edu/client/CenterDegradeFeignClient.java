package com.school.edu.client;

import com.school.common.utils.ordervo.CenterMember;
import com.school.guli.config.handler.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.client
 * @author: Marvin-zl
 * @date: 2022/6/6 15:56
 */
@Component
public class CenterDegradeFeignClient implements CenterClient{


    @Override
    public CenterMember getMemberInfoById(String id) {
        throw new GuliException();
    }
}
