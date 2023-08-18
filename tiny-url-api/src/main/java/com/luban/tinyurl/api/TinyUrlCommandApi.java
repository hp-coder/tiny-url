package com.luban.tinyurl.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hp
 */
public interface TinyUrlCommandApi {

    String PATH = "tinyUrl/command";

    @PostMapping("_create")
    CreateTinyUrlResponse createTinyUrl(@RequestBody CreateTinyUrlRequest request);

    @PostMapping("_create/expire")
    CreateTinyUrlResponse createExpire(@RequestBody CreateTinyUrlWithExpirationRequest request);

    @PostMapping("_create/limitAccessCount")
    CreateTinyUrlResponse createLimitAccessCount(@RequestBody CreateTinyUrlWithAccessCountLimitationRequest request);

    @PostMapping("{id}/_disable")
    void disable(@PathVariable(value = "id") Long id);

    @PostMapping("{id}/_enable")
    void enable(@PathVariable(value = "id") Long id);
}
