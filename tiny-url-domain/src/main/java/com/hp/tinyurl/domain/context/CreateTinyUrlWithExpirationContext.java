package com.hp.tinyurl.domain.context;

import com.hp.tinyurl.domain.command.CreateTinyUrlWithExpirationCommand;

/**
 * @author hp
 */
public class CreateTinyUrlWithExpirationContext extends AbstractCreateTinyUrlContext<CreateTinyUrlWithExpirationCommand> {

    public static CreateTinyUrlWithExpirationContext create(CreateTinyUrlWithExpirationCommand command) {
        final CreateTinyUrlWithExpirationContext context = new CreateTinyUrlWithExpirationContext();
        context.setCommand(command);
        return context;
    }
}
