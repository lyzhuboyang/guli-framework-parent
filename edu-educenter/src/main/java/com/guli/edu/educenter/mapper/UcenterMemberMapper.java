package com.guli.edu.educenter.mapper;

import com.guli.edu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zby
 * @since 2020-10-05
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    //统计某一天的注册人数
    public Integer countRegisterNum(String day);
}
