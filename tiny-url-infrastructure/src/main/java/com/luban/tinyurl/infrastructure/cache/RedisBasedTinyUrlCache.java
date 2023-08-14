package com.luban.tinyurl.infrastructure.cache;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Maps;
import com.luban.tinyurl.domain.TinyUrl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.beans.PropertyDescriptor;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author hp
 */
@Slf4j
public class RedisBasedTinyUrlCache implements TinyUrlCache {

    private static final String LUA_INCR_SCRIPT =
            "local is_exists = redis.call('HEXISTS', KEYS[1], ARGV[1])\n"
                    + "if is_exists == 1 then\n"
                    + "    return redis.call('HINCRBY', KEYS[1], ARGV[1], ARGV[2])\n"
                    + "else\n"
                    + "    return nil\n"
                    + "end";

    private final StringRedisTemplate redisTemplate;
    private final DefaultConversionService conversionService = new DefaultConversionService();

    @Value("${tinyurl.cache.key:tinyurl-{id}}")
    private String cacheKey;

    public RedisBasedTinyUrlCache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        conversionService.addConverter(new LocalDateTimeToStringConverter());
        conversionService.addConverter(new StringToLocalDateTimeConverter());
        conversionService.addConverter(new StringToInstantConverter());
        conversionService.addConverter(new InstantToStringConverter());
    }

    @Override
    public Optional<TinyUrl> findById(Long id) {
        final String key = createKey(id);
        final Map<String, Object> entries = redisTemplate.<String, Object>opsForHash().entries(key);
        return Optional.of(entries).map(this::buildFromMap);
    }

    @SneakyThrows
    private TinyUrl buildFromMap(Map<String, Object> entries) {
        if (MapUtil.isEmpty(entries)) {
            return null;
        }
        final TinyUrl tinyUrl = new TinyUrl();
        final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(tinyUrl);
        for (PropertyDescriptor descriptor : beanWrapper.getPropertyDescriptors()) {
            final String fieldName = descriptor.getName();
            final Class type = descriptor.getPropertyType();
            final Object value = entries.get(fieldName);
            if (value != null) {
                final Object value2Use = this.conversionService.convert(value, type);
                ReflectUtil.setFieldValue(tinyUrl, fieldName, value2Use);
            }

        }
        return tinyUrl;
    }

    @Override
    public void put(TinyUrl tinyUrl) {
        if (!tinyUrl.isEnableCache()) {
            log.info("tinyurl {} cache is disable", tinyUrl.getId());
            return;
        }
        final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(tinyUrl);
        final PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        final Map<String, String> result = Maps.newHashMapWithExpectedSize(propertyDescriptors.length);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            final String fieldName = descriptor.getName();
            if ("class".equals(fieldName)) {
                continue;
            }
            if ("events".equals(fieldName)) {
                continue;
            }
            if ("enableCache".equals(fieldName)) {
                continue;
            }
            if ("enableCacheSync".equals(fieldName)) {
                continue;
            }
            final Object fieldValue = beanWrapper.getPropertyValue(fieldName);
            if (fieldValue != null) {
                final String strValue = this.conversionService.convert(fieldValue, String.class);
                result.put(fieldName, strValue);
            }
        }

        final String cacheKey = createKey(tinyUrl.getId());
        redisTemplate.opsForHash().putAll(cacheKey, result);
    }

    @Override
    public void incrAccessCount(Long id, int count) {
        final String key = createKey(id);
        redisTemplate.execute(
                new DefaultRedisScript<>(LUA_INCR_SCRIPT, Long.class)
                , List.of(key),
                "accessCount",
                String.valueOf(count)
        );
    }

    @Override
    public void remove(Long id) {
        final String key = createKey(id);
        redisTemplate.delete(key);
    }

    private String createKey(Long id) {
        final String replace = "{id}";
        return cacheKey.replace(replace, String.valueOf(id));
    }

    static class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
        @Override
        public String convert(LocalDateTime source) {
            final long time = source.toInstant(ZoneOffset.of(ZoneId.systemDefault().getId()))
                    .toEpochMilli();
            return String.valueOf(time);
        }
    }

    static class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String s) {
            if (NumberUtil.isNumber(s)) {
                final long time = Long.parseLong(s);
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
            }
            return null;
        }
    }

    static class InstantToStringConverter implements Converter<Instant, String> {
        @Override
        public String convert(Instant source) {
            return String.valueOf( source.toEpochMilli());
        }
    }

    static class StringToInstantConverter implements Converter<String, Instant> {
        @Override
        public Instant convert(String s) {
            if (NumberUtil.isNumber(s)) {
                return Instant.ofEpochMilli(Long.parseLong(s));
            }
            return null;
        }
    }
}
