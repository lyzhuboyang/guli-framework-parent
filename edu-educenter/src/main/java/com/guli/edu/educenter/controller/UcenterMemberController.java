package com.guli.edu.educenter.controller;


import com.guli.edu.common.R;
import com.guli.edu.educenter.entity.UcenterMember;
import com.guli.edu.educenter.service.UcenterMemberService;
import com.guli.edu.educenter.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-10-05
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;
    //根据日期得到日期中注册人数

    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer num = ucenterMemberService.countNumRegister(day);
        return R.ok().data("countRegister", num);
    }

    //根据jwt获取用户数据
    @GetMapping("getUserInfoJwt/{token}")
    public R getJwtUserInfo(@PathVariable String token) {
        Claims claims = JwtUtils.checkJWT(token);
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");
        String id = (String) claims.get("id");
        UcenterMember member = new UcenterMember();
        member.setAvatar(avatar);
        member.setNickname(nickname);
        member.setId(id);
        return R.ok().data("userInfo", member);
    }


}

