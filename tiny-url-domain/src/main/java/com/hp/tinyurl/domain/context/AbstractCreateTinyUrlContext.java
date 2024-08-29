package com.hp.tinyurl.domain.context;

import com.hp.tinyurl.domain.command.AbstractCreateTinyUrlCommand;
import lombok.Data;

/**
 * @author hp
 */
@Data
public abstract class AbstractCreateTinyUrlContext<CMD extends AbstractCreateTinyUrlCommand> {
    //TODO number generator required

    private CMD command;

    public Long nextId(){
        return 0L;
    }
}
