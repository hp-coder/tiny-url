package com.hp.tinyurl.domain.command;

import com.hp.common.base.command.CommandForCreate;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


/**
 * @author hp
 */
@Data
public class AbstractCreateTinyUrlCommand implements CommandForCreate {

    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;
}
