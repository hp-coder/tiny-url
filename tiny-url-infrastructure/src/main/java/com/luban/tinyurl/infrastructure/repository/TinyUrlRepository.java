package com.luban.tinyurl.infrastructure.repository;

import com.luban.tinyurl.domain.TinyUrl;

import java.util.Optional;

/**
 * @author hp
 */
public interface TinyUrlRepository {

    TinyUrl save(TinyUrl tinyUrl);

    Optional<TinyUrl> findById(Long id);

    void incrAccessCount(Long id, Integer incrCount);

}
