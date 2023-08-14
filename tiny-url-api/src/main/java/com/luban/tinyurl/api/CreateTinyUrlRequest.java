
package com.luban.tinyurl.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author hp
 */
@Data
public class CreateTinyUrlRequest {

    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;
}
