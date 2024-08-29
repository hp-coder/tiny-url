package com.hp.tinyurl.infrastructure.config;

import com.hp.tinyurl.infrastructure.cache.NullTinyUrlCache;
import com.hp.tinyurl.infrastructure.cache.RedisBasedTinyUrlCache;
import com.hp.tinyurl.infrastructure.cache.TinyUrlCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author hp
 */
@Slf4j
@Configuration("tinyUrlCacheConfiguration")
public class CacheConfiguration {

    @Value("${tinyurl.cache.enable:false}")
    private boolean cacheEnable;

    @Configuration
    @ConditionalOnClass(RedisOperations.class)
    static class RedisTemplateConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public RedisTemplate<String, Long> tinyurlRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            return redisTemplate;
        }
    }

    @Bean
    public TinyUrlCache tinyUrlCache(StringRedisTemplate redisTemplate) {
        if (cacheEnable) {
            return new RedisBasedTinyUrlCache(redisTemplate);
        } else {
            return new NullTinyUrlCache();
        }
    }
}
