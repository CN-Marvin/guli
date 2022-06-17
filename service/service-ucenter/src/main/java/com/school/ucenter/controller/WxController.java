package com.school.ucenter.controller;

import com.google.gson.Gson;
import com.school.common.utils.JwtUtils;
import com.school.guli.config.handler.exceptionhandler.GuliException;
import com.school.ucenter.entity.Member;
import com.school.ucenter.service.MemberService;
import com.school.ucenter.utils.ConstantWxUtils;
import com.school.ucenter.utils.HttpClientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 功能描述：
 *
 * @Package: com.school.ucenter.controller
 * @author: Marvin-zl
 * @date: 2022/6/3 17:48
 */
@Controller

@RequestMapping("/ucenterservice/wx")
public class WxController {

    @Resource
    private MemberService memberService;

    @GetMapping("login")
    public String genQrConnect() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        //获取业务服务器重定向地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            //url编码
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        String state = "imhelen";
        System.out.println("state = " + state);

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                state);
        return "redirect:" + qrcodeUrl;
    }

    @GetMapping("callback")
    public String callback(String code, String state) {
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET, code);
        String accessTokenInfo = null;
        try {
            //向认证服务器发送请求换取access_token
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String) map.get("access_token");
            String openid = (String) map.get("openid");
            Member member = memberService.getMemberByOpenId(openid);
            if(member == null) {
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" + "?access_token=%s" + "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headImgUrl = (String) userInfoMap.get("headimgurl");
                //自动注册
                member = new Member();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headImgUrl);
                memberService.save(member);
            }

            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://localhost:3000?token=" + token;
        } catch (Exception e) {
            throw new GuliException(20001,"error");
        }
    }
}
