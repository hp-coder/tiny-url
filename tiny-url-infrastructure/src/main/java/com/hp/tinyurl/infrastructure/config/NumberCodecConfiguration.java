package com.hp.tinyurl.infrastructure.config;

import cn.hutool.core.util.StrUtil;
import com.hp.tinyurl.infrastructure.codec.CharacterBaseNumberCodec;
import com.hp.tinyurl.infrastructure.codec.RadixBasedNumberCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hp
 */
@Slf4j
@Configuration
public class NumberCodecConfiguration {

    @Value("${tinyurl.number.codec.radix:31}")
    private int radix;

    @Value("${tinyurl.number.codec.customStr:}")
    private String customStr;

    @Bean
    @ConditionalOnProperty(name = "tinyurl.number.codec.type", havingValue = "radix")
    public RadixBasedNumberCodec radixBasedNumberCodec() {
        return new RadixBasedNumberCodec(this.radix);
    }

    @Bean
    @ConditionalOnProperty(name = "tinyurl.number.codec.type", havingValue = "custom-base")
    public CharacterBaseNumberCodec characterBaseNumberCodec() {
        if (StrUtil.isEmpty(customStr)) {
            return new CharacterBaseNumberCodec();
        } else {
            return new CharacterBaseNumberCodec(customStr.toCharArray());
        }
    }
}
