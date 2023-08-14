package com.luban.tinyurl.app.service.impl;

import com.luban.tinyurl.infrastructure.repository.TinyUrlRepository;
import com.luban.tinyurl.app.event.TinyUrlApplicationEvents;
import com.luban.tinyurl.app.service.TinyUrlQueryApplicationService;
import com.luban.tinyurl.domain.TinyUrl;
import com.luban.tinyurl.infrastructure.codec.NumberCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author hp
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TinyUrlQueryApplicationServiceImpl implements TinyUrlQueryApplicationService {

    private final TinyUrlRepository tinyUrlRepository;
    private final NumberCodec numberCodec;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Optional<TinyUrl> findById(Long id) {
        return Optional.ofNullable(id).flatMap(tinyUrlRepository::findById);
    }

    @Override
    public Optional<TinyUrl> findByCode(String code) {
        return findById(numberCodec.decode(code));
    }

    @Override
    public Optional<TinyUrl> accessById(Long id) {
        return findById(id)
                .map(url -> {
                    eventPublisher.publishEvent(new TinyUrlApplicationEvents.TinyUrlAccessedEvent(url));
                    return url;
                });
    }

    @Override
    public Optional<TinyUrl> accessByCode(String code) {
        return accessById(numberCodec.decode(code));
    }
}
