package com.example.springboot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class TokenUtils {
    /**
     * 生成token
     *
     * @return
     */
    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+(30*60*1000))) // token过期时间
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }
}
