

package com.hp.tinyurl.domain.command;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author hp
 */
@Data
public class CreateTinyUrlWithAccessCountLimitationCommand extends AbstractCreateTinyUrlCommand {

    @NotNull
    private Integer maxCount;

}
