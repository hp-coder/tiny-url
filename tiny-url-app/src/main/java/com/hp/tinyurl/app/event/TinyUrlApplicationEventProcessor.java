package com.hp.tinyurl.app.event;

import com.hp.tinyurl.app.service.TinyUrlCommandApplicationService;
import com.hp.tinyurl.domain.TinyUrl;
import com.hp.tinyurl.domain.command.AccessTinyUrlCommand;
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
public class TinyUrlApplicationEventProcessor {

    private final TinyUrlCommandApplicationService commandApplicationService;

    @EventListener
    public void handleTinyUrlAccessedEvent(TinyUrlApplicationEvents.TinyUrlAccessedEvent event) {
        final TinyUrl tinyUrl = event.tinyUrl();
        if (tinyUrl.needToUpdateAccessCount()) {
            final AccessTinyUrlCommand accessTinyUrlCommand = new AccessTinyUrlCommand();
            accessTinyUrlCommand.setId(tinyUrl.getId());
            accessTinyUrlCommand.setAccessCount(1);
            commandApplicationService.incrAccessCount(accessTinyUrlCommand);
        }
    }
}
