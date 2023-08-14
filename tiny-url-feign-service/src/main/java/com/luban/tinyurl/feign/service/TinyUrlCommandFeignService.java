package com.luban.tinyurl.feign.service;


import com.luban.tinyurl.api.*;
import com.luban.tinyurl.app.service.TinyUrlCommandApplicationService;
import com.luban.tinyurl.domain.TinyUrl;
import com.luban.tinyurl.domain.command.*;
import com.luban.tinyurl.infrastructure.codec.NumberCodec;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(TinyUrlCommandApi.PATH)
@RequiredArgsConstructor
public class TinyUrlCommandFeignService implements TinyUrlCommandApi {

    private final TinyUrlCommandApplicationService commandApplicationService;
    private final NumberCodec numberCodec;

    @Override
    @PostMapping("_create")
    public CreateTinyUrlResponse createTinyUrl(@RequestBody CreateTinyUrlRequest request) {
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
    public CreateTinyUrlResponse createExpire(CreateTinyUrlWithExpirationRequest request) {
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
    public CreateTinyUrlResponse createLimitAccessCount(CreateTinyUrlWithAccessCountLimitationRequest request) {
        final CreateTinyUrlWithAccessCountLimitationCommand command = new CreateTinyUrlWithAccessCountLimitationCommand();
        command.setUrl(request.getUrl());
        command.setEnableCache(request.getEnableCache());
        command.setEnableCacheSync(request.getEnableCacheSync());
        command.setMaxCount(request.getMaxCount());
        final TinyUrl tinyUrl = this.commandApplicationService.createLimitTimeTinyUrl(command);
        final String code = this.numberCodec.encode(tinyUrl.getId());
        return new CreateTinyUrlResponse(code);
    }


    @PostMapping("{id}/_disable/")
    @Override
    public void disable(@PathVariable Long id) {
        final DisableTinyUrlCommand command = new DisableTinyUrlCommand();
        command.setId(id);
        commandApplicationService.disableTinyUrl(command);
    }

    @PostMapping("{id}/_enable/")
    @Override
    public void enable(@PathVariable Long id) {
        final EnableTinyUrlCommand command = new EnableTinyUrlCommand();
        command.setId(id);
        commandApplicationService.enableTinyUrl(command);
    }
}

