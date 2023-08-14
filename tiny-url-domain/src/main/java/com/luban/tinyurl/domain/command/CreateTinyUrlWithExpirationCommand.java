
package com.luban.tinyurl.domain.command;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author hp
 */
@Data
public class CreateTinyUrlWithExpirationCommand extends AbstractCreateTinyUrlCommand {

    private LocalDateTime startsAt;
    @NotNull
    private LocalDateTime endsAt;

    public LocalDateTime getStartsAt(){
        return Optional.ofNullable(this.startsAt).orElse(LocalDateTime.now());
    }
}
