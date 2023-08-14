

package com.luban.tinyurl.domain.command;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author hp
 */
@Data
public class CreateTinyUrlWithAccessCountLimitationCommand extends AbstractCreateTinyUrlCommand {

    @NotNull
    private Integer maxCount;

}
