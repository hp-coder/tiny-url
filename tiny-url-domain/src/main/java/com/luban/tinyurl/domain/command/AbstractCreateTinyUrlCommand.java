package com.luban.tinyurl.domain.command;

import com.luban.common.base.command.CommandForCreate;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

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
