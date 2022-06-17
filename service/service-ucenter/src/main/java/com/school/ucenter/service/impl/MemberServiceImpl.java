package com.school.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.school.common.utils.JwtUtils;
import com.school.common.utils.MD5;
import com.school.guli.config.handler.exceptionhandler.GuliException;
import com.school.ucenter.entity.Member;
import com.school.ucenter.entity.vo.RegisterVo;
import com.school.ucenter.mapper.MemberMapper;
import com.school.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-02
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(Member member) {
        if(member == null){
            throw new GuliException(20001,"error");
        }
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"error");
        }
        Member member1 = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", mobile));

        if(member1 == null){
            throw new GuliException(20001,"error");
        }
        if(!member1.getPassword().equals(MD5.encrypt(password))){
            throw new GuliException(20001,"error");
        }
        if(member1.getIsDisabled()){
            throw new GuliException(20001,"error");
        }

        return JwtUtils.getJwtToken(member1.getId(), member1.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"注册失败");
        }

        if(!redisTemplate.opsForValue().get(mobile).equals(code)){
            throw new GuliException(20001,"注册失败");
        }

        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if (count > 0){
            throw new GuliException(20001,"注册失败");
        }

        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://edu-school-demo.oss-cn-hangzhou.aliyuncs.com/2022/05/29/2ea15a7c07774436836bd046c8f01e65202233110454472209.jpg");
        baseMapper.insert(member);
    }

    @Override
    public Member getMemberByOpenId(String openid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("openid", openid);
        return baseMapper.selectOne(memberQueryWrapper);
    }

    @Override
    public Integer countRegisterOneDay(String day) {
        return baseMapper.countRegisterOneDay(day);
    }
}
