package com.hp.tinyurl.app.service;

import com.hp.tinyurl.domain.TinyUrl;
import com.hp.tinyurl.domain.command.*;

/**
 * @author hp
 */
public interface TinyUrlCommandApplicationService {

    TinyUrl createTinyUrl(CreateTinyUrlCommand command);

    TinyUrl createExpireTimeTinyUrl(CreateTinyUrlWithExpirationCommand command);

    TinyUrl createLimitTimeTinyUrl(CreateTinyUrlWithAccessCountLimitationCommand command);

    void enableTinyUrl(EnableTinyUrlCommand command);

    void disableTinyUrl(DisableTinyUrlCommand command);

    void incrAccessCount(AccessTinyUrlCommand command);

}
