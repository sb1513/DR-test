package com.backend.drbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JWTutil {

    // JWT 密钥
    @Value("${my.jwt.secret}")
    private String SECRET;

    // 过期时间（毫秒）
    @Value("${my.jwt.exp}")
    private long EXPIRATION;


    /**
     * 获取 JWT 签名密钥
     */
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(SECRET)
        );
    }


    /**
     * 创建 JWT Token
     */
    public String generateToken(Map<String, Object> claims) {

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }


    /**
     * 解析 JWT Token，获取载荷信息
     */
    public Claims parseToken(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}