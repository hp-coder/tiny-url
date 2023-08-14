package com.luban.tinyurl.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author hp
 */
@Data
public class CreateTinyUrlWithAccessCountLimitationRequest {
    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;

    @NotNull
    private Integer maxCount;
}
