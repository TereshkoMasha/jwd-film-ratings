package com.epam.command.impl.common;

import com.epam.command.Command;
import com.epam.command.CommandExecute;
import com.epam.command.Destination;
import com.epam.command.RoteType;

import javax.servlet.http.HttpServletRequest;

public class MainCommand implements Command {
    @Override
    public CommandExecute executeCommand(HttpServletRequest request) {
        var commandExecute = new CommandExecute(RoteType.REDIRECT);
        commandExecute.setPagePath(Destination.MAIN_PAGE.getPath());
        return commandExecute;
    }
}
