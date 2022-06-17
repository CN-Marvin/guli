package com.school.ucenter.controller;


import com.school.common.utils.JwtUtils;
import com.school.common.utils.R;
import com.school.common.utils.ordervo.CenterMember;
import com.school.ucenter.entity.Member;
import com.school.ucenter.entity.vo.RegisterVo;
import com.school.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-02
 */
@Api(tags = "用户前台控制管理")
@RestController
@RequestMapping("/ucenterservice/member")

public class MemberController {
    @Resource
    private MemberService memberService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(@RequestBody Member member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation("根据token获得用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(id);
        return R.ok().data("userInfo",member);
    }

    @ApiOperation("根据用户id获得用户信息")
    @GetMapping("getMemberInfo/{id}")
    public CenterMember getMemberInfoById(@PathVariable("id") String id){
        Member member = memberService.getById(id);
        CenterMember centerMember = new CenterMember();
        BeanUtils.copyProperties(member, centerMember);
        return centerMember;
    }

    @ApiOperation("查询某一天注册人数")
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day){
        Integer count = memberService.countRegisterOneDay(day);
        return R.ok().data("count",count);
    }

}

