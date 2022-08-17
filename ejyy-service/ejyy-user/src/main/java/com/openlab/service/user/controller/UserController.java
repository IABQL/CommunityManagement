package com.openlab.service.user.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.common.dto.CompanyUserPart;
import com.openlab.common.utils.*;
import com.openlab.service.user.dto.*;
import com.openlab.service.user.entity.PropertyCompanyAuth;
import com.openlab.service.user.entity.PropertyCompanyUserDefaultCommunity;
import com.openlab.service.user.entity.PropertyCompanyUserLogin;
import com.openlab.service.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 控制器
 */
@CrossOrigin // 允许跨域请求
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PropertyCompanyUserService propertyCompanyUserService;
    @Autowired
    private PropertyCompanyAuthService propertyCompanyAuthService;
    @Autowired
    private PropertyCompanyUserLoginService propertyCompanyUserLoginService;
    @Autowired
    private CommunityInfoService communityInfoService;
    @Autowired
    private PropertyCompanyUserDefaultCommunityService propertyCompanyUserDefaultCommunityService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    PropertyCompanyUserService companyUserService;

    @GetMapping("/companyUser")
    CompanyUserPart getCompanyUserInformation(@RequestBody CompanyUserInformation companyUserInformation){
        return companyUserService.getCompanyUser(companyUserInformation);
    }

    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        String token = request.getHeader("ejyy-pc-token");
        String account = TokenManager.getNameFromToken(token);
        //System.out.println("account = " + account);
        LoginInfo loginInfo = propertyCompanyUserService.login(account);
        // 1. 创建返回给前端的对象
        UserInfo userInfo = UserInfo.builder()
                .id(loginInfo.getId())
                .account(loginInfo.getAccount())
                .open_id(loginInfo.getOpenId())
                .real_name(loginInfo.getRealName())
                .gender(loginInfo.isGender()?1:2) // 女是2，男是1
                .avatar_url(loginInfo.getAvatarUrl())
                .phone(MaskPhone.mask(loginInfo.getPhone())) // 屏蔽手机号
                .join_company_at(loginInfo.getJoinCompanyAt())
                .admin(loginInfo.isAdmin()?1:0)
                .created_at(loginInfo.getCreatedAt())
                .subscribed(loginInfo.getSubscribed())
                .access(StringUtils.hasLength(loginInfo.getContent()) ? loginInfo.getContent() : "[]")
                .build();

        // 5. 通过用户ID查询默认的用户部门、职位
        DepartJob departJob = propertyCompanyUserService.info(loginInfo.getId());
        PostInfo postInfo = new PostInfo();
        if (ObjectUtils.isEmpty(departJob)) {
            //5.1 没查到，则添加null
            postInfo.setDepartment(null);
            postInfo.setJob(null);
            // 5.2 通过用户ID查询小区临时表
            CommunityDto communityDto = communityInfoService.getInfoByUserId(loginInfo.getId().longValue());
            if (ObjectUtils.isEmpty(communityDto)) {
                CommunityList communityList = CommunityList.builder().build();
                postInfo.getCommunity_list().add(communityList);
            } else {
                CommunityList communityList = CommunityList.builder()
                        .community_id(communityDto.getCommunityId())
                        .access_remote(communityDto.isAccessRemote()?1:0)
                        .fitment_pledge(communityDto.isFitmentPledge()?1:0)
                        .access_nfc(communityDto.isAccessNfc()?1:0)
                        .access_qrcode(communityDto.isAccessQrcode()?1:0)
                        .name(communityDto.getName())
                        .build();
                postInfo.getCommunity_list().add(communityList);
            }

            // 5.3 查到了直接添加
        } else {
            postInfo.setJob(departJob.getJob());
            postInfo.setDepartment(departJob.getDepartment());
        }

        // 6. 更新默认小区信息
        Long defaultInfo = propertyCompanyUserDefaultCommunityService.getCommunityId(loginInfo.getId().longValue());
        postInfo.setDefault_community_id(defaultInfo == null ? postInfo.getCommunity_list().get(0).getCommunity_id() : defaultInfo);
        postInfo.setWechat_payment(0);

        // 返回结果的信息
        Map<String, Object> data = new HashMap<>();
        data.put("userInfo", userInfo);
        data.put("postInfo", postInfo);
        return Result.ok(200, data);
    }

    // 实现登录
    @PostMapping("/account_login")
    public Result login(@RequestBody LoginParam loginParam, HttpServletRequest request) {

        String userAgent = request.getHeader("User-Agent");
        // 获取验证码
        String captcha = loginParam.getCaptcha();

        // 从 Redis 中获取验证码值
        String redisCaptcha = (String) redisTemplate.opsForValue().get(captcha);
        if (!StringUtils.hasLength(redisCaptcha)) {
            return Result.ok(ResultCodeEnum.CAPTCHA_ERROR.getCode(), "验证码失效，请重新获取");
        }
        /*if (StringUtils.hasLength(redisCaptcha)) {
            // 校验验证码
            if (!StringUtils.hasLength(captcha) || "".equals(captcha.toLowerCase())) {
                return Result.ok(ResultCodeEnum.CAPTCHA_ERROR.getCode(), "验证码不能为空");
            }
            if (!redisCaptcha.equals(captcha)) {
                return Result.ok(ResultCodeEnum.CAPTCHA_ERROR.getCode(), "验证码错误");
            }
        } else {
            return Result.ok(ResultCodeEnum.CAPTCHA_ERROR.getCode(), "验证码失效，请重新获取");
        }*/
        // 清除 Redis 中该验证码信息
        redisTemplate.delete(captcha);

        // 调用登录方法进行登录
        LoginInfo loginInfo = propertyCompanyUserService.login(loginParam.getAccount());
        // 对账号和密码进行校验
        if (ObjectUtils.isEmpty(loginInfo) || !loginInfo.getPassword().equals(MD5.encrypt(loginParam.getPassword()))) {
            return Result.ok(ResultCodeEnum.PWD_ERROR.getCode(), "密码错误或账号不存在");
        }

        // 登录校验通过需要处理的流程
        // 1. 创建返回给前端的对象
        UserInfo userInfo = UserInfo.builder()
                .id(loginInfo.getId())
                .account(loginInfo.getAccount())
                .open_id(loginInfo.getOpenId())
                .real_name(loginInfo.getRealName())
                .gender(loginInfo.isGender()?1:2) // 女是2，男是1
                .avatar_url(loginInfo.getAvatarUrl())
                .phone(MaskPhone.mask(loginInfo.getPhone())) // 屏蔽手机号
                .join_company_at(loginInfo.getJoinCompanyAt())
                .admin(loginInfo.isAdmin()?1:0)
                .created_at(loginInfo.getCreatedAt())
                .subscribed(loginInfo.getSubscribed())
                .access(StringUtils.hasLength(loginInfo.getContent()) ? loginInfo.getContent() : "[]")
                .build();

        // 2. 生成 token
        String token = TokenManager.createToken(loginInfo.getAccount());

        // 3. 更新 token，这一步的操作可以放到消息队列中
        UpdateWrapper<PropertyCompanyAuth> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("token", token);
        updateWrapper.eq("property_company_user_id", loginInfo.getId().longValue());
        propertyCompanyAuthService.update(updateWrapper);
        // 注意：要把 ejyy_property_company_auth 表中的 token 字段长度设置为 200，原来的 32 位不够

        // 4. 添加登录日志，这一步也是可以放到消息队列中
        propertyCompanyUserLoginService.save(PropertyCompanyUserLogin.builder()
                .propertyCompanyUserId(loginInfo.getId().longValue())
                .userAgent(userAgent) // 浏览器的信息
                .ip(IPUtils.getIpAddr(request)) // 客户端的IP
                .loginAt(new Date().getTime())
                .build());

        // 5. 返回 postInfo 信息
        DepartJob departJob = propertyCompanyUserService.info(loginInfo.getId());
        PostInfo postInfo = new PostInfo();
        if (ObjectUtils.isEmpty(departJob)) {
            postInfo.setDepartment(null);
            postInfo.setJob(null);
            // 5.1 查询小区信息
            CommunityDto communityDto = communityInfoService.getInfoByUserId(loginInfo.getId().longValue());
            if (ObjectUtils.isEmpty(communityDto)) {
                CommunityList communityList = CommunityList.builder().build();
                postInfo.getCommunity_list().add(communityList);
            } else {
                CommunityList communityList = CommunityList.builder()
                        .community_id(communityDto.getCommunityId())
                        .access_remote(communityDto.isAccessRemote()?1:0)
                        .fitment_pledge(communityDto.isFitmentPledge()?1:0)
                        .access_nfc(communityDto.isAccessNfc()?1:0)
                        .access_qrcode(communityDto.isAccessQrcode()?1:0)
                        .name(communityDto.getName())
                        .build();
                postInfo.getCommunity_list().add(communityList);
            }
        } else {
            postInfo.setJob(departJob.getJob());
            postInfo.setDepartment(departJob.getDepartment());
        }

        // 6. 更新默认小区信息
        Long defaultInfo = propertyCompanyUserDefaultCommunityService.getCommunityId(loginInfo.getId().longValue());
        if (defaultInfo == null) {
            if (postInfo.getCommunity_list().size() > 0) {
                // 添加
                propertyCompanyUserDefaultCommunityService.save(PropertyCompanyUserDefaultCommunity.builder()
                        .communityId(postInfo.getCommunity_list().get(0).getCommunity_id())
                        .propertyCompanyUserId(loginInfo.getId().longValue())
                        .build());
            }
        } else {
            if (postInfo.getCommunity_list().size() > 0) {
                // 更新
                UpdateWrapper<PropertyCompanyUserDefaultCommunity> updateWrapper1 = new UpdateWrapper<>();
                updateWrapper1.set("community_id", defaultInfo);
                updateWrapper1.eq("property_company_user_id", loginInfo.getId().longValue());
                propertyCompanyUserDefaultCommunityService.update(updateWrapper1);
            }
        }
        postInfo.setDefault_community_id(defaultInfo == null ? postInfo.getCommunity_list().get(0).getCommunity_id() : defaultInfo);
        postInfo.setWechat_payment(0);

        // 返回结果的信息
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userInfo", userInfo);
        data.put("postInfo", postInfo);
        return Result.ok(200, data);
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader("ejyy-pc-token") String token){
        String account = TokenManager.getNameFromToken(token);
        LoginInfo loginInfo = propertyCompanyUserService.login(account);
        System.out.println(token);
        propertyCompanyAuthService.logout(loginInfo);
        return Result.ok(200,"账号已退出");
    }
    // 验证码
    @GetMapping("/captcha")
    public Result captcha(HttpServletResponse response) {
        // 获取验证码字符串
        String verifyCode = defaultKaptcha.createText();
        //System.out.println(verifyCode);

        // 缓存用于登录校验
        String key = UUID.randomUUID().toString().replaceAll("-","");
        // 存储到 Redis 中
        redisTemplate.opsForValue().set(key, verifyCode, 60*3, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(verifyCode, verifyCode, 60*3, TimeUnit.SECONDS);

        // 将key存储到本地cookie中
        Cookie cookie = new Cookie("captchaIdentity", key);
        cookie.setMaxAge(60*3); // 有效时间为 3 分钟
        response.addCookie(cookie);

        // 生成图片
        BufferedImage bufferedImage = defaultKaptcha.createImage(verifyCode);
        try (
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ) {
            ImageIO.write(bufferedImage, "png", outputStream);

            Base64.Encoder encoder = Base64.getEncoder();
            String base64 = encoder.encodeToString(outputStream.toByteArray());
            //String captchaBase64 = "data:image/svg+xml;base64," + base64.replaceAll("\r\n", "");
            String captchaBase64 = "data:image/png;base64," + base64.replaceAll("\r\n", "");
            // 返回结果
            Map<String, Object> data = new HashMap<>();
            data.put("img", captchaBase64);
            data.put("expire", 1800000);
            return Result.ok(200, data);
        } catch (IOException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("img", null);
            data.put("expire", 1);
            return Result.ok(ResultCodeEnum.CAPTCHA_ERROR.getCode(), ResultCodeEnum.CAPTCHA_ERROR.getMessage(), data);
        }
    }

    // 获取状态信息
    @GetMapping("/state")
    public Result state() {
        Map<String, Object> data = new HashMap<>();
        data.put("state", UUID.randomUUID().toString()); // 设置UUID
        data.put("expire", 1800000);
        return Result.ok(200, data);
    }
}
