
package com.hp.tinyurl.api;

import com.hp.common.base.model.Request;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;

/**
 * @author hp
 */
@Data
@Validated
public class CreateTinyUrlRequest implements Request {

    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;
}
