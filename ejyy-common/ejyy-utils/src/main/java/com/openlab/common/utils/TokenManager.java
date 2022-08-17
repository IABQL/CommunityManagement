package com.openlab.common.utils;

import com.openlab.common.dto.UserTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 生成 Token 的工具类
 */
public class TokenManager {
    public static final long EXPIRE = 1000 * 60 * 60 * 24; // token 过期时间为 1 天
    public static final String SECRET = "openlab123";  // 密钥

    // 生成 token 字符串
    public static String createToken(Long id,String name) {
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return token;
    }

    // 根据 token 获取用户名
    public static String getNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 判断 token 是否存在，或者是否有效
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.hasLength(jwtToken)) {
            try {
                Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    // 判断 token 是否存在或有效
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("ejyy-pc-token");
            if (StringUtils.hasLength(jwtToken)) {
                Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // 根据 token 字符串获取对象属性
    public static UserTokenDto getNameByJwtToken(HttpServletRequest request) {
        UserTokenDto userTokenDto = new UserTokenDto();

        String jwtToken = request.getHeader("ejyy-pc-token");
        if (StringUtils.hasLength(jwtToken)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            userTokenDto.setId(claims.get("id", Long.class));
            userTokenDto.setName(claims.get("name", String.class));
        }
        return userTokenDto;
    }

}
