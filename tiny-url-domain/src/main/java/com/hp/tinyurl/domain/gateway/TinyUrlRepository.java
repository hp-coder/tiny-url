package com.hp.tinyurl.domain.gateway;

import com.hp.tinyurl.domain.TinyUrl;

import java.util.Optional;

/**
 * @author hp
 */
public interface TinyUrlRepository {

    TinyUrl save(TinyUrl tinyUrl);

    Optional<TinyUrl> findById(Long id);

    void incrAccessCount(Long id, Integer incrCount);

    Optional<TinyUrl> findByUrl(String url);
}
