package com.school.statistics.client;

import com.school.common.utils.R;

/**
 * 功能描述：
 *
 * @Package: com.school.statistics.client
 * @author: Marvin-zl
 * @date: 2022/6/8 14:48
 */
public class CenterFallBackClient implements CenterClient{
    @Override
    public R countRegister(String day) {
        return R.error().code(20001).message("调用失败");
    }
}
