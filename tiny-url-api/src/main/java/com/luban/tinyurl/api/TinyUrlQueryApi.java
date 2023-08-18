package com.luban.tinyurl.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author hp
 */
public interface TinyUrlQueryApi {

    String PATH = "tingurl/query";

    @GetMapping("queryByCode/{code}")
    String queryByCode(@PathVariable(value = "code") String code);

    @GetMapping("accessByCode/{code}")
    String accessByCode(@PathVariable(value = "code") String code);

}
