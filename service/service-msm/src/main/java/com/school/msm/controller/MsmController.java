package com.school.msm.controller;

import com.school.common.utils.R;
import com.school.msm.service.MsmService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述：
 *
 * @Package: com.school.msm.controller
 * @author: Marvin-zl
 * @date: 2022/6/1 15:53
 */
@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Resource
    private MsmService msmService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone){
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        code = "1234";
        Map<String,Object> param = new HashMap<>(16);
        param.put("code", code);
        if(msmService.send(param,phone)){
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("发送失败");
        }
    }
}
