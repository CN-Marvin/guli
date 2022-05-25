package com.school.edu.controller;

import com.school.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.controller
 * @author: Marvin-zl
 * @date: 2022/5/25 14:57
 */
@Api(tags = "后台登录")
@RestController
@RequestMapping("/eduService/user")
@CrossOrigin
public class TeacherLoginController {
    @PostMapping("login")
    public R login(){
        return R.ok().data("token", "admin");
    }
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://seopic.699pic.com/photo/50061/8976.jpg_wh1200.jpg");
    }
}
