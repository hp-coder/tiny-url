package com.hp.tinyurl.feign.service;

import com.hp.tinyurl.api.TinyUrlQueryApi;
import com.hp.tinyurl.app.service.TinyUrlQueryApplicationService;
import com.hp.tinyurl.domain.TinyUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Validated
@RestController
@RequestMapping(TinyUrlQueryApi.PATH)
@RequiredArgsConstructor
public class TinyUrlQueryFeignService implements TinyUrlQueryApi {

    private final TinyUrlQueryApplicationService queryApplicationService;

    @GetMapping("queryByCode/{code}")
    @Override
    public String queryByCode(@PathVariable("code") String code) {
        Optional<TinyUrl> tinyUrlOptional = this.queryApplicationService.findByCode(code);
        return tinyUrlOptional
                .filter(TinyUrl::accessible)
                .map(TinyUrl::getUrl)
                .orElse(null);
    }

    @GetMapping("accessByCode/{code}")
    @Override
    public String accessByCode(@PathVariable("code") String code) {
        Optional<TinyUrl> tinyUrlOptional = this.queryApplicationService.accessByCode(code);
        return tinyUrlOptional
                .filter(TinyUrl::accessible)
                .map(TinyUrl::getUrl)
                .orElse(null);
    }
}
