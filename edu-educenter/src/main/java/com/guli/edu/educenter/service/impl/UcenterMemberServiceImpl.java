package com.guli.edu.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.educenter.entity.UcenterMember;
import com.guli.edu.educenter.mapper.UcenterMemberMapper;
import com.guli.edu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-10-05
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public Integer countNumRegister(String day) {
        return baseMapper.countRegisterNum(day);
    }

    //根据openId获取UcenterMember
    @Override
    public UcenterMember getUserByOpenId(String openidwx) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openidwx);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
}
