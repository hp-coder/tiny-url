package com.hp.tinyurl.domain.event;

import com.hp.tinyurl.domain.TinyUrl;

/**
 * @author hp
 */
public interface TinyUrlDomainEvents {

    record TinyUrlCreatedEvent(TinyUrl tinyUrl) {
    }

    record TinyUrlDisabledEvent(TinyUrl tinyUrl) {
    }

    record TinyUrlEnabledEvent(TinyUrl tinyUrl) {
    }
}
