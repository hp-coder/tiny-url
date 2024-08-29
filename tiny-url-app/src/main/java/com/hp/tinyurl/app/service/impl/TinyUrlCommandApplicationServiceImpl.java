package com.hp.tinyurl.app.service.impl;

import com.hp.tinyurl.app.service.TinyUrlCommandApplicationService;
import com.hp.tinyurl.domain.TinyUrl;
import com.hp.tinyurl.domain.command.*;
import com.hp.tinyurl.domain.context.CreateTinyUrlContext;
import com.hp.tinyurl.domain.context.CreateTinyUrlWithAccessCountLimitationContext;
import com.hp.tinyurl.domain.context.CreateTinyUrlWithExpirationContext;
import com.hp.tinyurl.infrastructure.cache.TinyUrlCache;
import com.hp.tinyurl.domain.gateway.TinyUrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hp
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TinyUrlCommandApplicationServiceImpl implements TinyUrlCommandApplicationService {

    private final TinyUrlRepository tinyUrlRepository;
    private final TinyUrlCache tinyUrlCache;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TinyUrl createTinyUrl(CreateTinyUrlCommand command) {
        final CreateTinyUrlContext context = CreateTinyUrlContext.create(command);
        final TinyUrl tinyUrl = TinyUrl.createTinyUrl(context);
        return tinyUrlRepository.save(tinyUrl);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TinyUrl createExpireTimeTinyUrl(CreateTinyUrlWithExpirationCommand command) {
        final CreateTinyUrlWithExpirationContext context = CreateTinyUrlWithExpirationContext.create(command);
        final TinyUrl tinyUrl = TinyUrl.createTinyUrlWithExpiration(context);
        return tinyUrlRepository.save(tinyUrl);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TinyUrl createLimitTimeTinyUrl(CreateTinyUrlWithAccessCountLimitationCommand command) {
        final CreateTinyUrlWithAccessCountLimitationContext context =
                CreateTinyUrlWithAccessCountLimitationContext.create(command);
        final TinyUrl tinyUrl = TinyUrl.createTinyUrlWithAccessCountLimitation(context);
        return tinyUrlRepository.save(tinyUrl);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void enableTinyUrl(EnableTinyUrlCommand command) {
        tinyUrlRepository.findById(command.getId())
                .ifPresent(tinyUrl -> tinyUrl.enable(command));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void disableTinyUrl(DisableTinyUrlCommand command) {
        tinyUrlRepository.findById(command.getId())
                .ifPresent(tinyUrl -> tinyUrl.disable(command));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void incrAccessCount(AccessTinyUrlCommand command) {
        incrAccessCountForCache(command);
        incrAccessCountForDB(command);
    }

    // TODO 考虑异步顺序更新
    public void incrAccessCountForDB(AccessTinyUrlCommand command) {
        tinyUrlRepository.incrAccessCount(command.getId(), command.getAccessCount());
    }

    public void incrAccessCountForCache(AccessTinyUrlCommand command) {
        tinyUrlCache.incrAccessCount(command.getId(), command.getAccessCount());
    }
}
