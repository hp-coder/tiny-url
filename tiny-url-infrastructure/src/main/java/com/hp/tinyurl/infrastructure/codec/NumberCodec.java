package com.hp.tinyurl.infrastructure.codec;

/**
 * @author hp
 */
public interface NumberCodec {
    String encode(Long number);

    Long decode(String code);
}

