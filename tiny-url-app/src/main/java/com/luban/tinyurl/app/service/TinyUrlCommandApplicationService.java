package com.luban.tinyurl.app.service;

import com.luban.tinyurl.domain.TinyUrl;
import com.luban.tinyurl.domain.command.*;

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
