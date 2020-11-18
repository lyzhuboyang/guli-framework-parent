package com.guli.edu.educenter.controller;

import com.google.gson.Gson;
import com.guli.edu.educenter.entity.UcenterMember;
import com.guli.edu.educenter.service.UcenterMemberService;
import com.guli.edu.educenter.utils.ConstantPropertiesUtil;
import com.guli.edu.educenter.utils.HttpClientUtils;
import com.guli.edu.educenter.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx") //TODO 因为多人测试，要求名字固定，实际开发中，随便起名字
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {
        try {
            //1、得到授权时票据code,类似手机的验证码

            //2、请求腾讯提供的地址
            //向认证服务器发送请求换取 微信id 和 access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //向地址里面拼接参数
            baseAccessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);
            //请求拼接之后的地址，得到微信id和access_token
            //使用httpclent发送请求
            String resultAccessToken = HttpClientUtils.get(baseAccessTokenUrl);
            //请求获取openid和access_token
            Gson gson = new Gson();
            HashMap map = gson.fromJson(resultAccessToken, HashMap.class);
            String access_token = (String) map.get("access_token");//访问凭证
            String openid = (String) map.get("openid");//微信唯一标识

            //根据openid到数据库中查询是否有相同的数据
            UcenterMember member = memberService.getUserByOpenId(openid);
            String token = null;
            if (member == null) {//数据库中没有数据
                //3拿着access_token和openid再去请求地址，得到扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                baseUserInfoUrl = String.format(baseUserInfoUrl,
                        access_token,
                        openid);
                //httpclient请求拼接之后的地址
                String resultUserInfo = HttpClientUtils.get(baseUserInfoUrl);
                HashMap userInfoMap = gson.fromJson(resultUserInfo, HashMap.class);
                String openidwx = (String) userInfoMap.get("openid");
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");

                //获取到扫码人的信息添加到数据库中
                //添加扫描人信息之前，判断表是否已经存在相同数据
                //如果没有存在,添加到数据库中
                //根据openid判断
                member = new UcenterMember();
                member.setOpenid(openidwx);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            //生成token
            token = JwtUtils.geneJsonWebToken(member);
            //添加之后，跳转回到首页面
            return "redirect:http://localhost:3000/?token=" + token;
        } catch (Exception e) {
            return null;
        }

    }

    //生成二维码
    @GetMapping("login")
    public String genQrConnect() {
        //拼接请求地址，拼接需要的参数
        //%s 相当于？占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对回调地址进行url编码
        String redirectUri = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUri = URLEncoder.encode(redirectUri, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //为了测试，需要传递state
        //为了做内网穿透时候的值，内网穿透前置域名
        String state = "eduonline";

        //生成qrcodeUrl
        String url = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUri,
                state
        );
        //请求拼接地址
        return "redirect:" + url;
    }


}
