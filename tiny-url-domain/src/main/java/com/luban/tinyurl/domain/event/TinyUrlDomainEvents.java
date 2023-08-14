package com.luban.tinyurl.domain.event;

import com.luban.tinyurl.domain.TinyUrl;

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
