package com.wong.common.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.util.ClassUtils;

/**
 * @author Wongbuer
 * @description 配置RedisTemplate的String序列化, 并在Security启用时配置对应序列化
 */
@Slf4j
@Configuration
@ConditionalOnClass({RedisTemplate.class, RedisConnectionFactory.class})
public class RedisTemplateConfig {
    private static final String SECURITY_MODULES = "org.springframework.security.jackson2.SecurityJackson2Modules";

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        GenericJackson2JsonRedisSerializer serializer = null;
        boolean isPresent = ClassUtils.isPresent(SECURITY_MODULES, getClass().getClassLoader());
        if (isPresent) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));
            objectMapper.activateDefaultTyping(
                    objectMapper.getPolymorphicTypeValidator(),
                    ObjectMapper.DefaultTyping.NON_FINAL,
                    JsonTypeInfo.As.PROPERTY);
            serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
            if (log.isTraceEnabled()) {
                log.trace("initializing GenericJackson2JsonRedisSerializer with SecurityJackson2Modules");
            }
        } else {
            serializer = new GenericJackson2JsonRedisSerializer();
        }
        if (log.isTraceEnabled() && !isPresent) {
            log.trace("initializing GenericJackson2JsonRedisSerializer without SecurityJackson2Modules");
        }
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }
}
