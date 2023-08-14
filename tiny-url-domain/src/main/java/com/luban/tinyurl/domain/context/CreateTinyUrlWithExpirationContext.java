package com.luban.tinyurl.domain.context;

import com.luban.tinyurl.domain.command.CreateTinyUrlWithExpirationCommand;

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
