package com.luban.tinyurl.infrastructure.cache;

import com.luban.tinyurl.domain.TinyUrl;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author hp
 */
@Slf4j
public class NullTinyUrlCache implements TinyUrlCache {
    @Override
    public Optional<TinyUrl> findById(Long id) {
        log.error("null cache: did nothing");
        return Optional.empty();
    }

    @Override
    public void put(TinyUrl tinyUrl) {
        log.error("null cache: did nothing");
    }

    @Override
    public void incrAccessCount(Long id, int times) {
        log.error("null cache: did nothing");
    }

    @Override
    public void remove(Long id) {
        log.error("null cache: did nothing");
    }
}
