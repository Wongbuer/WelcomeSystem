package com.wong.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Wongbuer
 * @Description  Jwt工具类
 */
@Component
public class JwtUtil {
    private static final SecretKey KEY = Keys.hmacShaKeyFor("WONGBUERWONGBUERWONGBUERWONGBUERWONGBUERWONGBUER".getBytes(StandardCharsets.UTF_8));
    private static final JwtBuilder BUILDER = Jwts.builder().signWith(KEY);
    private static final JwtParser PARSER = Jwts.parser().verifyWith(KEY).build();

    public static String createToken(String key, Object value) {
        Map<String, Object> map = Map.of(key, value);
        return createToken(map);
    }

    public static String createToken(String key, Object value, TimeUnit timeUnit, long l) {
        Map<String, Object> map = Map.of(key, value);
        return createToken(map, timeUnit, l);
    }

    public static String createToken(Map<String, Object> map) {
        return BUILDER
                .expiration(getExpireDate(TimeUnit.MINUTES, 10L))
                .claims(map)
                .compact();
    }

    public static String createToken(Map<String, Object> map, TimeUnit timeUnit, long l) {
        return BUILDER
                .expiration(getExpireDate(timeUnit, l))
                .claims(map)
                .compact();
    }

    private static Date getExpireDate(TimeUnit timeUnit, long l) {
        return new Date(System.currentTimeMillis() + timeUnit.toMillis(l));
    }

    public static Claims parseToken(String token) {
        return (Claims) PARSER
                .parse(token)
                .getPayload();
    }
}
