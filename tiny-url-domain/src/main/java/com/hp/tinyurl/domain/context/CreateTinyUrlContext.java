package com.hp.tinyurl.domain.context;

import com.hp.tinyurl.domain.command.CreateTinyUrlCommand;

/**
 * @author hp
 */
public class CreateTinyUrlContext extends AbstractCreateTinyUrlContext<CreateTinyUrlCommand> {

    public static CreateTinyUrlContext create(CreateTinyUrlCommand command){
        final CreateTinyUrlContext context = new CreateTinyUrlContext();
        context.setCommand(command);
        return context;
    }
}
