
package com.hp.tinyurl.api;

import com.hp.common.base.model.Request;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author hp
 */
@Data
@Validated
public class CreateTinyUrlWithExpirationRequest implements Request {

    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;

    private LocalDateTime startsAt;

    @NotNull
    private LocalDateTime endsAt;
}
