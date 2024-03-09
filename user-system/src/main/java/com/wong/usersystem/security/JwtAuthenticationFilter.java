package com.wong.usersystem.security;

import com.wong.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.wong.usersystem.config.SecurityConfig.SECURITY_LOGIN_PREFIX;

/**
 * @author Wongbuer
 * @description Jwt请求头过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        Claims claims = null;
        try {
            claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (claims == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String username = claims.get("username", String.class);
        if (StringUtils.hasText(username)) {
            String redisKey = SECURITY_LOGIN_PREFIX + username;
            UserDetails userDetails = (UserDetails) redisTemplate.opsForValue().get(redisKey);
            if (userDetails != null) {
                if (log.isTraceEnabled()) {
                    log.trace("JwtAuthenticationFilter: Jwt authentication success-{}", username);
                }
                UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
