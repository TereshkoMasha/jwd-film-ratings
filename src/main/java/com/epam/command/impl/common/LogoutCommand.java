package com.epam.command.impl.common;

import com.epam.command.*;

public class LogoutCommand implements CommandRequest {
    @Override
    public CommandExecute executeCommand(RequestData request) {
        request.setInvalidated(true);
        CommandExecute commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
        return commandExecute;
    }
}
