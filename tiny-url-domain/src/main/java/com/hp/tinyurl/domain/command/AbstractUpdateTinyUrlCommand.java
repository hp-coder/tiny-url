package com.hp.tinyurl.domain.command;

import com.hp.common.base.command.CommandForUpdateById;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author hp
 */
@Data
public abstract class AbstractUpdateTinyUrlCommand implements CommandForUpdateById<Long> {

    @NotNull
    private Long id;

}
