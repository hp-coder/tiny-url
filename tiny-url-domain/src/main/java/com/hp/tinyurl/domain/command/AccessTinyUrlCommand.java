package com.hp.tinyurl.domain.command;

import lombok.Data;

import java.util.Optional;

/**
 * @author hp
 */
@Data
public class AccessTinyUrlCommand extends AbstractUpdateTinyUrlCommand {

    private Integer accessCount;

    public Integer getAccessCount() {
        return Optional.ofNullable(this.accessCount).map(Integer::intValue).orElse(1);
    }
}
