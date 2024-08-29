package com.hp.tinyurl.feign.service;


import com.hp.tinyurl.api.*;
import com.hp.tinyurl.domain.command.*;
import com.hp.tinyurl.infrastructure.codec.NumberCodec;
import com.hp.tinyurl.api.*;
import com.hp.tinyurl.app.service.TinyUrlCommandApplicationService;
import com.hp.tinyurl.domain.TinyUrl;
import com.hp.tinyurl.domain.command.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping(TinyUrlCommandApi.PATH)
@RequiredArgsConstructor
public class TinyUrlCommandFeignService implements TinyUrlCommandApi {

    private final TinyUrlCommandApplicationService commandApplicationService;
    private final NumberCodec numberCodec;

    @Override
    @PostMapping("_create")
    public CreateTinyUrlResponse createTinyUrl(@RequestBody @Valid CreateTinyUrlRequest request) {
        final CreateTinyUrlCommand command = new CreateTinyUrlCommand();
        command.setUrl(request.getUrl());
        command.setEnableCache(request.getEnableCache());
        command.setEnableCacheSync(request.getEnableCacheSync());
        final TinyUrl tinyUrl = this.commandApplicationService.createTinyUrl(command);
        final String code = this.numberCodec.encode(tinyUrl.getId());
        return new CreateTinyUrlResponse(code);
    }

    @PostMapping("_create/expire")
    @Override
    public CreateTinyUrlResponse createExpire(@RequestBody @Valid CreateTinyUrlWithExpirationRequest request) {
        final CreateTinyUrlWithExpirationCommand command = new CreateTinyUrlWithExpirationCommand();
        command.setUrl(request.getUrl());
        command.setEnableCache(request.getEnableCache());
        command.setEnableCacheSync(request.getEnableCacheSync());
        command.setStartsAt(request.getStartsAt());
        command.setEndsAt(request.getEndsAt());
        final TinyUrl tinyUrl = this.commandApplicationService.createExpireTimeTinyUrl(command);
        final String code = this.numberCodec.encode(tinyUrl.getId());
        return new CreateTinyUrlResponse(code);
    }

    @PostMapping("_create/limitAccessCount")
    @Override
    public CreateTinyUrlResponse createLimitAccessCount(@RequestBody @Valid CreateTinyUrlWithAccessCountLimitationRequest request) {
        final CreateTinyUrlWithAccessCountLimitationCommand command = new CreateTinyUrlWithAccessCountLimitationCommand();
        command.setUrl(request.getUrl());
        command.setEnableCache(request.getEnableCache());
        command.setEnableCacheSync(request.getEnableCacheSync());
        command.setMaxCount(request.getMaxCount());
        final TinyUrl tinyUrl = this.commandApplicationService.createLimitTimeTinyUrl(command);
        final String code = this.numberCodec.encode(tinyUrl.getId());
        return new CreateTinyUrlResponse(code);
    }


    @PostMapping("{id}/_disable")
    @Override
    public void disable(@PathVariable("id") Long id) {
        final DisableTinyUrlCommand command = new DisableTinyUrlCommand();
        command.setId(id);
        commandApplicationService.disableTinyUrl(command);
    }

    @PostMapping("{id}/_enable")
    @Override
    public void enable(@PathVariable("id") Long id) {
        final EnableTinyUrlCommand command = new EnableTinyUrlCommand();
        command.setId(id);
        commandApplicationService.enableTinyUrl(command);
    }
}

