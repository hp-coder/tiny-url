package com.hp.tinyurl.app.service;

import com.hp.tinyurl.domain.TinyUrl;

import java.util.Optional;

/**
 * @author hp
 */
public interface TinyUrlQueryApplicationService {
    Optional<TinyUrl> findById(Long id);

    Optional<TinyUrl> findByCode(String code);

    Optional<TinyUrl> accessById(Long id);

    Optional<TinyUrl> accessByCode(String code);

    Optional<TinyUrl> findByUrl(String url);
}
