package com.epam.command.impl.common;

import com.epam.command.Command;
import com.epam.command.CommandExecute;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {


    @Override
    public CommandExecute executeCommand(HttpServletRequest request) {
        return null;
    }
}
