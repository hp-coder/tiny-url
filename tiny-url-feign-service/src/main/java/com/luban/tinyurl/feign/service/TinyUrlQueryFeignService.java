package com.luban.tinyurl.feign.service;

import com.luban.tinyurl.app.service.TinyUrlQueryApplicationService;
import com.luban.tinyurl.api.TinyUrlQueryApi;
import com.luban.tinyurl.domain.TinyUrl;
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
    public String queryByCode(@PathVariable String code) {
        Optional<TinyUrl> tinyUrlOptional = this.queryApplicationService.findByCode(code);
        return tinyUrlOptional
                .filter(TinyUrl::accessible)
                .map(TinyUrl::getUrl)
                .orElse(null);
    }

    @GetMapping("accessByCode/{code}")
    @Override
    public String accessByCode(String code) {
        Optional<TinyUrl> tinyUrlOptional = this.queryApplicationService.accessByCode(code);
        return tinyUrlOptional
                .filter(TinyUrl::accessible)
                .map(TinyUrl::getUrl)
                .orElse(null);
    }
}
