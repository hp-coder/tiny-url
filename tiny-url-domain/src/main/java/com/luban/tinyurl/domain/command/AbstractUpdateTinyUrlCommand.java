package com.luban.tinyurl.domain.command;

import com.luban.common.base.command.CommandForUpdateById;
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
