package com.school.ucenter.service;

import com.school.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-02
 */
public interface MemberService extends IService<Member> {
    /**
     * 登录
     * @param member
     * @return
     */
    String login(Member member);

    /**
     * 注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据openId查询用户
     * @param openid
     * @return
     */
    Member getMemberByOpenId(String openid);
    /**
     * 统计某一天注册的人数
     * @param day
     * @return
     */
    Integer countRegisterOneDay(String day);

}
