package com.luban.tinyurl.infrastructure.event;

import com.luban.tinyurl.domain.event.TinyUrlDomainEvents;
import com.luban.tinyurl.infrastructure.cache.TinyUrlCache;
import com.luban.tinyurl.domain.TinyUrl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author hp
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TinyUrlDomainEventProcessor {

    private final TinyUrlCache tinyUrlCache;

    @EventListener
    public void handleTinyUrlCreated(TinyUrlDomainEvents.TinyUrlCreatedEvent event){
        log.debug("putting tiny url to cache after creation");
        final TinyUrl tinyUrl = event.tinyUrl();
        tinyUrlCache.put(tinyUrl);
    }
}
