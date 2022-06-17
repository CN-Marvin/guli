package com.school.ucenter.mapper;

import com.school.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-02
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 统计某一天注册的人数
     * @param day
     * @return
     */
    Integer countRegisterOneDay(String day);
}
