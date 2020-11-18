package com.guli.edu.educenter.service;

import com.guli.edu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zby
 * @since 2020-10-05
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    public Integer countNumRegister(String day);

    //根据openId获取UcenterMember
    UcenterMember getUserByOpenId(String openidwx);
}
