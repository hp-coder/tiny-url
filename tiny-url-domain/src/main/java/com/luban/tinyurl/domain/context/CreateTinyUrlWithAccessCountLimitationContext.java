package com.luban.tinyurl.domain.context;

import com.luban.tinyurl.domain.command.CreateTinyUrlWithAccessCountLimitationCommand;

/**
 * @author hp
 */
public class CreateTinyUrlWithAccessCountLimitationContext extends AbstractCreateTinyUrlContext<CreateTinyUrlWithAccessCountLimitationCommand> {

    public static CreateTinyUrlWithAccessCountLimitationContext create(CreateTinyUrlWithAccessCountLimitationCommand command) {
        final CreateTinyUrlWithAccessCountLimitationContext context = new CreateTinyUrlWithAccessCountLimitationContext();
        context.setCommand(command);
        return context;
    }
}
