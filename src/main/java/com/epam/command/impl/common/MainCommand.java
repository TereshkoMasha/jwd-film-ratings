package com.epam.command.impl.common;

import com.epam.command.*;


public class MainCommand implements CommandRequest {
    @Override
    public CommandExecute executeCommand(RequestData request) {
        CommandExecute commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
        commandExecute.setPagePath(Destination.MAIN_PAGE.getPath());
        return commandExecute;
    }
}
