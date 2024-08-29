package com.hp.tinyurl.app.event;

import com.hp.tinyurl.domain.TinyUrl;

/**
 * @author hp
 */
public interface TinyUrlApplicationEvents {

    record TinyUrlAccessedEvent(TinyUrl tinyUrl){

    }
}
