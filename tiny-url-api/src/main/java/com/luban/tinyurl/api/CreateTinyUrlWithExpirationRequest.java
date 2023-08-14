
package com.luban.tinyurl.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author hp
 */
@Data
public class CreateTinyUrlWithExpirationRequest {

    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;

    private LocalDateTime startsAt;

    @NotNull
    private LocalDateTime endsAt;
}
