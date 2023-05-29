package com.app.forecast.utils;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {

    //private static long time = 1000*10;        // token 有效期为10秒
    private static final long time = 1000 * 60 * 60;   // token 有效期为1小时
    private static final String signature = "admin";

    // 生成token ，三个参数是我实体类的字段，可根据自身需求来传，一般只需要用户id即可
    public static String createJwtToken(String userAccount) {
        JwtBuilder builder = Jwts.builder();
        String jwtToken = builder
                // header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // payload 载荷
                .claim("userAccount", userAccount)
                .claim("date", new Date())
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                // signature 签名信息
                .signWith(SignatureAlgorithm.HS256, signature)
                // 用.拼接
                .compact();
        return jwtToken;
    }

    // 验证 token 是否还有效，返回具体内容
    public static Claims checkToken(String token) {
        if (token == null) {
            return null;
        }
        JwtParser parser = Jwts.parser();
        try {
            Jws<Claims> claimsJws = parser.setSigningKey(signature).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            System.out.println(claims.get("userAccount"));
            System.out.println(claims.getSubject()); // 签名
            System.out.println(claims.getExpiration()); // 有效期
            // 如果解析 token 正常，返回 claims
            return claims;
        } catch (Exception e) {
            // 如果解析 token 抛出异常，返回 null
            return null;
        }
    }
}
