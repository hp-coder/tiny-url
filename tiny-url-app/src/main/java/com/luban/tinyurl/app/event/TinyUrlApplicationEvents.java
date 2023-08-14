package com.luban.tinyurl.app.event;

import com.luban.tinyurl.domain.TinyUrl;

/**
 * @author hp
 */
public interface TinyUrlApplicationEvents {

    record TinyUrlAccessedEvent(TinyUrl tinyUrl){

    }
}
